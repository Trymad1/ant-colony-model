package App.Model.Entity;

import java.awt.Color;
import java.awt.Point;
import java.util.HashMap;

import App.Model.Movable;
import App.Model.Updatable;

public abstract class Creature extends Entity implements Updatable, Movable {

    protected HashMap<String, Point> memory;

    public Creature(Color color, int size) {
        super(color, size);
    }

    public Creature(Color color, Point point, int size) {
        super(color, point, size);
    }
    
    @Override
    public void move(Point point) {
        
    }
}
