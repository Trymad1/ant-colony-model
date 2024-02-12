package App.Model.Entity.Ant;

import java.awt.Point;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import App.Model.World;
import App.Model.Entity.Entity;
import App.Model.Entity.Food.Food;
import App.Model.Entity.Food.FoodSource;
import App.Model.Entity.Pheromone.Pheromone;
import App.Util.Enum.Directional;
import App.Util.Enum.FixedSizeQueue;
import App.Util.Enum.PheromoneTypes;
import App.Util.Static.Directionals;
import App.Util.Static.Entities;
import App.Util.Static.EntityParams; 

/**
 * Describes a forager ant whose priority is collecting food
 */
public class CollectorAnt extends Ant {

    private final FixedSizeQueue<Point> lastVisitedPoint; 

    public CollectorAnt(Point point, World world) {
        super(EntityParams.Colors.ANT_COLLECTOR, point, world);
        lastVisitedPoint = new FixedSizeQueue<>(4);
    }
    
    public CollectorAnt(World world) {
        super(EntityParams.Colors.ANT_COLLECTOR, world);  
        lastVisitedPoint = new FixedSizeQueue<>(4);
    }

    /**
     * Determining the ant's next action, points for movement
     */
    @Override
    protected Optional<Point> findPointToMove() {

        final Map<Point, Optional<Entity>> nearPoints = getNearPoints();

        final Optional<FoodSource> foodSource 
            = Entities.findTargetEntityNear(nearPoints, EntityParams.Colors.FOOD, FoodSource.class);

        final Optional<Anthill> anthill = Entities.findTargetEntityNear(nearPoints, EntityParams.Colors.ANTHILL, Anthill.class);

        final List<Point> nearEmptyPoints = Entities.findEmptyPoint(nearPoints);

        final PheromoneTypes pheromoneType = this.getTargetPheromone();

        //If there is a food source nearby that has food and the ant doesn't have
        //picked up item, causes the ant to take food from the source
        if(foodSource.isPresent() && 
           foodSource.get().hasFood() && 
           !takedItem.isPresent()) {
            take(foodSource.get().getFood());
            
            final Map<Point, Pheromone> nearPheromone = 
                getPheromoneUtil().getPheromone(this.getTargetPheromone(), nearEmptyPoints);
            this.directional = this.getHighValueDirectional(nearPheromone);
            pheromoneValue = 1;
            lastVisitedPoint.clear();

            return Optional.ofNullable(null);

        // If there is an anthill nearby and the ant has food in his hands, he will put it in the anthill
        } else if (anthill.isPresent() && 
            takedItem.isPresent()) {
            anthill.get().putFood( (Food) takedItem.get());
            takedItem = Optional.ofNullable(null);
            
            final Map<Point, Pheromone> nearPheromone = 
                getPheromoneUtil().getPheromone(this.getTargetPheromone(), nearEmptyPoints);
            this.directional = this.getHighValueDirectional(nearPheromone);
            pheromoneValue = 1;

            color = EntityParams.Colors.ANT_COLLECTOR;
            lastVisitedPoint.clear();
            return Optional.ofNullable(null);
        };
        
        //If there were no objects nearby, the ant will try to find a neighboring point
        //with the highest pheromone value. The ant cannot step on the last 3 points it visited.
        final List<Point> emptyNotVisitedPoint = nearEmptyPoints.stream()
        .filter(point -> !lastVisitedPoint.contain(point))
        .collect(Collectors.toList());
        
        //If there are no free points nearby to move, the ant will stand still
        if (emptyNotVisitedPoint.size() == 0) {
            return Optional.ofNullable(null);
        }
        
        final Map<Point, Pheromone> nearPheromone = 
            getPheromoneUtil().getPheromone(pheromoneType, emptyNotVisitedPoint);
        
        final Optional<Point> highValue = getHighPheromoneValuePoint(nearPheromone);

        //If there is a point with the highest pheromone value nearby, the ant will move to it
        if (highValue.isPresent()) {
            final Point highPoint = highValue.get();
            this.directional = Directionals.getDirectional(
                new Point(highPoint.x - point.x, highPoint.y - point.y));
            lastVisitedPoint.add(highPoint);
            return Optional.of(highPoint);

        } else {
        //Otherwise, if there is no pheromone nearby, it will move randomly in the direction of its movement
            final List<Point> emptyPointToMoveDirectional = 
                this.getPointOnDirectional(emptyNotVisitedPoint);
                if (emptyPointToMoveDirectional.size() == 0) {
                    return Optional.ofNullable(null);
                }
            int random = (int) (Math.random() * emptyPointToMoveDirectional.size());
            final Point randomPointToMove = emptyPointToMoveDirectional.get(random);
            this.directional = Directionals.getDirectional(
                new Point(randomPointToMove.x - point.x, randomPointToMove.y - point.y));
            lastVisitedPoint.add(randomPointToMove);
            return Optional.of(randomPointToMove);
        }
    }

    /*
    *Gets the point in the direction of movement with the highest pheromone value
    */
    private Directional getHighValueDirectional(Map<Point, Pheromone> pheromone) {
            
        final Optional<Point> highPheromoneValuePoint = getHighPheromoneValuePoint(pheromone);
    
        if (highPheromoneValuePoint.isPresent()) {
            return getNewDirectional(highPheromoneValuePoint.get());
        } else {
            return Directionals.getRandomDirectional();
        }
    }

    /*
    *Returns the point with the best pheromone value
    */
    private Optional<Point> getHighPheromoneValuePoint(Map<Point, Pheromone> pheromone) {
        return pheromone.entrySet().stream()
            .filter(entrySet -> entrySet.getValue().getPheromoneValue() > 0)
            .min(Comparator.comparingInt(entrySet -> entrySet.getValue().getPheromoneValue()))
            .map(Map.Entry::getKey);
    }
}
