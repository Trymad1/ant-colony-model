package App.Model.Entity.Ant;

import java.awt.Color;
import java.awt.Point;

import App.Util.EntityParams;

public class SoldierAnt extends Ant {

    public SoldierAnt(Point point) {
        super(EntityParams.Colors.ANT_SOLDIER, point);
    }
    
    public SoldierAnt() {
        super(Color.ORANGE);
    }

    @Override
    protected Point findWay() {
        return null; // TODO
    }
}
