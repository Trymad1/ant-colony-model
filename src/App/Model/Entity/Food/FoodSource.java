package App.Model.Entity.Food;

import java.awt.Color;
import java.awt.Point;


import App.Model.World;
import App.Model.Entity.Entity;
import App.Util.EntityParams;
import App.Util.EntityTypes;

public class FoodSource extends Entity {

    private int foodQuantity;

    public FoodSource(Point point, World world) {
        super(Color.GREEN, point, EntityParams.Sizes.FOOD_SOURCE, world, EntityTypes.OBJECT);
        foodQuantity = 100000;
    }
    
    public FoodSource(World world) {
        super(Color.GREEN, EntityParams.Sizes.FOOD_SOURCE, world, EntityTypes.OBJECT);
        foodQuantity = 100000;
    }

    public Food getFood() {
        if (foodQuantity < 0) {
            throw new RuntimeException("No food in foodsource");
        }

        foodQuantity--;
        if(foodQuantity == 0) setToRemove();
        return new Food();
    }       

    public boolean hasFood() {
        return foodQuantity > 0;
    }
}
