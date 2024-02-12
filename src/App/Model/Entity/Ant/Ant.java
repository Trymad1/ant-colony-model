package App.Model.Entity.Ant;

import java.awt.Color;
import java.awt.Point;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import App.Model.World;
import App.Model.Entity.Creature;
import App.Model.Interface.CanTake;
import App.Model.Interface.Item;
import App.Util.Enum.Directional;
import App.Util.Enum.PheromoneTypes;
import App.Util.Static.Directionals;
import App.Util.Static.EntityParams;

/*
 * Describes the basic properties and abilities of any ant
 */
public abstract class Ant extends Creature implements CanTake {

    protected Optional<Item> takedItem;

    // Direction of movement, only used when ants move randomly
    // For more natural movement
    protected Directional directional; 
    protected boolean inAnthill;

    protected int pheromoneValue;
    protected final int START_PHEROMONE_VALUE = 1;

    public Ant(Color color, Point point, World world) {
        this(color, world);
        setPoint(point);
    }

    public Ant(Color color, World world) {
        super(color, EntityParams.Sizes.ANT, world);
        takedItem = Optional.ofNullable(null);
        directional = Directionals.getRandomDirectional();
        inAnthill = true;
    }

    /**
     * @param item - Item to be picked up
     */
    @Override
    public void take(Item item) {
        takedItem = Optional.of(item);
        color = Color.GREEN;
    }

    /**
    * Describes the algorithm that determines the next point to which the ant wants to move, as well as its actions
     * such as item selection
     * @retun The point to which the ant wants to move can be null if the ant has performed an action or has nowhere to move
     */
    protected abstract Optional<Point> findPointToMove();

    /**
     * Method called on every iteration of the world
     */
    @Override
    public void update() {
        if(inAnthill == true) return;
        final Optional<Point> pointToMove = findPointToMove();
        if (pointToMove.isPresent()) {
            dropPheromone(point);
            move(pointToMove.get());
        } else {
            this.directional = Directionals.getRandomDirectional();
        };
    }

    /**
     * Leaves a pheromone on the transmitted point
     * @param point - the point at which the pheromone will be left
     */
    protected void dropPheromone(Point point) {
        if (pheromoneValue > 200) {
            return;
        }
        final PheromoneTypes pheromoneType = getDroppedPheromone();
        super.getPheromoneUtil().dropPheromone(pheromoneType, point, pheromoneValue);
        pheromoneValue++;
    } 
 
    /**
     * 
     * @param points - list of ant's neighboring points
     * @return - list of points that are on the ant's trajectory
     */
    protected List<Point> getPointOnDirectional(List<Point> points) {
        final Point[] pointInfo = directional.pointInfo;
        return points.stream()
        .filter(p -> (p.x == this.point.x + pointInfo[0].x &&
                      p.y == this.point.y + pointInfo[0].y)
                      ||
                      (p.x == this.point.x + pointInfo[1].x &&
                      p.y == this.point.y + pointInfo[1].y)
                      ||
                      (p.x == this.point.x + pointInfo[2].x &&
                      p.y == this.point.y + pointInfo[2].y)
                      )
        .collect(Collectors.toList());
    }

    /*
    *Gets a new direction for the ant, according to the point to which it will move
    *the direction changes if the ant did not go in a straight line, but changed the angle of movement
    */
    protected Directional getNewDirectional(Point pointToMove) {
        final Point pointDirectional = 
            new Point(pointToMove.x - this.point.x, pointToMove.y - this.point.y);
        return Directionals.getDirectional(pointDirectional);
    } 

    /**
    *Returns the type of pheromone that the ant should leave behind when moving
    *@return -pheromone type
    */
    protected PheromoneTypes getDroppedPheromone() {
        return takedItem.isPresent() ? 
            PheromoneTypes.TO_TARGET : PheromoneTypes.TO_ANTHILL;
    }

    /**
    *Returns the type of pheromone that the ant should make
    *@return -pheromone type
    */
    protected PheromoneTypes getTargetPheromone() {
        return takedItem.isPresent() ? 
            PheromoneTypes.TO_ANTHILL : PheromoneTypes.TO_TARGET;
    }

    protected boolean inAnthill() {
        return inAnthill;
    }

    protected void setInAnthill(boolean inAnthill) {
        this.inAnthill = inAnthill;
    }
}
