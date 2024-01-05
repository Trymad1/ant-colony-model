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

import App.Model.World;
import App.Model.Entity.Entity;
import App.Model.Entity.Food.Food;
import App.Model.Entity.Food.FoodSource;
import App.Model.Entity.Pheromone.Pheromone;
import App.Util.Directional;
import App.Util.Directionals;
import App.Util.Entities;
import App.Util.EntityParams;
import App.Util.PheromoneTypes; 

public class CollectorAnt extends Ant {

    public CollectorAnt(Point point, World world) {
        super(EntityParams.Colors.ANT_COLLECTOR, point, world);
    }
    
    public CollectorAnt(World world) {
        super(EntityParams.Colors.ANT_COLLECTOR, world);  
    }

        // int random = (int) (Math.random() * emptyPoint.size());
        // if (emptyPoint.size() == 0) return Optional.ofNullable(null);
        // return Optional.of(emptyPoint.get(random));
    @Override
    protected Optional<Point> findPointToMove() {
        final Map<Point, Optional<Entity>> nearPoints = getNearPoints();
        final Optional<FoodSource> foodSource 
            = Entities.findTargetEntityNear(nearPoints, EntityParams.Colors.FOOD, FoodSource.class);
        final Optional<Anthill> anthill = Entities.findTargetEntityNear(nearPoints, EntityParams.Colors.ANTHILL, Anthill.class);

        List<Point> nearEmptyPoints = Entities.findEmptyPoint(nearPoints);
        final PheromoneTypes pheromoneType = this.getTargetPheromone();

        if(foodSource.isPresent() && 
           foodSource.get().hasFood() && 
           takedItem.isEmpty()) {
            take(foodSource.get().getFood());
            
            Map<Point, Pheromone> nearPheromone = 
                getPheromoneUtil().getPheromone(this.getTargetPheromone(), nearEmptyPoints);
            
            Point point = null;
            point = AntAlgorithm.chooseNextCell(AntAlgorithm.calculateProbability(nearPheromone));
            this.directional = getNewDirectional(point);

            pheromoneValue = START_PHEROMONE_VALUE;
            return Optional.ofNullable(null);
        } else if (anthill.isPresent() && 
            takedItem.isPresent()) {
            anthill.get().putFood( (Food) takedItem.get());
            takedItem = Optional.ofNullable(null);
            
            // Map<Point, Pheromone> nearPheromone = 
            //     getPheromoneUtil().getPheromone(this.getTargetPheromone(), nearEmptyPoints);
            // point = AntAlgorithm.chooseNextCell(AntAlgorithm.calculateProbability(nearPheromone));
            // directional = Directionals.getDirectional(point);
            directional = directional.getOppositeDirectional();

            color = EntityParams.Colors.ANT_COLLECTOR;
            pheromoneValue = START_PHEROMONE_VALUE;
            return Optional.ofNullable(null);
        };

        List<Point> emptyPointToMove = 
            this.getPointOnDirectional(nearEmptyPoints);
        
        Map<Point, Pheromone> nearPheromone = 
            getPheromoneUtil().getPheromone(pheromoneType, emptyPointToMove);
        
        Point point = null;

        point = AntAlgorithm.chooseNextCell(AntAlgorithm.calculateProbability(nearPheromone));
        if (point != null) {
            directional = getNewDirectional(point);
        }
    
        return Optional.ofNullable(point);
    }


}
