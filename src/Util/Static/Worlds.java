package Util.Static;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import Model.Entity.Entity;

public abstract class Worlds {

    /**
     * Получить список точек из опредленной области
     * @param leftTop - верхний левый угол
     * @param bottomRight - нижний правый угол
     * @return - список всех сущностей, входящих в данную область
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
     * Получить список точек на которых не расположены сущности
     * @param leftTop -
     * @param bottomRight
     * @param entities - список сущностей, расположенных в мире
     * @return - список пустых точек
     */
    public static List<Point> getAreaWithEmptyPoint(
        Point leftTop, Point bottomRight, Map<Point, Entity> entities) {
        final List<Point> area = getArea(leftTop, bottomRight);

        return getEmptyPointFromArea(area, entities);
    }

    /**
     * 
     * @param area - список точек, из которых нужно получить пустые точки
     * @param entities - список сущностей, расположенных в мире
     * @return - пустые точки, на которых не расположенны сущности
     */
    public static List<Point> getEmptyPointFromArea(
        List<Point> area, Map<Point, Entity> entities) {
        
        return area.stream()
        .filter(point -> (entities.get(point) == null))
        .collect(Collectors.toList());
    }
}
