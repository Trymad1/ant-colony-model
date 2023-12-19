package App.Util;

import java.awt.Point;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import App.Model.Entity.Entity;

public abstract class Entities {
    public static List<Point> findEmptyPoint(Map<Point, Optional<Entity>> nearPoint) {
        return nearPoint.entrySet().stream()
            .filter(entrySet -> entrySet.getValue().isEmpty())
            .map(entrySet -> entrySet.getKey())
            .collect(Collectors.toList());
    }

    public static List<Entity> findNearEntity(Map<Point, Optional<Entity>> nearPoint) {
        return nearPoint.entrySet().stream()
            .filter(entrySet -> entrySet.getValue().isPresent())
            .map(entrySet -> entrySet.getValue().get())
            .collect(Collectors.toList());
    }
}
