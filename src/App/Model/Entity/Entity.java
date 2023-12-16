package App.Model.Entity;

import java.awt.Color;
import java.awt.Point;

public abstract class Entity {

    protected Color color;
    protected Point point;
    protected int size;

    public Entity(Color color, int size) {
        this.color = color;
        this.size = size;
        point = new Point(0, 0);
    }

    public Entity(Color color, Point point, int size) {
        this(color, size);
        this.point = point;
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
