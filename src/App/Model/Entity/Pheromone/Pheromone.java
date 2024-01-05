package App.Model.Entity.Pheromone;

import java.awt.Color;
import java.awt.Point;

import App.Model.World;
import App.Model.Entity.Entity;
import App.Util.EntityTypes;

public abstract class Pheromone extends Entity {

    private float pheromoneValue;

    public Pheromone(
        Color color, Point point, int size, World world, EntityTypes entityType, float pheromoneValue) {
        super(color, point, size, world, entityType);
        this.pheromoneValue = pheromoneValue;
        this.point = point;
    }

    public Pheromone(Color color, int size, World world, EntityTypes entityType, float pheromoneValue) {
        super(color, size, world, entityType);
        this.pheromoneValue = pheromoneValue;
    }

    public void setPheromoneValue(float pheromoneValue) {
        this.pheromoneValue = pheromoneValue;
    }

    public void addPheromone(float valueToAdd) {
        pheromoneValue = 
            Float.valueOf(String.format("%.4f", pheromoneValue + valueToAdd).replace(',', '.'));
        
        if(pheromoneValue < 0.0000f) pheromoneValue = 0.0000f;
    }

    public void minusPheromone(float valueToAdd) {
        addPheromone(-1 * valueToAdd);
    }

    public float getPheromoneValue() {
        return pheromoneValue;
    }

    @Override
    public String toString() {
        return String.valueOf(pheromoneValue);
    }
}
