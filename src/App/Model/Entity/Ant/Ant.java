package App.Model.Entity.Ant;

import java.awt.Color;
import java.awt.Point;
import java.util.Queue;

import App.Model.CanTake;
import App.Model.Entity.Creature;
import App.Model.Entity.Entity;

public abstract class Ant extends Creature implements CanTake {

    protected Queue<Entity> takedItem;

    public Ant(Color color, Point point) {
        super(color, point, 1);
    }

    public Ant(Color color) {
        super(color, 1);
    }

    @Override
    public void take(Entity entity) {
        // TODO
    }

    protected abstract Point findWay();

    @Override
    public void update() {
        move(findWay());
    }
}
