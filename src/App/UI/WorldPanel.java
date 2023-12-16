package App.UI;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.util.Map;

import javax.swing.JPanel;

import App.Model.Updatable;
import App.Model.World;
import App.Model.Entity.Entity;
import App.Model.Entity.Ant.Anthill;

public class WorldPanel extends JPanel implements Updatable{

    private World world;

    public WorldPanel(World world) {
        super();
        this.world = world;
    }

    public WorldPanel() {
        super();
    };

    private final int SCALING = 3;
    private final int WORLD_BOUND = 5;

    @Override
    protected void paintComponent(Graphics g) {
        System.out.println(this.getPreferredSize() + "TEST");

        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;    
        
        Map<Point, Entity> entities = world.getEntities();

        final Point nextPoint = new Point(0,0);
        entities.forEach((point, entity) -> {
            nextPoint.x = (point.x * SCALING) + WORLD_BOUND;
            nextPoint.y = (point.y * SCALING) + WORLD_BOUND;
            System.out.println(nextPoint);
            g2d.setColor(entity.getColor());
            g2d.fillRect(nextPoint.x, nextPoint.y, SCALING, SCALING);
        });
    }

    public void setWorld(World world) {
        this.world = world;
    }

    public World getWorld() {
        return world;
    }

    @Override
    public void update() {

    }
}
