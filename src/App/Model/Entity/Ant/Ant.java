package App.Model.Entity.Ant;

import java.awt.Color;
import java.awt.Point;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import App.Model.CanTake;
import App.Model.Item;
import App.Model.World;
import App.Model.Entity.Creature;
import App.Util.Directional;
import App.Util.Directionals;
import App.Util.EntityParams;
import App.Util.PheromoneTypes;

public abstract class Ant extends Creature implements CanTake {

    protected Optional<Item> takedItem;
    protected Directional directional;

    protected float pheromoneValue;
    protected final float START_PHEROMONE_VALUE = 1.0f;

    public Ant(Color color, Point point, World world) {
        this(color, world);
        setPoint(point);
    }

    public Ant(Color color, World world) {
        super(color, EntityParams.Sizes.ANT, world);
        takedItem = Optional.ofNullable(null);
        directional = Directionals.getRandomDirectional();
        pheromoneValue = START_PHEROMONE_VALUE;
    }

    @Override
    public void take(Item item) {
        takedItem = Optional.of(item);
        color = Color.GREEN;
    }

    protected abstract Optional<Point> findPointToMove();

    @Override
    public void update() {
        if (findPointToMove().isPresent()) {
            move(findPointToMove().get());
            dropPheromone(getDroppedPheromone(), point);
        } else {
            this.directional = Directionals.getRandomDirectional();
        };
    }

    protected void dropPheromone(PheromoneTypes type, Point point) {
        final PheromoneTypes pheromoneType = getDroppedPheromone();
        super.getPheromoneUtil().addPheromone(type, point, pheromoneValue);
        pheromoneValue /=  1.1;
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
}
