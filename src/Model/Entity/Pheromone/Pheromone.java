package Model.Entity.Pheromone;

import java.awt.Color;
import java.awt.Point;

import Model.World;
import Model.Entity.Entity;
import Util.Enum.EntityTypes;

/**
 * Класс, описывающий любой феромон и его свойства
 */
public abstract class Pheromone extends Entity implements Comparable<Integer> {

    // Значением феромона является количество точек, которые муравей
    // прошел от своего начала. Каждый муравей обновляет значение феромона
    // на точке, где он находится, но феромон запоминает только самое меньшее переданное значение,
    // т.е самое меньшее количество шагов, необходимое чтобы достичь точки из которой дошел муравей.
    // Таким образом муравьи находят кратчаший путь от еды к дому и наоборот, следуя точкам с наименьшим растоянием от цели
    protected int pheromoneValue;

    public Pheromone(
        Color color, Point point, int size, World world, EntityTypes entityType, int pheromoneValue) {
        super(color, point, size, world, entityType);
        this.pheromoneValue = pheromoneValue;
        this.point = point;
    }

    public Pheromone(Color color, int size, World world, EntityTypes entityType, int pheromoneValue) {
        super(color, size, world, entityType);
        this.pheromoneValue = pheromoneValue;
    }

    /**
     * @param newPheromoneValue - новое значение для феромона
     */
    public abstract void setPheromoneValue(int newPheromoneValue);
    
    /*
     * Определяет будет ли феромон испаряться, и то как это будет происходить
     */
    public abstract void evaporate();

    public int getPheromoneValue() {
        return pheromoneValue;
    }

    @Override
    public String toString() {
        return String.valueOf(pheromoneValue);
    }

    /*
     * Метод для сравнения двух феромонов
     */
    @Override
    public int compareTo(Integer o) {
        return Integer.compare(this.pheromoneValue, o);
    }
}
