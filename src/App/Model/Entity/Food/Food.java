package App.Model.Entity.Food;

import java.awt.Color;
import java.awt.Point;
import java.util.List;

import App.Model.World;
import App.Model.Entity.Entity;
import App.Util.EntityParams;

public class Food extends Entity {

    public Food(Point point, World world) {
        super(Color.GREEN, point, EntityParams.Sizes.FOOD, world);
    }
    
    public Food(World world) {
        super(Color.GREEN, EntityParams.Sizes.FOOD, world);
    }
}
