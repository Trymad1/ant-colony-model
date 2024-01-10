package App.Model.Entity.Pheromone;

import java.awt.Point;

import App.Model.World;
import App.Util.EntityParams;
import App.Util.EntityTypes;

public class ToTargetPheromone extends Pheromone {

    private int QUANT_ITERATION_FOR_RESET = 100;
    private int iterationWithoutUpdate = 0;
    private boolean wasUpdated = false;

    public ToTargetPheromone(Point point, World world, int pheromoneValue) {
        super(EntityParams.Colors.PHEROMONE_TO_TARGET, 
        EntityParams.Sizes.PHEROMONE, world, 
        EntityTypes.PHEROMONE, pheromoneValue);
        this.point = point;
    }

    public ToTargetPheromone(World world , int pheromoneValue) {
        super(EntityParams.Colors.PHEROMONE_TO_TARGET, 
        EntityParams.Sizes.PHEROMONE, world, 
        EntityTypes.PHEROMONE, pheromoneValue);
    }

    public ToTargetPheromone(World world) {
        super(EntityParams.Colors.PHEROMONE_TO_TARGET, 
        EntityParams.Sizes.PHEROMONE, world, 
        EntityTypes.PHEROMONE, 0);
    }

    public ToTargetPheromone(World world, Point point) {
        super(EntityParams.Colors.PHEROMONE_TO_TARGET, point,
        EntityParams.Sizes.PHEROMONE, world, 
        EntityTypes.PHEROMONE, 0);
    }

    @Override
    public void setPheromoneValue(int newPheromoneValue) {
        this.pheromoneValue = (this.pheromoneValue > newPheromoneValue) || pheromoneValue == 0 ? 
            newPheromoneValue : this.pheromoneValue;
        this.wasUpdated = true;
    }

    @Override
    public void evaporate() {
        if (wasUpdated) {
            wasUpdated = false;
            iterationWithoutUpdate = 0;
            return;
        }

        if (this.pheromoneValue != 0) {
            iterationWithoutUpdate++;
        }

        if(iterationWithoutUpdate >= QUANT_ITERATION_FOR_RESET) {
            pheromoneValue = 0;
        }
    }
}
