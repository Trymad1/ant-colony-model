package App.Model;

import java.awt.Point;
import java.util.List;
import java.util.Map;

import App.Util.PheromoneTypes;

public class PheromoneUtil implements Updatable {

    private final Map<PheromoneTypes, Map<Point, Float>> pheromones;

    public PheromoneUtil(Map<PheromoneTypes, Map<Point, Float>> pheromones) {
        this.pheromones = pheromones;
    }

    @Override
    public void update() {
        evaporationPheromone();
    }

    private static float TESTCONST = 0.001f;
    // TOOD formula
    private void evaporationPheromone() {
        for (Map<Point, Float> pheromone : pheromones.values()) {
            pheromone.entrySet().stream()
            .forEach( entrySet -> {
                if (entrySet.getValue() - TESTCONST < 0);
                
                // Округление число до 3-х знаков после запятой.
                else pheromone.replace(entrySet.getKey(), 
                (float) Math.round((entrySet.getValue() - TESTCONST) * 1000) / 1000 );
            });
        }
    }

    private static float ADD_VALUE = 0.05f;
    public void addPheromone(PheromoneTypes pheromoneType, Point point) {
        Map<Point, Float> pheromoneMap = pheromones.get(pheromoneType);
        pheromoneMap.replace(point, pheromoneMap.get(point) + ADD_VALUE);
        // System.out.println(pheromoneMap.get(point));
    }
    
}
