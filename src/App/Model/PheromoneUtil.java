package App.Model;

import java.awt.Point;
import java.util.Map;

import App.Model.Entity.Pheromone.Pheromone;
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

    private static float TESTCONST = 0.003f;
    // TOOD formula
    private void evaporationPheromone() {
        for (Map<Point, Pheromone> pheromones : this.pheromones.values()) {
            pheromones.values().stream()
            .forEach(pheromone -> pheromone.minusPheromone(TESTCONST));
        }
    }

    private static float ADD_VALUE = 0.05f;
    public void addPheromone(PheromoneTypes pheromoneType, Point point) {
        Map<Point, Pheromone> pheromoneMap = pheromones.get(pheromoneType);
        pheromoneMap.get(point).addPheromone(ADD_VALUE);
    }
    
}
