package App.Model.Entity.Ant;

import java.awt.Color;
import java.awt.Point;
import java.util.List;

import App.Model.World;
import App.Util.EntityParams;

public class SoldierAnt extends Ant {

    public SoldierAnt(Point point, World world) {
        super(EntityParams.Colors.ANT_SOLDIER, point, world);
    }
    
    public SoldierAnt(World world) {
        super(Color.ORANGE, world);
    }

    @Override
    protected Point findWay() {
        return null; // TODO
    }
}
