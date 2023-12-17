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
import App.Model.Entity.Ant.Pheromone;
import App.Model.Entity.Ant.PheromoneTypes;

public class World {

    private final Map<Point, Entity> entities;
    private final List<Updatable> updatableObjects;
    private final Map<PheromoneTypes, Map<Point, Pheromone>> pheromones;
    private final Anthill anthill;

    private Dimension worldSize;

    private final int DEFAULT_SIZE_WIDTH = 100;
    private final int DEFAULT_SIZE_HEIGHT = 100;

    public World() {

        entities = new HashMap<>();
        worldSize = new Dimension(DEFAULT_SIZE_WIDTH, DEFAULT_SIZE_HEIGHT);
        updatableObjects = new ArrayList<>();

        Map<PheromoneTypes, Map<Point, Pheromone>> tempMap = new HashMap<>();
        tempMap.put(PheromoneTypes.COLLECTOR, new HashMap<>());
        tempMap.put(PheromoneTypes.SOLDIER, new HashMap<>());
        pheromones = Collections.unmodifiableMap(new HashMap<>());

        anthill = new Anthill(this);
    }

    public World(Dimension worldSize) {
        this();
        this.worldSize = worldSize;
    }

    private void update() {
        updatableObjects.stream().forEach(Updatable::update);
    }

    public void createRandomWorld() {
        final Point anthillPoint = new Point(
            ((worldSize.width - 1) / 2),
            ((worldSize.height - 1) / 2));
        createEntity(anthill, anthillPoint);
    }

    public void clearWorld() {
        entities.clear();
        updatableObjects.clear();
        pheromones.get(PheromoneTypes.COLLECTOR).clear();
        pheromones.get(PheromoneTypes.SOLDIER).clear();
    }

    public Map<Point, Pheromone> getSoldierPheromone() {
        return pheromones.get(PheromoneTypes.SOLDIER);
    }

    public Map<Point, Pheromone> getCollectorPheromone() {
        return pheromones.get(PheromoneTypes.COLLECTOR);
    }

    public Dimension getWorldSize() {
        return worldSize;
    }

    public void setWorldSize(Dimension worldSize) {
        this.worldSize = worldSize;
    }

    public Map<Point, Entity> getEntities() {
        return entities;
    }

    private void createEntity(Entity entity, Point point) {
        List<Point> coord = new ArrayList<>();

        // Единица в четном случае отнимается для того, чтобы
        // центр элемента оставался в том же месте
        int centeredX = entity.getSize() % 2 == 0 ?
            point.x - ((entity.getSize() / 2) - 1) :
            point.x - entity.getSize() / 2;
        
        int centeredY = entity.getSize() % 2 == 0 ?
            point.y - ((entity.getSize() / 2) - 1) :
            point.y - entity.getSize() / 2;
        
        final Point centeredPoint = new Point(centeredX, centeredY);
        for (int i = centeredPoint.x; i < centeredPoint.x + entity.getSize(); i++) {
            for (int j = centeredPoint.y; j < centeredPoint.y + entity.getSize(); j++) {
                coord.add(new Point(i,j));
            }
        }

        if (isPossibleToCreate(coord) == false) return;
        entity.setPoint(point);
        coord.forEach(emptyPoint -> {
            entities.put(emptyPoint, entity);
        });
    }

    private boolean isPossibleToCreate(List<Point> coord) {
        boolean isNotEmptyCoord = coord.stream().anyMatch(point -> entities.get(point) != null);
        return !isNotEmptyCoord;
    }
    
}
