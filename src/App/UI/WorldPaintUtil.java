package App.UI;

import java.util.ConcurrentModificationException;
import java.util.HashMap;

import java.util.Map;

import App.Model.World;
import App.Model.Entity.Entity;
import App.Model.Entity.Pheromone.Pheromone;
import App.Util.Enum.PheromoneTypes;
import App.Util.Enum.WorldPaintMode;

import java.awt.Point;

public final class WorldPaintUtil {

    boolean displayAnthill;
    boolean displayObjects;
    boolean displayCreature;
    boolean displayPheromoneToAnthill;
    boolean displayPheromoneToTarget;

    public WorldPaintUtil() {
        setPaintMode(WorldPaintMode.ALL_ENTITIES);
    }

    /**
    *Returns a list of all entities to draw, depending on
    *what rendering mode was set for the current instance of the object
    *@param world the world from which entities will be retrieved
    *@return a list of entities that will be rendered
    */
    public Map<Point, Entity> getEntityListForDisplay(World world) throws ConcurrentModificationException {
        final Map<Point, Entity> entityList = new HashMap<>();


        if(displayAnthill) entityList.putAll(world.getAnthill());  
        if(displayObjects) entityList.putAll(world.getObjects());
        if(displayCreature) entityList.putAll(world.getCreatures());


        if(displayPheromoneToTarget) {

            final Map<Point, Pheromone> toTargetPheromone = 
                getCopyPheromoneMap(PheromoneTypes.TO_TARGET, world);
            //Removing from the list of pheromones points on which other entities are located
            entityList.keySet().stream()
            .filter(point -> toTargetPheromone.containsKey(point))
            .forEach(point -> toTargetPheromone.remove(point));
            
            entityList.putAll(toTargetPheromone);
        }

        else if(displayPheromoneToAnthill) {
            final Map<Point, Pheromone> toTargetPheromone = 
                getCopyPheromoneMap(PheromoneTypes.TO_ANTHILL, world);
            //Removing from the list of pheromones points on which other entities are located
            entityList.keySet().stream()
            .filter(point -> toTargetPheromone.containsKey(point))
            .forEach(point -> toTargetPheromone.remove(point));
            
            entityList.putAll(toTargetPheromone);
        };

        return entityList;
    }

    private Map<Point, Pheromone> getCopyPheromoneMap(PheromoneTypes pheromoneTypes, World world) {
        final Map<Point, Pheromone> pheromoneMap = new HashMap<>();
        pheromoneMap.putAll(world.getPheromones().get(pheromoneTypes));
        return pheromoneMap;
    }

    public void setPaintMode(WorldPaintMode mode) {
        displayAnthill = mode.displayAnthill;
        displayObjects = mode.displayObjects;
        displayCreature = mode.displayCreature;
        displayPheromoneToAnthill = mode.displayPheromoneToAnthill;
        displayPheromoneToTarget = mode.displayPheromoneToTarget;
    }
}
