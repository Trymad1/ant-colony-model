package App.Model.Entity.Ant;

import java.awt.Color;
import java.awt.Point;

import App.Util.EntityParams;

public class CollectorAnt extends Ant {

    public CollectorAnt(Point point) {
        super(EntityParams.Colors.ANT_COLLECTOR, point);
    }
    
    public CollectorAnt() {
        super(Color.DARK_GRAY);
    }

    @Override
    protected Point findWay() {
        return null; // TODO
    }

}
