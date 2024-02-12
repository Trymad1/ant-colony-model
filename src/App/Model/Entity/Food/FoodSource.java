package App.Model.Entity.Food;

import java.awt.Color;
import java.awt.Point;
import java.util.Random;

import App.Model.World;
import App.Model.Entity.Entity;
import App.Util.Enum.EntityTypes;
import App.Util.Static.EntityParams;

/**
*Food source
*/
public class FoodSource extends Entity {

    private int foodQuantity;

    public FoodSource(Point point, World world, int foodQuantity) {   
        super(Color.GREEN, point, EntityParams.Sizes.FOOD_SOURCE, world, EntityTypes.OBJECT);
        this.foodQuantity = foodQuantity;
    }

    public FoodSource(Point point, World world) {   
        super(Color.GREEN, point, EntityParams.Sizes.FOOD_SOURCE, world, EntityTypes.OBJECT);
        foodQuantity = 100;
    }

    public FoodSource(World world, int foodQuantity) {
        super(Color.GREEN, EntityParams.Sizes.FOOD_SOURCE, world, EntityTypes.OBJECT);
        this.foodQuantity = foodQuantity;
    }
    
    public FoodSource(World world) {
        super(Color.GREEN, EntityParams.Sizes.FOOD_SOURCE, world, EntityTypes.OBJECT);
        foodQuantity = 100;
    }

    /*
    *Get food from the current food source, if food runs out -flags
    *source to be deleted
    */
    public Food getFood() {
        if (foodQuantity < 0) {
            throw new RuntimeException("No food in foodsource");
        }

        foodQuantity--;
        if(foodQuantity == 0) setToRemove();

        final float MIN_FOODVALUE = 0.2f;
        final float MAX_FOODVALUE = 2.2f;
        return new Food(MIN_FOODVALUE + (new Random().nextFloat() * (MAX_FOODVALUE - MIN_FOODVALUE)));
    }       

    public boolean hasFood() {
        return foodQuantity > 0;
    }
}
