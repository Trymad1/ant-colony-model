package App.UI;

import java.util.ConcurrentModificationException;
import java.util.HashMap;

import java.util.Map;
import java.awt.Point;

import App.Model.World;
import App.Model.Entity.Entity;
import App.Model.Entity.Pheromone.Pheromone;
import App.Util.PheromoneTypes;
import App.Util.WorldPaintMode;

public final class WorldPaintUtil {

    boolean displayAnthill;
    boolean displayObjects;
    boolean displayCreature;
    boolean displayPheromoneToAnthill;
    boolean displayPheromoneToTarget;

    public WorldPaintUtil() {
        setPaintMode(WorldPaintMode.ALL_ENTITIES);
    }

    public Map<Point, Entity> getEntityListForDisplay(World world) {
        final Map<Point, Entity> entityList = new HashMap<>();

        if(displayAnthill) entityList.putAll(world.getAnthill());  
        if(displayObjects) entityList.putAll(world.getObjects());
        
        try {
            if(displayCreature) entityList.putAll(world.getCreatures());
        } catch (ConcurrentModificationException ignored) {
            System.out.println("Bug");
            // TODO fix bug

            // Очень редко в этом участке кода выбрасывается исключение, 
            // из за чего на текущей итерации мира сущности категории
            // creature не удается получить в полном составе. 
            // Так же начинает часто выбрасываться, если
            // поставить метод обновления мира update() в потоке 
            // WorldThread класса Controller раньше отрисовки поля. 
        }

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
