package App.Util;

import java.awt.Point;

public abstract class Directionals {

    private final static Directional[] allDirectionals = new Directional[]
        {Directional.NORTH, Directional.EAST,Directional.SOUTH,Directional.WEST,
            Directional.NORTH_EAST,Directional.SOUTH_EAST, 
            Directional.SOUTH_WEST, Directional.NORTH_WEST};

    public static Directional getRandomDirectional() {
        int randomDirectional = (int) (Math.random() * allDirectionals.length);
        
        return allDirectionals[randomDirectional];
    }

    public static Directional getDirectional(Point pointDirectional) {
        Directional directional = allDirectionals[0];
        for (Directional dir : allDirectionals) {
            Point pointInfoDir = dir.pointInfo[0];
            if(pointDirectional.equals(pointInfoDir)) {
                directional = dir;
            }
        }

        return directional;
    }
}
