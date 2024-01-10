package App.Model.Entity;

import java.awt.Color;
import java.awt.Point;

import App.Model.World;
import App.Model.Interface.Movable;
import App.Model.Interface.Updatable;
import App.Util.EntityTypes;

public abstract class Creature extends Entity implements Updatable, Movable {

    public Creature(Color color, int size, World world) {
        super(color, size, world, EntityTypes.CREATURE);
    }

    public Creature(Color color, Point point, int size, World world) {
        super(color, point, size, world, EntityTypes.CREATURE);
    }
    
    @Override
    public void move(Point point) {
        setPoint(point);
    }
}
