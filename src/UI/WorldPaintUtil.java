package UI;

import java.util.HashMap;

import java.util.Map;
import java.awt.Point;

import Model.World;
import Model.Entity.Entity;
import Model.Entity.Pheromone.Pheromone;
import Util.Enum.PheromoneTypes;
import Util.Enum.WorldPaintMode;

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
     * Возвращает список всех сущностей для отрисовки, в зависимости от того,
     * какой режим отрисовки был установлен для текущего экземпляра обьекта
     * @param world мир, с которого будут извлечены сущности
     * @return список сущностей, которые будут отрисованы
     */
    public Map<Point, Entity> getEntityListForDisplay(World world)  {
        final Map<Point, Entity> entityList = new HashMap<>();

        if(displayAnthill) entityList.putAll(world.getAnthill());  
        if(displayObjects) entityList.putAll(world.getObjects());
        
        
        if(displayCreature) entityList.putAll(world.getCreatures());

 

        if(displayPheromoneToTarget) {

            Map<Point, Pheromone> toTargetPheromone = 
                getCopyPheromoneMap(PheromoneTypes.TO_TARGET, world);
            // Удаление из списка феромонов точек, на которых расположены другие сущности
            entityList.keySet().stream()
            .filter(point -> toTargetPheromone.containsKey(point))
            .forEach(point -> toTargetPheromone.remove(point));
            
            entityList.putAll(toTargetPheromone);
        }

        else if(displayPheromoneToAnthill) {
            Map<Point, Pheromone> toTargetPheromone = 
                getCopyPheromoneMap(PheromoneTypes.TO_ANTHILL, world);
            // Удаление из списка феромонов точек, на которых расположены другие сущности
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
