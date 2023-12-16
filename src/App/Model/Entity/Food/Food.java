package App.Model.Entity.Food;

import java.awt.Color;
import java.awt.Point;

import App.Model.Entity.Entity;

public class Food extends Entity {

    public Food(Point point) {
        super(Color.GREEN, point, 1);
    }
    
    public Food() {
        super(Color.GREEN, 1);
    }
}
