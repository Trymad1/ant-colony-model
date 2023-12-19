package App.Model.Entity;

import java.awt.Color;
import java.awt.Point;
import java.util.HashMap;
import java.util.Map;

import App.Model.Movable;
import App.Model.Updatable;
import App.Model.World;

public abstract class Creature extends Entity implements Updatable, Movable {

    protected HashMap<String, Point> memory;

    public Creature(Color color, int size, World world) {
        super(color, size, world);
    }

    public Creature(Color color, Point point, int size, World world) {
        super(color, point, size, world);
    }
    
    @Override
    public void move(Point point) {
        final Map<Point, Entity> entities = world.getEntitiesInWorld();
        entities.remove(this.point);
        entities.put(point, this);
        this.point.setLocation(point);
    }
}
