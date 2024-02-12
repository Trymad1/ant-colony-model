package App.UI;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JPanel;

import App.Model.World;
import App.Model.Entity.Entity;
import App.Model.Entity.Pheromone.Pheromone;
import App.Util.Enum.WorldPaintMode;

/**
*Extended Jpanel, on which the actual model is drawn
*/
public class WorldPanel extends JPanel {

    private World world;
    private WorldPaintUtil worldPaintUtil;
    private Map<Point, Entity> entityListForDisplay;

    public WorldPanel(World world) {
        super();
        this.world = world;
        this.worldPaintUtil = new WorldPaintUtil();
        this.entityListForDisplay = new HashMap<>();
        setOpaque(false);
        setBackground(Color.WHITE);
    }

    public WorldPanel() {
        super();
        this.worldPaintUtil = new WorldPaintUtil();
        this.entityListForDisplay = new HashMap<>();
        setOpaque(false);
        setBackground(Color.WHITE);
    };

    private final int SCALING = 3;
    private final int WORLD_BOUND = 5;

    @Override
    //Receiving data from the World class, rendering content on the panel
    //with scaling 3:1
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        final Graphics2D g2d = (Graphics2D) g;    

        final Point nextPoint = new Point(0,0);
        entityListForDisplay.forEach((point, entity) -> {
            if(entity instanceof Pheromone) {
                final Pheromone pheromone = (Pheromone) entity;
                float alphaValue = 0;
                if (pheromone.getPheromoneValue() > 0) {
                    alphaValue = 1 / ((float)pheromone.getPheromoneValue() / 3) > 1 ?
                    1f :  (float) 1 / ((float)pheromone.getPheromoneValue() / 3);
                } else return;
                    
                g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alphaValue));
            }
            nextPoint.x = (point.x * SCALING) + WORLD_BOUND;
            nextPoint.y = (point.y * SCALING) + WORLD_BOUND;
            g2d.setColor(entity.getColor());
            g2d.fillRect(nextPoint.x, nextPoint.y, SCALING, SCALING);
            g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
        });
    }

    public void setEntityListForDisplay(Map<Point, Entity> entityListForDisplay) {
        this.entityListForDisplay = entityListForDisplay;
    }

    public WorldPaintUtil getWorldPaintUtil() {
        return worldPaintUtil;
    }

    public Map<Point, Entity> getEntityListForDisplay() {
        return entityListForDisplay;
    }

    public void setWorld(World world) {
        this.world = world;
    }

    public World getWorld() {
        return world;
    }

    public void repaint(Map<Point, Entity> entityListForDisplay) {
        this.entityListForDisplay = entityListForDisplay;
        repaint();
    }

    public void setWorldPaintMode(WorldPaintMode worldPaintMode) {
        worldPaintUtil.setPaintMode(worldPaintMode);
    }
}
