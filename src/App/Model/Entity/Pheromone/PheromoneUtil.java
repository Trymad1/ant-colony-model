package App.Model.Entity.Pheromone;

import java.awt.Point;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import App.Model.Interface.Updatable;
import App.Util.PheromoneTypes;

public class PheromoneUtil implements Updatable {

    private final Map<PheromoneTypes, Map<Point, Pheromone>> pheromones;

    public PheromoneUtil(Map<PheromoneTypes, Map<Point, Pheromone>> pheromones) {
        this.pheromones = pheromones;
    }

    @Override
    public void update() {
        evaporationPheromone();
    }
    // TOOD formula
    private void evaporationPheromone() {
        for (Map<Point, Pheromone> pheromones : this.pheromones.values()) {
            pheromones.values().stream().forEach(Pheromone::evaporate);
        }
    }

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
