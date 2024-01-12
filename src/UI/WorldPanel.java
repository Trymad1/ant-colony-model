package UI;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.util.ConcurrentModificationException;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JPanel;

import Model.World;
import Model.Entity.Entity;
import Model.Entity.Pheromone.Pheromone;
import Model.Interface.Updatable;
import Util.Enum.WorldPaintMode;

/**
 * Расширенная Jpanel, на которой происходит отрисовка действующей модели
 */
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
    // Получение данных с класса World, отрисовка содержимого на панели
    // с масштабированием 3 : 1
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        final Graphics2D g2d = (Graphics2D) g;    
        
        worldPaintUtil.setPaintMode(worldPaintMode);

        Map<Point, Entity> entities = new HashMap<>();
        try {
            entities =   
                worldPaintUtil.getEntityListForDisplay(world);
        } catch (ConcurrentModificationException ignored) {
            // TODO fix bug
            // Изредка в этом участке кода выбрасывается исключение, 
            // из за чего на текущей итерации мира сущности категории
            // creature не удается получить в полном составе. 
            // Так же начинает часто выбрасываться, если
            // поставить метод обновления мира update() в потоке 
            // WorldThread класса Controller раньше отрисовки поля. 
            // Аналогично выбрасывается при высокой скорости воспроизведения
        }

        final Point nextPoint = new Point(0,0);
        entities.forEach((point, entity) -> {
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
