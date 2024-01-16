package Model.Entity.Ant;

import java.awt.Point;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import Model.World;
import Model.Entity.Entity;
import Model.Entity.Food.Food;
import Model.Entity.Food.FoodSource;
import Model.Entity.Pheromone.Pheromone;
import Util.Enum.Directional;
import Util.Enum.FixedSizeQueue;
import Util.Enum.PheromoneTypes;
import Util.Static.Directionals;
import Util.Static.Entities;
import Util.Static.EntityParams; 

/**
 * Описывает муравья собирателя, приоритетом которого является сбор еды
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
     * Определение следующего действия муравья, точки для передвижения
     */
    @Override
    protected Optional<Point> findPointToMove() {

        final Map<Point, Optional<Entity>> nearPoints = getNearPoints();

        final Optional<FoodSource> foodSource 
            = Entities.findTargetEntityNear(nearPoints, EntityParams.Colors.FOOD, FoodSource.class);

        final Optional<Anthill> anthill = Entities.findTargetEntityNear(nearPoints, EntityParams.Colors.ANTHILL, Anthill.class);

        final List<Point> nearEmptyPoints = Entities.findEmptyPoint(nearPoints);

        final PheromoneTypes pheromoneType = this.getTargetPheromone();

        // Если рядом существует источник еды, в котором есть пища, и муравей не имеет
        // подобранного предмета, заставляет муравья взять еду из источника
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

        // Если рядом муравейник, и муравей имеет в руках еду, он положит ее в муравейник
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
        
        // Если рядом не было никаких обьектов, муравей попытается найти соседнюю точку
        // с самым большим значением феромона. Муравей не может наступить на последние 3 точки которые посетил
        final List<Point> emptyNotVisitedPoint = nearEmptyPoints.stream()
        .filter(point -> !lastVisitedPoint.contain(point))
        .collect(Collectors.toList());
        
        // Если рядом нет свободных точек для перемещения, муравей будет стоять на месте
        if (emptyNotVisitedPoint.size() == 0) {
            return Optional.ofNullable(null);
        }
        
        final Map<Point, Pheromone> nearPheromone = 
            getPheromoneUtil().getPheromone(pheromoneType, emptyNotVisitedPoint);
        
        final Optional<Point> highValue = getHighPheromoneValuePoint(nearPheromone);

        // Если рядом есть точка с высшим значением феромона, муравей переместится на нее
        if (highValue.isPresent()) {
            final Point highPoint = highValue.get();
            this.directional = Directionals.getDirectional(
                new Point(highPoint.x - point.x, highPoint.y - point.y));
            lastVisitedPoint.add(highPoint);
            return Optional.of(highPoint);

        } else {
        // Иначе, если рядом нет феромона он будет двигатся случайным образом в направлении своего движения
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
     * Получает точку в направлении движения с самым большим значением феромона
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
     * Возвращает точку с самым лучшим значением феромона
     */
    private Optional<Point> getHighPheromoneValuePoint(Map<Point, Pheromone> pheromone) {
        return pheromone.entrySet().stream()
            .filter(entrySet -> entrySet.getValue().getPheromoneValue() > 0)
            .min(Comparator.comparingInt(entrySet -> entrySet.getValue().getPheromoneValue()))
            .map(Map.Entry::getKey);
    }


}
