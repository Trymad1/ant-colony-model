package App.UI;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.util.Map;

import javax.swing.JPanel;

import App.Model.Updatable;
import App.Model.World;
import App.Model.Entity.Entity;
import App.Model.Entity.Pheromone.Pheromone;
import App.Util.WorldPaintMode;

public class WorldPanel extends JPanel implements Updatable {

    private World world;
    private WorldPaintUtil worldPaintUtil;
    private WorldPaintMode worldPaintMode;

    public WorldPanel(World world) {
        super();
        this.world = world;
        this.worldPaintUtil = new WorldPaintUtil();
        worldPaintMode = WorldPaintMode.ALL_ENTITIES;
        setOpaque(false);
        setBackground(Color.WHITE);
    }

    public WorldPanel() {
        super();
        this.worldPaintUtil = new WorldPaintUtil();
        worldPaintMode = WorldPaintMode.ALL_ENTITIES;
        setOpaque(false);
        setBackground(Color.WHITE);
    };

    private final int SCALING = 3;
    private final int WORLD_BOUND = 5;

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        final Graphics2D g2d = (Graphics2D) g;    
        
        worldPaintUtil.setPaintMode(worldPaintMode);
        final Map<Point, Entity> entities =   
            worldPaintUtil.getEntityListForDisplay(world);

        final Point nextPoint = new Point(0,0);
        entities.forEach((point, entity) -> {
            if(entity instanceof Pheromone) {
                final Pheromone pheromone = (Pheromone) entity;
                final float alphaValue = 
                    (int) pheromone.getPheromoneValue() >= 1 ? 1f : pheromone.getPheromoneValue();
                g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alphaValue));


            }
            nextPoint.x = (point.x * SCALING) + WORLD_BOUND;
            nextPoint.y = (point.y * SCALING) + WORLD_BOUND;
            g2d.setColor(entity.getColor());
            g2d.fillRect(nextPoint.x, nextPoint.y, SCALING, SCALING);
            g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
        });
        // for (Point iterable_element : entity.getNearPoints().keySet()) {
        //     nextPoint.x = (iterable_element.x * SCALING) + WORLD_BOUND;
        //     nextPoint.y = (iterable_element.y * SCALING) + WORLD_BOUND;
        //     g2d.setColor(Color.RED);
        //     g2d.fillRect(nextPoint.x, nextPoint.y, SCALING, SCALING);
        // }
    }

    public void setWorld(World world) {
        this.world = world;
    }

    public World getWorld() {
        return world;
    }

    public void setWorldPaintMode(WorldPaintMode worldPaintMode) {
        this.worldPaintMode = worldPaintMode;
    }

    @Override
    public void update() {

    }
}
