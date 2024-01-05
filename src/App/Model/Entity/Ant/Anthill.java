package App.Model.Entity.Ant;

import java.awt.Point;

import App.Model.Updatable;
import App.Model.World;
import App.Model.Entity.Entity;
import App.Model.Entity.Food.Food;
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

    public void putFood(Food food) {
        foodQuantity += food.getFoodValue();
    }

    public void clear() {
        foodQuantity = 0;
    }

    @Override
    public void update() {
        
    }
}
