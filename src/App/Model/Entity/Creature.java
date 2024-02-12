package App.Model.Entity;

import java.awt.Color;
import java.awt.Point;

import App.Model.World;
import App.Model.Interface.Movable;
import App.Model.Interface.Updatable;
import App.Util.Enum.EntityTypes;

/**
*An object that describes any living entities in the world that are capable of walking and updating their state
*every iteration of the world
*/
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
