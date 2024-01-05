package App.Util;

import java.awt.Color;

public abstract class EntityParams {

    public static class Colors {

        public static Color ANT_COLLECTOR = Color.BLACK;

        public static Color ANT_SOLDIER = Color.MAGENTA;

        public static Color ANT_SCOUT = Color.ORANGE;

        public static Color ANTHILL = Color.GRAY;

        public static Color FOOD = Color.GREEN;

        public static Color PHEROMONE_TO_ANTHILL = Color.BLUE;

        public static Color PHEROMONE_TO_TARGET = Color.GRAY;
    }

    public static class Sizes {

        public static int ANT = 1;

        public static int ANTHILL = 3;

        public static int FOOD_SOURCE = 2;

        public static int PHEROMONE = 1;
    }
}
