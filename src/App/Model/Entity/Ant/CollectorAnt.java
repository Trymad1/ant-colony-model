package App.Model.Entity.Ant;

import java.awt.Point;
import java.util.List;
import java.util.Optional;

import App.Model.World;
import App.Util.Entities;
import App.Util.EntityParams;
import App.Util.PheromoneTypes;   

public class CollectorAnt extends Ant {

    public CollectorAnt(Point point, World world) {
        super(EntityParams.Colors.ANT_COLLECTOR, point, world);
    }
    
    public CollectorAnt(World world) {
        super(EntityParams.Colors.ANT_COLLECTOR, world);    
    }

    @Override
    protected Optional<Point> findWay() {
        List<Point> emptyPoint = Entities.findEmptyPoint(getNearPoints());
        int random = (int) (Math.random() * emptyPoint.size());
        if (emptyPoint.size() == 0) return Optional.ofNullable(null);

        final PheromoneTypes type = takedItem.isPresent() ? 
            PheromoneTypes.TO_TARGET : PheromoneTypes.TO_ANTHILL;
        super.getPheromoneUtil().addPheromone(type, point);
    
        return Optional.of(emptyPoint.get(random));
    }
}
