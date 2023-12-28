package App.Model.Entity.Ant;

import java.awt.Point;
import java.util.Optional;

import App.Model.Updatable;
import App.Model.World;
import App.Model.Entity.Entity;
import App.Util.EntityParams;
import App.Util.EntityTypes;

public class Anthill extends Entity implements Updatable {

    private float foodQuantity; 

    public Anthill(Point point, World world) {
        super(EntityParams.Colors.ANTHILL, point, EntityParams.Sizes.ANTHILL, world, EntityTypes.ANTHILL);
    }

    public Anthill(World world) {
        super(EntityParams.Colors.ANTHILL, EntityParams.Sizes.ANTHILL, world, EntityTypes.ANTHILL);
    }

    public float getFoodQuantity() {
        return foodQuantity;
    }

    public boolean putFood(Optional<Entity> food) {
        return false; //TODO
    }

    public void clear() {
        foodQuantity = 0;
    }

    @Override
    public void update() {
        
    }
}
