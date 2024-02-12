package App.Model.Entity.Pheromone;

import java.awt.Color;
import java.awt.Point;

import App.Model.World;
import App.Model.Entity.Entity;
import App.Util.Enum.EntityTypes;

/**
*Class describing any pheromone and its properties
*/
public abstract class Pheromone extends Entity implements Comparable<Integer> {

    //The pheromone value is the number of points that the ant
    //passed from its beginning. Each ant updates the pheromone value
    //at the point where it is located, but the pheromone remembers only the smallest transmitted value,
    //that is, the minimum number of steps required to reach the point from which the ant came.
    //Thus, the ants find the shortest path from food to home and vice versa, following points with the shortest distance from the goal
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

    /**
    *@param newPheromoneValue -new value for the pheromone
    */
    public abstract void setPheromoneValue(int newPheromoneValue);
    
    /*
    *Determines whether the pheromone will evaporate, and how this will happen
    */
    public abstract void evaporate();

    public int getPheromoneValue() {
        return pheromoneValue;
    }

    @Override
    public String toString() {
        return String.valueOf(pheromoneValue);
    }

    /*
    *Method for comparing two pheromones
    */
    @Override
    public int compareTo(Integer o) {
        return Integer.compare(this.pheromoneValue, o);
    }
}
