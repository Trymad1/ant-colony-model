package Util.Static;

import java.awt.Color;
import java.awt.Point;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import Model.Entity.Entity;

public abstract class Entities {

    /**
     * @param nearPoint - список соседних точек сущности
     * @return - свободные для перемещения точки
     */
    public static List<Point> findEmptyPoint(Map<Point, Optional<Entity>> nearPoint) {
        return nearPoint.entrySet().stream()
            .filter(entrySet -> !entrySet.getValue().isPresent())
            .map(entrySet -> entrySet.getKey())
            .collect(Collectors.toList());
    }

    /**
     * 
     * @param nearPoint - список соседних точек сущности
     * @return - точки, на которых расположены сущнсоти
     */
    public static List<Entity> findNearEntity(Map<Point, Optional<Entity>> nearPoint) {
        return nearPoint.entrySet().stream()
            .filter(entrySet -> entrySet.getValue().isPresent())
            .map(entrySet -> entrySet.getValue().get())
            .collect(Collectors.toList());
    }

    /**
     * 
     * @param entityPoint - точка, на которой расположена сущность
     * @param size - размер сущности
     * @return - список точек, которые сущность будет занимать на карте, в зависимости от своего размера
     */
    public static List<Point> getPointsForEntity(Point entityPoint, int size) {
        final List<Point> entityPoints = new ArrayList<>();
        final Point centeredPoint = new Point();
        if (size > 1) {
            int centeredX = size % 2 == 0 ?
            entityPoint.x - ((size / 2) - 1) :
            entityPoint.x - size / 2;
            
            int centeredY = size % 2 == 0 ?
                entityPoint.y - ((size / 2) - 1) :
                entityPoint.y - size / 2;
            
            centeredPoint.setLocation(centeredX, centeredY);
            for (int i = centeredPoint.x; i < centeredPoint.x + size; i++) {
                for (int j = centeredPoint.y; j < centeredPoint.y + size; j++) {
                    entityPoints.add(new Point(i,j));
                }
            }

        } else {
            centeredPoint.setLocation(entityPoint);
            entityPoints.add(centeredPoint);
        }

        return entityPoints;    
    }

    /**
     * Возвращает сущность опредленного типа из списка соседних точек, если она существует
     * @param nearPoints - список соседних точек
     * @param entityColor - цвет искомой сущности
     * @param entityClass - класс, который представляет искомую сущность
     * @return - Optional с искомой сущностью
     */
    public static <T extends Entity> Optional<T> findTargetEntityNear(
        Map<Point, Optional<Entity>> nearPoints, Color entityColor, Class<T> entityClass) {
        List<Entity> nearEntity = Entities.findNearEntity(nearPoints);

        return nearEntity.stream()
        .filter(currentEntity -> 
            currentEntity.getColor() == entityColor && entityClass.isInstance(currentEntity))
        .map(entityClass::cast)
        .findFirst();
    }
}
