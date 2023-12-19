package App.Util;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import App.Model.Entity.Entity;

public abstract class Worlds {

    public static List<Point> getArea(Point leftTop, Point bottomRight) {
        final List<Point> area = new ArrayList<>();
        for (int i = leftTop.x; i <= bottomRight.x; i++) {
            for (int j = leftTop.y; j <= bottomRight.y; j++) {
                area.add(new Point(i, j));
            }
        }
        return area;
    }
        
    public static List<Point> getAreaWithEmptyPoint(
        Point leftTop, Point bottomRight, Map<Point, Entity> entities) {
        final List<Point> area = getArea(leftTop, bottomRight);

        return getEmptyPointFromArea(area, entities);
    }

    public static List<Point> getEmptyPointFromArea(
        List<Point> area, Map<Point, Entity> entities) {
        
        return area.stream()
        .filter(point -> (entities.get(point) == null))
        .collect(Collectors.toList());
    }
}
