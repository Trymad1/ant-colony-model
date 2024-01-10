package App.Model.Entity.Ant;

import java.awt.Point;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Deque;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Map.Entry;
import java.util.stream.Collectors;

import App.Model.World;
import App.Model.Entity.Entity;
import App.Model.Entity.Food.Food;
import App.Model.Entity.Food.FoodSource;
import App.Model.Entity.Pheromone.Pheromone;
import App.Util.Directional;
import App.Util.Directionals;
import App.Util.Entities;
import App.Util.EntityParams;
import App.Util.FixedSizeQueue;
import App.Util.PheromoneTypes; 

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

    @Override
    protected Optional<Point> findPointToMove() {

        final Map<Point, Optional<Entity>> nearPoints = getNearPoints();

        final Optional<FoodSource> foodSource 
            = Entities.findTargetEntityNear(nearPoints, EntityParams.Colors.FOOD, FoodSource.class);

        final Optional<Anthill> anthill = Entities.findTargetEntityNear(nearPoints, EntityParams.Colors.ANTHILL, Anthill.class);

        final List<Point> nearEmptyPoints = Entities.findEmptyPoint(nearPoints);

        final PheromoneTypes pheromoneType = this.getTargetPheromone();

        if(foodSource.isPresent() && 
           foodSource.get().hasFood() && 
           !takedItem.isPresent()) {
            take(foodSource.get().getFood());
            
            Map<Point, Pheromone> nearPheromone = 
                getPheromoneUtil().getPheromone(this.getTargetPheromone(), nearEmptyPoints);
            this.directional = this.getHighValueDirectional(nearPheromone);
            pheromoneValue = 1;
            lastVisitedPoint.clear();

            return Optional.ofNullable(null);
        } else if (anthill.isPresent() && 
            takedItem.isPresent()) {
            anthill.get().putFood( (Food) takedItem.get());
            takedItem = Optional.ofNullable(null);
            
            Map<Point, Pheromone> nearPheromone = 
                getPheromoneUtil().getPheromone(this.getTargetPheromone(), nearEmptyPoints);
            this.directional = this.getHighValueDirectional(nearPheromone);
            pheromoneValue = 1;

            color = EntityParams.Colors.ANT_COLLECTOR;
            lastVisitedPoint.clear();
            return Optional.ofNullable(null);
        };
        
        List<Point> emptyNotVisitedPoint = nearEmptyPoints.stream()
        .filter(point -> !lastVisitedPoint.contain(point))
        .collect(Collectors.toList());
        
        if (emptyNotVisitedPoint.size() == 0) {
            return Optional.ofNullable(null);
        }
        
        Map<Point, Pheromone> nearPheromone = 
            getPheromoneUtil().getPheromone(pheromoneType, emptyNotVisitedPoint);
        
        Optional<Point> highValue = getHighPheromoneValuePoint(nearPheromone);

        if (highValue.isPresent()) {
            Point highPoint = highValue.get();
            this.directional = Directionals.getDirectional(
                new Point(highPoint.x - point.x, highPoint.y - point.y));
            lastVisitedPoint.add(highPoint);
            return Optional.of(highPoint);
        } else {
            List<Point> emptyPointToMoveDirectional = 
                this.getPointOnDirectional(emptyNotVisitedPoint);
                if (emptyPointToMoveDirectional.size() == 0) {
                    return Optional.ofNullable(null);
                }
            int random = (int) (Math.random() * emptyPointToMoveDirectional.size());
            Point randomPointToMove = emptyPointToMoveDirectional.get(random);
            this.directional = Directionals.getDirectional(
                new Point(randomPointToMove.x - point.x, randomPointToMove.y - point.y));
            lastVisitedPoint.add(randomPointToMove);
            return Optional.of(randomPointToMove);
        }
    }

    private Directional getHighValueDirectional(Map<Point, Pheromone> pheromone) {
            
        Optional<Point> highPheromoneValuePoint = getHighPheromoneValuePoint(pheromone);
    
        if (highPheromoneValuePoint.isPresent()) {
            return getNewDirectional(highPheromoneValuePoint.get());
        } else {
            return Directionals.getRandomDirectional();
        }
    }

    private Optional<Point> getHighPheromoneValuePoint(Map<Point, Pheromone> pheromone) {
        return pheromone.entrySet().stream()
            .filter(entrySet -> entrySet.getValue().getPheromoneValue() > 0)
            .min(Comparator.comparingInt(entrySet -> entrySet.getValue().getPheromoneValue()))
            .map(Map.Entry::getKey);
    }


}
