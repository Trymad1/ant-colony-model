package Model.Entity.Ant;

import java.awt.Color;
import java.awt.Point;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import Model.World;
import Model.Entity.Creature;
import Model.Interface.CanTake;
import Model.Interface.Item;
import Util.Enum.Directional;
import Util.Enum.PheromoneTypes;
import Util.Static.Directionals;
import Util.Static.EntityParams;

/*
 * Описывает основные свойства и способности любого муравья
 */
public abstract class Ant extends Creature implements CanTake {

    protected Optional<Item> takedItem;

    // Направление движение, используя только когда муравьи передвигаются случайно
    // Для более естественного передвиждения
    protected Directional directional; 
    protected boolean inAnthill;

    protected int pheromoneValue;
    protected final int START_PHEROMONE_VALUE = 1;

    public Ant(Color color, Point point, World world) {
        this(color, world);
        setPoint(point);
    }

    public Ant(Color color, World world) {
        super(color, EntityParams.Sizes.ANT, world);
        takedItem = Optional.ofNullable(null);
        directional = Directionals.getRandomDirectional();
        inAnthill = true;
    }

    /**
     * @param item - Предмет, который будет подобран
     */
    @Override
    public void take(Item item) {
        takedItem = Optional.of(item);
        color = Color.GREEN;
    }

    /**
     * Описывает алгоритм, определяющий следующую точку на которую муравей захочет переместиться, а так же его действия
     * такие как подбор предметов
     * @retun Точка, на которую захочет переместиться муравей, может быть null, если муравей совершил действие или ему некуда переместиться
     */
    protected abstract Optional<Point> findPointToMove();

    /**
     * Метод, вызываемый при каждой итерации мира
     */
    @Override
    public void update() {
        if(inAnthill == true) return;
        final Optional<Point> pointToMove = findPointToMove();
        if (pointToMove.isPresent()) {
            dropPheromone(point);
            move(pointToMove.get());
        } else {
            this.directional = Directionals.getRandomDirectional();
        };
    }

    /**
     * Оставляет феромон на переданной точке
     * @param point - точка на которой будет оставлен феромон
     */
    protected void dropPheromone(Point point) {
        if (pheromoneValue > 200) {
            return;
        }
        final PheromoneTypes pheromoneType = getDroppedPheromone();
        super.getPheromoneUtil().dropPheromone(pheromoneType, point, pheromoneValue);
        pheromoneValue++;
    } 
 
    /**
     * 
     * @param points - список соседних точек муравья
     * @return - список точек, которые находятся на траектории движения муравья
     */
    protected List<Point> getPointOnDirectional(List<Point> points) {
        final Point[] pointInfo = directional.pointInfo;
        return points.stream()
        .filter(p -> (p.x == this.point.x + pointInfo[0].x &&
                      p.y == this.point.y + pointInfo[0].y)
                      ||
                      (p.x == this.point.x + pointInfo[1].x &&
                      p.y == this.point.y + pointInfo[1].y)
                      ||
                      (p.x == this.point.x + pointInfo[2].x &&
                      p.y == this.point.y + pointInfo[2].y)
                      )
        .collect(Collectors.toList());
    }

    /*
     * Получает новое направление для муравья, в соответствии с точкой, на которую он перейдет
     * направление меняется если муравей пошел не по прямой, а сменил угол движения
     */
    protected Directional getNewDirectional(Point pointToMove) {
        final Point pointDirectional = 
            new Point(pointToMove.x - this.point.x, pointToMove.y - this.point.y);
        return Directionals.getDirectional(pointDirectional);
    } 

    /**
     * Возвращает тип феромона, который муравей должен оставлять при движении
     * @return - тип феромона
     */
    protected PheromoneTypes getDroppedPheromone() {
        return takedItem.isPresent() ? 
            PheromoneTypes.TO_TARGET : PheromoneTypes.TO_ANTHILL;
    }

    /**
     * Возвращает тип феромона, по которому муравей должен сделать
     * @return - тип феромона
     */
    protected PheromoneTypes getTargetPheromone() {
        return takedItem.isPresent() ? 
            PheromoneTypes.TO_ANTHILL : PheromoneTypes.TO_TARGET;
    }

    protected boolean inAnthill() {
        return inAnthill;
    }

    protected void setInAnthill(boolean inAnthill) {
        this.inAnthill = inAnthill;
    }
}
