package App.Model.Entity.Pheromone;

import java.awt.Point;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import App.Model.Updatable;
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
            pheromones.values().stream()
            .forEach(pheromone -> {
                float value = pheromone.getPheromoneValue();
                value *= (0.07 * value);
                value = value + 0.002f;
                pheromone.minusPheromone(value);
            });
        }
    }

    public void addPheromone(PheromoneTypes pheromoneType, Point point, float value) {
        final Map<Point, Pheromone> pheromoneMap = pheromones.get(pheromoneType);
        pheromoneMap.get(point).addPheromone(value);
    }

    public Map<Point, Pheromone> getPheromone(
        PheromoneTypes pheromoneType, List<Point> points) {
        final Map<Point, Pheromone> pheromoneList = pheromones.get(pheromoneType);
        final Map<Point, Pheromone> pheromones = new HashMap<>();

        points.forEach(point -> pheromones.put(point, pheromoneList.get(point)));
        return pheromones;
    }
}
