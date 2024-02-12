package App.Util.Static;

import java.awt.Point;

import App.Util.Enum.Directional;


public abstract class Directionals {

    private final static Directional[] allDirectionals = new Directional[]
        {Directional.NORTH, Directional.EAST,Directional.SOUTH,Directional.WEST,
            Directional.NORTH_EAST,Directional.SOUTH_EAST, 
            Directional.SOUTH_WEST, Directional.NORTH_WEST};

    /**
    *@return -random direction
    */
    public static Directional getRandomDirectional() {
        int randomDirectional = (int) (Math.random() * allDirectionals.length);
        
        return allDirectionals[randomDirectional];
    }

    /**
     * 
     * @param pointDirectional the difference between the current and the desired point to move, required
     * to determine the direction in which the movement will occur 
     * @return - new direction of movement
     */
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
