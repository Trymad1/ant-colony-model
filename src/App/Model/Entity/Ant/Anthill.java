package App.Model.Entity.Ant;

import java.awt.Point;
import java.util.Queue;

import App.Model.Updatable;
import App.Model.Entity.Entity;
import App.Util.EntityParams;

public class Anthill extends Entity implements Updatable {

    private float foodQuantity; 
    

    public Anthill(Point point) {
        super(EntityParams.Colors.ANTHILL, point, EntityParams.Sizes.ANTHILL);
    }

    public Anthill() {
        super(EntityParams.Colors.ANTHILL, EntityParams.Sizes.ANTHILL);
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
