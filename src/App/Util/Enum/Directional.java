package App.Util.Enum;

import java.awt.Point;

//List of possible directions. Each direction has 3 points corresponding to them, and values,
//corresponding to how the coordinates of a point will change if it moves along given directions
//The class is intended only for objects and entities of size 1.
public enum Directional {
    NORTH(new Point[]{new Point(0, -1), new Point(-1, -1), new Point(1, -1)}),
    EAST(new Point[]{new Point(1, 0), new Point(1, -1), new Point(1,1)}), 
    SOUTH(new Point[]{new Point(0,1), new Point(1,1), new Point(-1,1)}), 
    WEST(new Point[]{new Point(-1, 0), new Point(-1, 1), new Point(-1, -1)}),
    NORTH_EAST(new Point[]{new Point(1,-1), new Point(0,-1), new Point(1,0)}), 
    SOUTH_EAST(new Point[]{new Point(1, 1), new Point(1,0), new Point(0,1)}),
    SOUTH_WEST(new Point[]{new Point(-1,1), new Point(0,1), new Point(-1,0)}),
    NORTH_WEST(new Point[]{new Point(-1,-1), new Point(-1,0), new Point(0,-1)});
    
    public Point[] pointInfo;

    private Directional(Point[] direction) {
        this.pointInfo = direction;
    }

    /**
    *@return -opposite direction to the current one
    */
    public Directional getOppositeDirectional() {
        switch (this) {
            case NORTH:
                return SOUTH;
            case SOUTH:
                return NORTH;

            case EAST:
                return WEST;
            case WEST:
                return EAST;

            case NORTH_EAST:
                return SOUTH_WEST;
            case SOUTH_WEST:
                return NORTH_EAST;

            case SOUTH_EAST:
                return NORTH_WEST;
            case NORTH_WEST:
                return SOUTH_EAST;

            default: 
                return this;
        }
    }
}
