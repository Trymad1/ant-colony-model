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

    public World() {

        entities = new HashMap<>();
        updatableObjects = new ArrayList<>();

        Map<PheromoneTypes, Map<Point, Pheromone>> tempMap = new HashMap<>();
        tempMap.put(PheromoneTypes.COLLECTOR, new HashMap<>());
        tempMap.put(PheromoneTypes.SOLDIER, new HashMap<>());
        pheromones = Collections.unmodifiableMap(new HashMap<>());

        anthill = new Anthill();
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
            (worldSize.width / 2) - (int) (anthill.getSize() / 2) - 1,
            (worldSize.height / 2)  - (int) (anthill.getSize() / 2) - 1);
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
        for (int i = point.x; i < point.x + entity.getSize(); i++) {
            for (int j = point.y; j < point.y + entity.getSize(); j++) {
                coord.add(new Point(i,j));
            }
        }

        if (isPossibleToCreate(coord) == false) return;
        coord.forEach(emptyPoint -> entities.put(emptyPoint, entity));
    }

    private boolean isPossibleToCreate(List<Point> coord) {
        boolean isNotEmptyCoord = coord.stream().anyMatch(point -> entities.get(point) != null);
        return !isNotEmptyCoord;
    }
    
}
