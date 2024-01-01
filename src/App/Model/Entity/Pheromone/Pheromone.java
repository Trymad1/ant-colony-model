package App.Model.Entity.Pheromone;

import java.awt.Color;
import java.awt.Point;
import java.text.DecimalFormat;

import App.Model.World;
import App.Model.Entity.Entity;
import App.Util.EntityTypes;

public abstract class Pheromone extends Entity {

    private float pheromoneValue;

    public Pheromone(Color color, Point point, int size, World world, EntityTypes entityType, float pheromoneValue) {
        super(color, point, size, world, entityType);
        this.pheromoneValue = pheromoneValue;
        this.point = point;
        //TODO Auto-generated constructor stub
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
            Float.valueOf(String.format("%.3f", pheromoneValue + valueToAdd).replace(',', '.'));
        
        if(pheromoneValue < 0.000f) pheromoneValue = 0.000f;
    }

    public void minusPheromone(float valueToAdd) {
        addPheromone(-1 * valueToAdd);
    }

    public float getPheromoneValue() {
        return pheromoneValue;
    }

}
