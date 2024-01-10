package App.Model.Entity.Pheromone;

import java.awt.Color;
import java.awt.Point;

import App.Model.World;
import App.Model.Entity.Entity;
import App.Util.EntityTypes;

public abstract class Pheromone extends Entity implements Comparable<Integer> {

    protected int pheromoneValue;

    public Pheromone(
        Color color, Point point, int size, World world, EntityTypes entityType, int pheromoneValue) {
        super(color, point, size, world, entityType);
        this.pheromoneValue = pheromoneValue;
        this.point = point;
    }

    public Pheromone(Color color, int size, World world, EntityTypes entityType, int pheromoneValue) {
        super(color, size, world, entityType);
        this.pheromoneValue = pheromoneValue;
    }

    public abstract void setPheromoneValue(int newPheromoneValue);
    
    public abstract void evaporate();

    public int getPheromoneValue() {
        return pheromoneValue;
    }

    @Override
    public String toString() {
        return String.valueOf(pheromoneValue);
    }

    @Override
    public int compareTo(Integer o) {
        return Integer.compare(this.pheromoneValue, o);
    }
}
