package Model.Entity;

import java.awt.Color;
import java.awt.Point;

import Model.World;
import Model.Interface.Movable;
import Model.Interface.Updatable;
import Util.Enum.EntityTypes;

/**
 * Обьект, описывающий любые живые сущности в мире, способные ходить и обновлять свое состояние
 * каждую итерацию мира
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
