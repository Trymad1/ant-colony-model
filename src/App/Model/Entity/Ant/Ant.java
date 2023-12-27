package App.Model.Entity.Ant;

import java.awt.Color;
import java.awt.Point;
import java.util.Optional;

import App.Model.CanTake;
import App.Model.World;
import App.Model.Entity.Creature;
import App.Model.Entity.Entity;
import App.Util.EntityParams;

public abstract class Ant extends Creature implements CanTake {

    protected Optional<Entity> takedItem;

    public Ant(Color color, Point point, World world) {
        this(color, world);
        setPoint(point);
    }

    public Ant(Color color, World world) {
        super(color, EntityParams.Sizes.ANT, world);
    }

    @Override
    public void take(Entity entity) {
        // TODO
    }

    protected abstract Optional<Point> findWay();

    @Override
    public void update() {
        if (findWay().isPresent()) move(findWay().get());
    }
}
