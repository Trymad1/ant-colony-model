package App.Model.Entity.Ant;

import java.awt.Point;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.Map.Entry;

import App.Model.Entity.Pheromone.Pheromone;

public class AntAlgorithm {
    private static final double ALPHA = 2.0; // Вес феромона
    private static final double BETA = 1.0;  // Вес эвристической информации

    public static float calculateProbability(float pheromone) {

        return (float) ( (Math.pow(pheromone, ALPHA) + 0.0001f) * Math.pow(1, BETA));
    }

    public static Map<Point, Float> calculateProbability(Map<Point, Pheromone> pheromones) {
        final Map<Point, Float> probabilities = new HashMap<>();
        pheromones.forEach( (key, value) -> 
            probabilities.put(key, calculateProbability(value.getPheromoneValue())));
        return probabilities;
    }

    public static Point chooseNextCell(Map<Point, Float> probabilities) {
        float totalProbability = 0.0f;

        for (double probability : probabilities.values()) {
            totalProbability += probability;
        }

        final Map<Point, Float> probabilitiesCopy = new HashMap<>();
        for (Entry<Point, Float> entry : probabilities.entrySet()) {
            probabilitiesCopy.put(entry.getKey(), entry.getValue() / totalProbability);
        }

        float randomValue = new Random().nextFloat();
        float cumulativeProbability = 0.0f;

        Point point = null;
        for (Entry<Point, Float> entry : probabilitiesCopy.entrySet()) {
            cumulativeProbability += entry.getValue();
            if (randomValue <= cumulativeProbability) {
                point = entry.getKey();
                break;
            }
        }

        return point;
    }
}
