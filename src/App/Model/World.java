package App.Model;

import java.awt.Dimension;
import java.awt.Point;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import App.Model.Entity.Entity;
import App.Model.Entity.Ant.Anthill;
import App.Model.Entity.Ant.CollectorAnt;
import App.Model.Entity.Ant.PheromoneTypes;
import App.Model.Entity.Ant.SoldierAnt;
import App.Model.Entity.Food.Food;
import App.Util.Worlds;

public class World {

    private final Map<Point, Entity> entitiesInWorld;
    private final List<Updatable> updatableObjects;
    private final Map<PheromoneTypes, Map<Point, Float>> pheromones;
    private final List<Point> areaForRandomSpawn;
    private final Anthill anthill;

    private Dimension worldSize;

    public final int COLLECTORS_ON_CREATE = 30;
    public final int FOOD_ON_CREATE = 5;
    public final int SOLDIER_ON_CREATE = 20;

    private final int DEFAULT_SIZE_WIDTH = 100;
    private final int DEFAULT_SIZE_HEIGHT = 100;
    
    public World() {
        
        entitiesInWorld = new HashMap<>();
        worldSize = new Dimension(DEFAULT_SIZE_WIDTH, DEFAULT_SIZE_HEIGHT);
        updatableObjects = new ArrayList<>();
        areaForRandomSpawn = new ArrayList<>();
        
        Map<PheromoneTypes, Map<Point, Float>> tempMap = new HashMap<>();
        tempMap.put(PheromoneTypes.COLLECTOR, new HashMap<>());
        tempMap.put(PheromoneTypes.SOLDIER, new HashMap<>());
        pheromones = Collections.unmodifiableMap(tempMap);
        
        anthill = new Anthill(this);
    }
    
    public World(Dimension worldSize) {
        this();
        this.worldSize = worldSize;
    }

    public void update() {
        updatableObjects.stream().forEach(Updatable::update);
    }

    
    private final Point DEFAULT_POINT_NEAR_ANTHILL_A = new Point(40, 40);
    private final Point DEFAULT_POINT_NEAR_ANTHILL_B = new Point(60, 60);

    public void createRandomWorld() {
        List<Point> allPointsInWorld = 
            Worlds.getArea(new Point(0, 0), new Point(99, 99));

        // Инициализация hashmap с феромонами
        allPointsInWorld.stream().forEach( point -> {
            pheromones.get(PheromoneTypes.COLLECTOR).put(point, 0f);
            pheromones.get(PheromoneTypes.SOLDIER).put(point, 0f);
        });

        // Созданиме муравейника
        final Point anthillPoint = new Point(
            ((worldSize.width - 1) / 2),
            ((worldSize.height - 1) / 2));
        createEntity(anthill, anthillPoint);

        // Создание и размещение муравьев около муравейника.
        final List<Point> pointsNearAnthill = Worlds.getArea(
            DEFAULT_POINT_NEAR_ANTHILL_A, DEFAULT_POINT_NEAR_ANTHILL_B);
        
        final List<Point> emptyPointsNearAnthill = Worlds.getEmptyPointFromArea(
            pointsNearAnthill, entitiesInWorld);
        
        for (int i = 0; i < COLLECTORS_ON_CREATE; i++) {
            int randomPointForSpawn = (int) (Math.random() * emptyPointsNearAnthill.size());
            createEntity(new CollectorAnt(this), emptyPointsNearAnthill.remove(randomPointForSpawn));
        }

        for (int i = 0; i < SOLDIER_ON_CREATE; i++) {
            int randomPointForSpawn = (int) (Math.random() * emptyPointsNearAnthill.size());
            createEntity(new SoldierAnt(this), emptyPointsNearAnthill.remove(randomPointForSpawn));
        }
        
        // Обозначение зоны для спавна других сущностей
        allPointsInWorld.stream()
        .filter(point -> !(pointsNearAnthill.contains(point)))
        .forEach(point -> areaForRandomSpawn.add(point));
    
        // Случайное размещение еды в мире на определенном расстоянии от колонии
        final List<Point> copyAreaForRandomSpawn = new ArrayList<>();
        copyAreaForRandomSpawn.addAll(areaForRandomSpawn);

        for (int i = 0; i < FOOD_ON_CREATE; i++) {  
            int randomPointForSpawn = (int) (Math.random() * copyAreaForRandomSpawn.size());
            createEntity(new Food(this), copyAreaForRandomSpawn.remove(randomPointForSpawn));
        }

    }

    public void clearWorld() {
        entitiesInWorld.clear();
        updatableObjects.clear();
        pheromones.get(PheromoneTypes.COLLECTOR).clear();
        pheromones.get(PheromoneTypes.SOLDIER).clear();
    }

    public Map<Point, Float> getSoldierPheromone() {
        return pheromones.get(PheromoneTypes.SOLDIER);
    }

    public Map<Point, Float> getCollectorPheromone() {
        return pheromones.get(PheromoneTypes.COLLECTOR);
    }

    public Dimension getWorldSize() {
        return worldSize;
    }

    public void setWorldSize(Dimension worldSize) {
        this.worldSize = worldSize;
    }

    public Map<Point, Entity> getEntitiesInWorld() {
        return entitiesInWorld;
    }

    private Entity createEntity(Entity entity, Point point) {
        // Единица в четном случае отнимается для того, чтобы
        // центр элемента оставался в том же месте
        
        List<Point> coord = new ArrayList<>();

        final Point centeredPoint = new Point();
        if (entity.getSize() > 1) {
            int centeredX = entity.getSize() % 2 == 0 ?
            point.x - ((entity.getSize() / 2) - 1) :
            point.x - entity.getSize() / 2;
            
            int centeredY = entity.getSize() % 2 == 0 ?
                point.y - ((entity.getSize() / 2) - 1) :
                point.y - entity.getSize() / 2;
            
            centeredPoint.setLocation(centeredX, centeredY);
            for (int i = centeredPoint.x; i < centeredPoint.x + entity.getSize(); i++) {
                for (int j = centeredPoint.y; j < centeredPoint.y + entity.getSize(); j++) {
                    coord.add(new Point(i,j));
                }
            }

        } else {
            centeredPoint.setLocation(point);
            coord.add(point);
        }

        if (isPossibleToCreate(coord) == false) return null;

        entity.setPoint(point);
        coord.forEach(emptyPoint -> {
            entitiesInWorld.put(emptyPoint, entity);
        });
        if (entity instanceof Updatable) updatableObjects.add( (Updatable) entity);

        return entity;
    }

    private boolean isPossibleToCreate(List<Point> coord) {
        boolean isNotEmptyCoord = coord.stream().anyMatch(point -> entitiesInWorld.get(point) != null);
        return !isNotEmptyCoord;
    }
    
}
