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
import App.Util.Directional;
import App.Util.Directionals;
import App.Util.EntityParams;
import App.Util.PheromoneTypes;

public abstract class Ant extends Creature implements CanTake {

    protected Optional<Item> takedItem;
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

    @Override
    public void take(Item item) {
        takedItem = Optional.of(item);
        color = Color.GREEN;
    }

    protected abstract Optional<Point> findPointToMove();

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

    protected void dropPheromone(Point point) {
        if (pheromoneValue > 200) {
            return;
        }
        final PheromoneTypes pheromoneType = getDroppedPheromone();
        super.getPheromoneUtil().dropPheromone(pheromoneType, point, pheromoneValue);
        pheromoneValue++;
    } 
 
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

    protected Directional getNewDirectional(Point pointToMove) {
        final Point pointDirectional = 
            new Point(pointToMove.x - this.point.x, pointToMove.y - this.point.y);
        return Directionals.getDirectional(pointDirectional);
    } 

    protected PheromoneTypes getDroppedPheromone() {
        return takedItem.isPresent() ? 
            PheromoneTypes.TO_TARGET : PheromoneTypes.TO_ANTHILL;
    }

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
