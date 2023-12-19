package App.Model.Entity.Ant;

import java.awt.Color;
import java.awt.Point;
import java.util.List;
import java.util.Map;
import java.util.Optional;


import App.Model.World;
import App.Model.Entity.Entity;
import App.Util.Entities;
import App.Util.EntityParams;

public class SoldierAnt extends Ant {

    public SoldierAnt(Point point, World world) {
        super(EntityParams.Colors.ANT_SOLDIER, point, world);
    }
    
    public SoldierAnt(World world) {
        super(EntityParams.Colors.ANT_SOLDIER, world);
    }

    @Override //TODO
    protected Optional<Point> findWay() {
        Map<Point, Optional<Entity>> nearPoints = this.getNearPoints();
        if (nearPoints.size() == 0) return Optional.ofNullable(null);

        List<Point> emptyPoints = Entities.findEmptyPoint(nearPoints);
        if (emptyPoints.size() == 0) return Optional.ofNullable(null);

        int nextPosition = (int) (Math.random() * emptyPoints.size());
        return Optional.of(new Point(emptyPoints.get(nextPosition)));
    }
}
