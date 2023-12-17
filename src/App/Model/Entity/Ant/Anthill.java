package App.Model.Entity.Ant;

import java.awt.Point;
import java.util.List;
import java.util.Queue;

import App.Model.Updatable;
import App.Model.World;
import App.Model.Entity.Entity;
import App.Util.EntityParams;

public class Anthill extends Entity implements Updatable {

    private float foodQuantity; 
    

    public Anthill(Point point, World world) {
        super(EntityParams.Colors.ANTHILL, point, EntityParams.Sizes.ANTHILL, world);
    }

    public Anthill(World world) {
        super(EntityParams.Colors.ANTHILL, EntityParams.Sizes.ANTHILL, world);
    }

    public float getFoodQuantity() {
        return foodQuantity;
    }

    public boolean putFood(Queue<Entity> food) {
        if(food.peek().getColor().equals(EntityParams.Colors.FOOD)) {
            food.poll();
            return true;
        } else {
            return false;
        }
    }

    public void clear() {
        foodQuantity = 0;
    }

    @Override
    public void update() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'update'");
    }
}
