package App.Util;

import java.awt.Color;
import java.awt.Point;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import App.Model.Entity.Entity;

public abstract class Entities {
    public static List<Point> findEmptyPoint(Map<Point, Optional<Entity>> nearPoint) {
        return nearPoint.entrySet().stream()
            .filter(entrySet -> !entrySet.getValue().isPresent())
            .map(entrySet -> entrySet.getKey())
            .collect(Collectors.toList());
    }

    public static List<Entity> findNearEntity(Map<Point, Optional<Entity>> nearPoint) {
        return nearPoint.entrySet().stream()
            .filter(entrySet -> entrySet.getValue().isPresent())
            .map(entrySet -> entrySet.getValue().get())
            .collect(Collectors.toList());
    }

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
