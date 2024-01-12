package Util.Static;

import java.awt.Point;

import Util.Enum.Directional;


public abstract class Directionals {

    private final static Directional[] allDirectionals = new Directional[]
        {Directional.NORTH, Directional.EAST,Directional.SOUTH,Directional.WEST,
            Directional.NORTH_EAST,Directional.SOUTH_EAST, 
            Directional.SOUTH_WEST, Directional.NORTH_WEST};

    /**
     * @return - случайное направление
     */
    public static Directional getRandomDirectional() {
        int randomDirectional = (int) (Math.random() * allDirectionals.length);
        
        return allDirectionals[randomDirectional];
    }

    /**
     * 
     * @param pointDirectional разница между текущей и желаемой для перемещения точкой, необходимой
     * // для опредленения направления в котором будет совершенно движение 
     * @return - новое направление движения
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
