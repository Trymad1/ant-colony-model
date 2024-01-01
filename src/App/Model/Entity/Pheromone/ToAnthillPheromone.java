package App.Model.Entity.Pheromone;

import java.awt.Color;
import java.awt.Point;

import App.Model.World;
import App.Util.Entities;
import App.Util.EntityParams;
import App.Util.EntityTypes;

public class ToAnthillPheromone extends Pheromone {

    public ToAnthillPheromone(Point point, World world, float pheromoneValue) {
        super(EntityParams.Colors.PHEROMONE_TO_ANTHILL, 
        EntityParams.Sizes.PHEROMONE, world, 
        EntityTypes.PHEROMONE, pheromoneValue);
        this.point = point;
    }

    public ToAnthillPheromone(World world , float pheromoneValue) {
        super(EntityParams.Colors.PHEROMONE_TO_ANTHILL, 
        EntityParams.Sizes.PHEROMONE, world, 
        EntityTypes.PHEROMONE, pheromoneValue);
    }

    public ToAnthillPheromone(World world) {
        super(EntityParams.Colors.PHEROMONE_TO_ANTHILL,
        EntityParams.Sizes.PHEROMONE, world, 
        EntityTypes.PHEROMONE, 0f);
    }

    public ToAnthillPheromone(World world, Point point) {
        super(EntityParams.Colors.PHEROMONE_TO_ANTHILL, point,
        EntityParams.Sizes.PHEROMONE, world, 
        EntityTypes.PHEROMONE, 0f);
    }
}
