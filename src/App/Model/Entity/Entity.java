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

public abstract class Entity {

    protected Color color;
    protected Point point;
    protected int size;
    protected World world;

    public Entity(Color color, int size, World world) {
        this.color = color;
        this.size = size;
        this.world = world;
        point = new Point();
    }

    public Entity(Color color, Point point, int size, World world) {
        this(color, size, world);
        this.point = point;
    }

    public Map<Point, Optional<Entity>> getNearPoints() {
        final List<Point> possiblePoints = new ArrayList<>();

        int intend = (size / 2) + 1;

        // При четном значении левая верхняя граница не должна меняться,
        // поэтому при вычислении добавляется единица, компенсирующая лишний отступ
        final Point leftTop = size % 2 == 0 ?
            new Point((point.x - intend) + 1, (point.y - intend) + 1) :
            new Point(point.x - intend, point.y - intend);
            
        final Point leftBottom = new Point(leftTop.x, leftTop.y + (size + 1));
        final Point rightTop = new Point(leftTop.x + (size + 1), leftTop.y);
        final Point rightBottom = new Point(leftTop.x + (size + 1), leftTop.y + (size + 1));

        possiblePoints.add(leftTop);
        possiblePoints.add(rightBottom);
        possiblePoints.add(rightTop);
        possiblePoints.add(leftBottom);

        for (int i = 1; i < size + 1; i++) {
            possiblePoints.add(new Point(leftTop.x + i, leftTop.y));
            possiblePoints.add(new Point(leftTop.x, leftTop.y + i));
            possiblePoints.add(new Point(rightBottom.x - i, rightBottom.y));
            possiblePoints.add(new Point(rightBottom.x, rightBottom.y - i));
        }

        final Map<Point, Optional<Entity>> nearPoints = new HashMap<>();
        final Dimension worldSize = world.getWorldSize();

        possiblePoints.stream()
        .filter(point -> (point.x < worldSize.width && point.x >= 0) &&
                          point.y < worldSize.height && point.y >= 0)
        .forEach(point -> nearPoints.put(
            point, Optional.ofNullable(world.getEntities().get(point))));
        
        return nearPoints;
    }

    public Color getColor() {
        return color;
    }

    public Point getPoint() {
        return point;
    }

    public void setColor(Color color) {
        this.color = color; 
    }

    public void setPoint(Point point) {
        this.point = point;
    }

    public int getSize() {
        return size;
    }
}
