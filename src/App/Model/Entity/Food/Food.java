package App.Model.Entity.Food;

import java.awt.Color;
import java.awt.Point;

import App.Model.World;
import App.Model.Entity.Entity;
import App.Util.EntityParams;

public class Food extends Entity {

    private int foodValue;

    public Food(World world) {
        super(Color.GREEN, EntityParams.Sizes.FOOD, world);
    }

    public Food(Color color, Point point, int size, World world) {
        super(Color.GREEN, point, EntityParams.Sizes.FOOD, world);
    }

    public int getFoodValue() {
        return foodValue;
    }
}
