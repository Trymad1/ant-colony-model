package App.Model.Entity;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Point;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import App.Model.World;
import App.Model.Entity.Pheromone.PheromoneUtil;
import App.Util.EntityTypes;

public abstract class Entity {
    // todo
    protected Color color;
    protected Point point;
    protected int size;

    private World world;
    private EntityTypes entityType;

    public Entity(Color color, int size, World world, EntityTypes entityType) {
        this.color = color;
        this.size = size;
        this.world = world;
        this.entityType = entityType;
        this.point = null;
    }

    public Entity(Color color, Point point, int size, World world, EntityTypes entityType) {
        this(color, size, world, entityType);
        this.point = point;
    }

    public Map<Point, Optional<Entity>> getNearPoints() {
        final List<Point> possiblePoints = new ArrayList<>();

        int intend = (size / 2) + 1;

        // При четном значении левая верхняя граница не должна увеличиваться,
        // поэтому при вычислении добавляется единица, компенсирующая лишний отступ
        final Point leftTop = size % 2 == 0 ?
            new Point((point.x - intend + 1), (point.y - intend) + 1) :
            new Point(point.x - intend, point.y - intend);
            
        final Point leftBottom = new Point(leftTop.x, (leftTop.y + (size + 1)));
        final Point rightTop = new Point(leftTop.x + (size + 1), leftTop.y);
        final Point rightBottom = new Point(leftTop.x + (size + 1), leftTop.y + (size + 1));

        possiblePoints.add(leftTop);
        possiblePoints.add(rightBottom);
        possiblePoints.add(rightTop); 
        possiblePoints.add(leftBottom);
            
        for (int j = 1; j < size + 1; j++) {
            possiblePoints.add(new Point(leftTop.x + j, leftTop.y));
            possiblePoints.add(new Point(leftTop.x, leftTop.y + j));
            possiblePoints.add(new Point(rightBottom.x - j, rightBottom.y));
            possiblePoints.add(new Point(rightBottom.x, rightBottom.y - j));
        }

        final Map<Point, Optional<Entity>> nearPoints = new HashMap<>();
        final Dimension worldSize = world.getWorldSize();

        possiblePoints.stream()
        .filter(point -> (point.x < worldSize.width && point.x >= 0) &&
                          point.y < worldSize.height && point.y >= 0)
        .forEach(point -> nearPoints.put(
            point, Optional.ofNullable(world.getAllEntities().get(point))));
        
        return nearPoints;
    }

    protected PheromoneUtil getPheromoneUtil() {
        return world.getPheromoneUtil();
    }

    public void setPoint(Point point) {
        world.relocateEntity(this, point);
        this.point = point;
    }

    public void setToRemove() {
        world.addToRemoveEntity(this);
    }

    public Color getColor() {
        return color;
    }

    public Point getPoint() {
        return point;
    }

    public int getSize() {
        return size;
    }

    public EntityTypes getEntityType() {
        return entityType;
    }

}
