package App.Util.Static;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import App.Model.Entity.Entity;

public abstract class Worlds {

    /**
    *Get a list of points from a specific area
    *@param leftTop -top left corner
    *@param bottomRight -bottom right corner
    *@return -list of all entities included in this area
    */
    public static List<Point> getArea(Point leftTop, Point bottomRight) {
        final List<Point> area = new ArrayList<>();
        for (int i = leftTop.x; i <= bottomRight.x; i++) {
            for (int j = leftTop.y; j <= bottomRight.y; j++) {
                area.add(new Point(i, j));
            }
        }
        return area;
    }
    
    /**
    *Get a list of points where no entities are located
    *@param leftTop -
    *@param bottomRight
    *@param entities -list of entities located in the world
    *@return -list of empty points
    */
    public static List<Point> getAreaWithEmptyPoint(
        Point leftTop, Point bottomRight, Map<Point, Entity> entities) {
        final List<Point> area = getArea(leftTop, bottomRight);

        return getEmptyPointFromArea(area, entities);
    }

    /**
    *
    *@param area -list of points from which empty points should be obtained
    *@param entities -list of entities located in the world
    *@return -empty points where no entities are located
    */
    public static List<Point> getEmptyPointFromArea(
        List<Point> area, Map<Point, Entity> entities) {
        
        return area.stream()
        .filter(point -> (entities.get(point) == null))
        .collect(Collectors.toList());
    }
}
