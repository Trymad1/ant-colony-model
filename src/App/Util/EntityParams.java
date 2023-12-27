package App.Util;

import java.awt.Color;

public abstract class EntityParams {

    public static class Colors {

        public static Color ANT_COLLECTOR = Color.BLUE;

        public static Color ANT_SOLDIER = Color.MAGENTA;

        public static Color ANT_SCOUT = Color.ORANGE;

        public static Color ANTHILL = Color.GRAY;

        public static Color FOOD = Color.GREEN;
    }

    public static class Sizes {

        public static int ANT = 1;

        public static int ANTHILL = 3;

        public static int FOOD_SOURCE = 2;

        public static int FOOD = 1;
    }

    public static class Names {

        public static String ATHILL = "ANTHILL";

        public static String ANT_COLLECTOR = "ANT_COLLECTOR";
        
        public static String ANT_SCOUT = "ANT_SCOUT";

        public static String FOOD_SOURCE = "FOOD_SOURCE";

        public static String FOOD = "FOOD";
    }
}
