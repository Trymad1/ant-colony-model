package App.Util.Static;

import java.awt.Color;
import java.awt.Point;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import App.Model.Entity.Entity;

public abstract class Entities {

    /**
    *@param nearPoint -list of neighboring points of the entity
    *@return -points free to move
    */
    public static List<Point> findEmptyPoint(Map<Point, Optional<Entity>> nearPoint) {
        return nearPoint.entrySet().stream()
            .filter(entrySet -> !entrySet.getValue().isPresent())
            .map(entrySet -> entrySet.getKey())
            .collect(Collectors.toList());
    }

    /**
    *
    *@param nearPoint -list of neighboring points of the entity
    *@return -points where entities are located
    */
    public static List<Entity> findNearEntity(Map<Point, Optional<Entity>> nearPoint) {
        return nearPoint.entrySet().stream()
            .filter(entrySet -> entrySet.getValue().isPresent())
            .map(entrySet -> entrySet.getValue().get())
            .collect(Collectors.toList());
    }

    /**
    *
    *@param entityPoint -the point at which the entity is located
    *@param size -entity size
    *@return -a list of points that the entity will occupy on the map, depending on its size
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
    *Returns an entity of a certain type from a list of neighboring points, if it exists
    *@param nearPoints -list of neighboring points
    *@param entityColor -the color of the searched entity
    *@param entityClass -the class that represents the entity being searched for
    *@return -Optional with the entity being searched for
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
