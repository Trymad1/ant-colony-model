package App.Model.Entity.Ant;

import java.awt.Point;
import java.lang.management.GarbageCollectorMXBean;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import App.Model.World;
import App.Model.Entity.Entity;
import App.Util.Entities;
import App.Util.EntityParams;   

public class CollectorAnt extends Ant {

    public CollectorAnt(Point point, World world) {
        super(EntityParams.Colors.ANT_COLLECTOR, point, world);
    }
    
    public CollectorAnt(World world) {
        super(EntityParams.Colors.ANT_COLLECTOR, world);    
    }

    @Override
    protected Optional<Point> findWay() {
        return Optional.ofNullable(null);        
    }
    

}
