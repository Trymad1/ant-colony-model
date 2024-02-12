package App.Model.Entity.Pheromone;

import java.awt.Point;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import App.Model.Interface.Updatable;
import App.Util.Enum.PheromoneTypes;

/**
*Helper class for pheromone management
*/
public class PheromoneUtil implements Updatable {

    private final Map<PheromoneTypes, Map<Point, Pheromone>> pheromones;

    public PheromoneUtil(Map<PheromoneTypes, Map<Point, Pheromone>> pheromones) {
        this.pheromones = pheromones;
    }

    @Override
    public void update() {
        evaporationPheromone();
    }
    //Calls the evaporation method on each existing pheromone
    private void evaporationPheromone() {
        for (Map<Point, Pheromone> pheromones : this.pheromones.values()) {
            pheromones.values().stream().forEach(Pheromone::evaporate);
        }
    }

    /*
    *An attempt to update the pheromone value at a certain coordinate
    */
    public void dropPheromone(PheromoneTypes pheromoneType, Point point, int value) {
        final Map<Point, Pheromone> pheromoneMap = pheromones.get(pheromoneType);
        pheromoneMap.get(point).setPheromoneValue(value);
    }

    public Map<Point, Pheromone> getPheromone(
        PheromoneTypes pheromoneType, List<Point> points) {
        final Map<Point, Pheromone> pheromoneList = pheromones.get(pheromoneType);
        final Map<Point, Pheromone> pheromones = new HashMap<>();

        points.forEach(point -> pheromones.put(point, pheromoneList.get(point)));
        return pheromones;
    }
}
