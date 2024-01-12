package Model.Entity.Food;

import java.awt.Color;
import java.awt.Point;
import java.util.Random;

import Model.World;
import Model.Entity.Entity;
import Util.Enum.EntityTypes;
import Util.Static.EntityParams;

/**
 * Источник пищи
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
     * Получить еду из текущего источника пищи, если еда заканчивается - помечает 
     * источник на удаление
     */
    public Food getFood() {
        if (foodQuantity < 0) {
            throw new RuntimeException("No food in foodsource");
        }

        foodQuantity--;
        if(foodQuantity == 0) setToRemove();

        final float MIN_FOODVALUE = 2.2f;
        final float MAX_FOODVALUE = 4.2f;
        return new Food(new Random().nextFloat() * (MAX_FOODVALUE - MIN_FOODVALUE));
    }       

    public boolean hasFood() {
        return foodQuantity > 0;
    }
}
