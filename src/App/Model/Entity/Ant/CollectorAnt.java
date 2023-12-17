package App.Model.Entity.Ant;

import java.awt.Color;
import java.awt.Point;
import java.util.List;

import App.Model.World;
import App.Util.EntityParams;

public class CollectorAnt extends Ant {

    public CollectorAnt(Point point, World world) {
        super(EntityParams.Colors.ANT_COLLECTOR, point, world);
    }
    
    public CollectorAnt(World world) {
        super(Color.DARK_GRAY, world);
    }

    @Override
    protected Point findWay() {
        return null; // TODO
    }

}
