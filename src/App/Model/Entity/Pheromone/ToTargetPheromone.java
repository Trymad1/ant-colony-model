package App.Model.Entity.Pheromone;

import java.awt.Point;

import App.Model.World;
import App.Util.EntityParams;
import App.Util.EntityTypes;

public class ToTargetPheromone extends Pheromone {

    public ToTargetPheromone(Point point, World world, float pheromoneValue) {
        super(EntityParams.Colors.PHEROMONE_TO_TARGET, 
        EntityParams.Sizes.PHEROMONE, world, 
        EntityTypes.PHEROMONE, pheromoneValue);
        this.point = point;
    }

    public ToTargetPheromone(World world , float pheromoneValue) {
        super(EntityParams.Colors.PHEROMONE_TO_TARGET, 
        EntityParams.Sizes.PHEROMONE, world, 
        EntityTypes.PHEROMONE, pheromoneValue);
    }

    public ToTargetPheromone(World world) {
        super(EntityParams.Colors.PHEROMONE_TO_TARGET, 
        EntityParams.Sizes.PHEROMONE, world, 
        EntityTypes.PHEROMONE, 0f);
    }

    public ToTargetPheromone(World world, Point point) {
        super(EntityParams.Colors.PHEROMONE_TO_TARGET, point,
        EntityParams.Sizes.PHEROMONE, world, 
        EntityTypes.PHEROMONE, 0f);
    }
}
