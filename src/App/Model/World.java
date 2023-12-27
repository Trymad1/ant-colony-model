package App.Model;

import java.awt.Dimension;
import java.awt.Point;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Queue;

import App.Model.Entity.Entity;
import App.Model.Entity.Ant.Anthill;
import App.Model.Entity.Ant.CollectorAnt;
import App.Model.Entity.Ant.SoldierAnt;
import App.Model.Entity.Food.FoodSource;
import App.Util.Entities;
import App.Util.PheromoneTypes;
import App.Util.Worlds;

public class World {

    private final Map<Point, Entity> entitiesInWorld;
    private final List<Entity> allEntities;
    private final List<Updatable> updatableObjects;
    private final Map<PheromoneTypes, Map<Point, Float>> pheromones;
    private final List<Point> areaForRandomSpawn;
    private final Anthill anthill;

    private final Queue<Entity> toRemove;
    private final Queue<Entity> toCreate;

    private Dimension worldSize;

    public final int COLLECTORS_ON_CREATE = 10; // 30
    public final int FOOD_ON_CREATE = 7; // 5
    public final int SOLDIER_ON_CREATE = 0; // 20

    private final int DEFAULT_SIZE_WIDTH = 100;
    private final int DEFAULT_SIZE_HEIGHT = 100;
    
    public World() {
        
        entitiesInWorld = new HashMap<>();
        worldSize = new Dimension(DEFAULT_SIZE_WIDTH, DEFAULT_SIZE_HEIGHT);
        updatableObjects = new ArrayList<>();
        areaForRandomSpawn = new ArrayList<>();
        allEntities = new ArrayList<>();

        toRemove = new ArrayDeque<>();
        toCreate = new ArrayDeque<>();
        
        Map<PheromoneTypes, Map<Point, Float>> tempMap = new HashMap<>();
        tempMap.put(PheromoneTypes.TO_ANTHILL, new HashMap<>());
        tempMap.put(PheromoneTypes.TO_TARGET, new HashMap<>());
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

    
    
    public void createRandomWorld() {
        
        List<Point> allPointsInWorld = 
        Worlds.getArea(new Point(0, 0), 
                new Point(worldSize.width - 1, worldSize.height - 1));
                
        // Инициализация hashmap с феромонами
        allPointsInWorld.stream().forEach( point -> {
            pheromones.get(PheromoneTypes.TO_ANTHILL).put(point, 0f);
            pheromones.get(PheromoneTypes.TO_TARGET).put(point, 0f);
        });

        // Созданиме муравейника
        final Point anthillPoint = new Point(
            ((worldSize.width - 1) / 2),
            ((worldSize.height - 1) / 2));
            anthill.setPoint(anthillPoint);
            createEntity(anthill);
            
        final Point pointNearAnthillA = 
            new Point((worldSize.width - 1) / 3, (worldSize.height - 1) / 3);
        final Point pointNearAnthillB 
            = new Point((worldSize.width - 1) - pointNearAnthillA.x, 
                        (worldSize.height - 1) - pointNearAnthillA.y); 
        // Создание и размещение муравьев около муравейника.
        final List<Point> pointsNearAnthill = Worlds.getArea(
            pointNearAnthillA, pointNearAnthillB);
        
        final List<Point> emptyPointsNearAnthill = Worlds.getEmptyPointFromArea(
            pointsNearAnthill, entitiesInWorld);
        
        for (int i = 0; i < COLLECTORS_ON_CREATE; i++) {
            final Entity entity = randomSpawn(new CollectorAnt(this), emptyPointsNearAnthill);
            if(entity != null) emptyPointsNearAnthill.removeAll(
                Entities.getPointsForEntity(entity.getPoint(), entity.getSize()));
        }

        for (int i = 0; i < SOLDIER_ON_CREATE; i++) {
            final Entity entity = randomSpawn(new SoldierAnt(this), emptyPointsNearAnthill);
            if(entity != null) emptyPointsNearAnthill.removeAll(
                Entities.getPointsForEntity(entity.getPoint(), entity.getSize()));
        }
        
        // Обозначение зоны для спавна других сущностей
        allPointsInWorld.stream()
        .filter(point -> !(pointsNearAnthill.contains(point)))
        .forEach(point -> areaForRandomSpawn.add(point));
    
        // Случайное размещение еды в мире на определенном расстоянии от колонии
        final List<Point> emptyPointsForRespawn 
            = Worlds.getEmptyPointFromArea(areaForRandomSpawn, entitiesInWorld);

        for (int i = 0; i < FOOD_ON_CREATE; i++) {  
            final Entity entity = randomSpawn(new FoodSource(this), emptyPointsForRespawn);
            if(entity != null) emptyPointsForRespawn.removeAll(
                Entities.getPointsForEntity(entity.getPoint(), entity.getSize()));
        }
    }

    public Entity randomSpawn(Entity entity, List<Point> area) {
        
        try {
            if(area.size() < Math.pow(entity.getSize(), 2)) 
                throw new RuntimeException("No place for spawn");
        } catch (Exception e) {
            return null;
        }

        for (int i = 0; i < area.size(); i++) {
            if(i == area.size() - 1) return null;
            
            int randomPointForSpawn = (int) (Math.random() * area.size());
            final Point emptyPoint = area.get(randomPointForSpawn);
            List<Point> entityPoints = Entities.getPointsForEntity(emptyPoint, entity.getSize());
            boolean canPlace = entityPoints.stream().allMatch(point -> area.contains(point));

            if(!canPlace) continue;
            entity.setPoint(emptyPoint);
            createEntity(entity);
            break;
        }

        return entity;
    }

    private Entity createEntity(Entity entity) {
        // Единица в четном случае отнимается для того, чтобы
        // центр элемента оставался в том же месте
        
        List<Point> coord = Entities.getPointsForEntity(entity.getPoint(), entity.getSize());
        if (isPossibleToCreate(coord) == false) return null;
        coord.forEach(emptyPoint -> {
            entitiesInWorld.put(emptyPoint, entity);
        });
        allEntities.add(entity);
        if (entity instanceof Updatable) updatableObjects.add( (Updatable) entity);

        return entity;  
    }

    public void addToRemoveEntity(Entity entity) {
        toRemove.add(entity);
    }

    // private void removeFromQueue(Queue<Entity> toRemove) {
    //     while(!toRemove.isEmpty()) {
    //         removeEntity(toRemove.poll());
    //     }
    // }

    public void addToCreateEntity(Entity entity) {
        toCreate.add(entity);
    }

    private void createFromQueue(Queue<Entity> toCreate) {
        // while(!toCreate.isEmpty()) {
        //     createEntity(toCreate.poll());
        // }
    }

    private Entity removeEntity(Entity entity) {
        List<Point> entityPoints = 
            Entities.getPointsForEntity(entity.getPoint(), entity.getSize());
        if(entity instanceof Updatable) updatableObjects.remove( (Updatable) entity);
        entityPoints.stream().forEach(point -> entitiesInWorld.remove(point));
        allEntities.remove(entity);
        
        return entity;
    }

    private boolean isPossibleToCreate(List<Point> coord) {
        boolean isNotEmptyCoord = 
        coord.stream()
        .anyMatch(point -> 
            (point.x >= 0 && point.y >= 0) &&
            !(point.x <= worldSize.width - 1 && point.y <= worldSize.height - 1) &&
            (entitiesInWorld.get(point) != null));
        return !isNotEmptyCoord;
    }
    
    public void clearWorld() {
        entitiesInWorld.clear();
        updatableObjects.clear();
        pheromones.get(PheromoneTypes.TO_ANTHILL).clear();
        pheromones.get(PheromoneTypes.TO_TARGET).clear();
    }

    public Map<Point, Float> getSoldierPheromone() {
        return pheromones.get(PheromoneTypes.TO_TARGET);
    }

    public Map<Point, Float> getCollectorPheromone() {
        return pheromones.get(PheromoneTypes.TO_ANTHILL);
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

    public Anthill getAnthill() {
        return anthill;
    }
}
