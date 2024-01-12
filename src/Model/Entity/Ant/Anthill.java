package Model.Entity.Ant;

import java.awt.Point;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.List;

import Model.World;
import Model.Entity.Entity;
import Model.Entity.Food.Food;
import Model.Interface.Updatable;
import Util.Enum.EntityTypes;
import Util.Static.Entities;
import Util.Static.EntityParams;

/**
 * Муравейник, хранящий в себе еду и муравьев, так же отвечает за расход еды
 */
public class Anthill extends Entity implements Updatable {

    private float foodQuantity; 
    private final Deque<Ant> ants;
    
    // Суммарное число еды, которое будет потрачено на следующей итерации мира
    private float foodConsumption; 

    // Количество потраченной еды на одного муравья
    private float foodConsumptionCoeff;

    public Anthill(Point point, World world, float foodQuantity, float foodConsuptionCoeff) {
        super(EntityParams.Colors.ANTHILL, point, EntityParams.Sizes.ANTHILL, world, EntityTypes.ANTHILL);
        this.foodQuantity = foodQuantity;
        this.foodConsumptionCoeff = foodConsuptionCoeff;
        ants = new ArrayDeque<>();
    }

    public Anthill(World world) {
        super(EntityParams.Colors.ANTHILL, EntityParams.Sizes.ANTHILL, world, EntityTypes.ANTHILL);
        this.foodQuantity = 100;
        ants = new ArrayDeque<>();
    }

    /**
     * Положить еду в муравейник
     * @param food
     */
    public void putFood(Food food) {
        foodQuantity += food.getFoodValue();
    }
    
    public void clear() {
        foodQuantity = 0;
    }
    
    /**
     * Добавить муравья внутрь муравейника
     * @param ant
     */
    public void addInAnthill(Ant ant) {
        ants.add(ant);
    }

    
    /**
     * Потратить определенное количество еды, зависящее от количества муравьев
     * @param antQuantity
     */
    public void foodConsum(int antQuantity) {
        foodConsumption = foodConsumptionCoeff * antQuantity;
        foodQuantity -= foodConsumption;
        if (foodQuantity < 0) {
            foodQuantity = 0;
        }
    }

    @Override
    public void update() {
        List<Point> emptyPoints = Entities.findEmptyPoint(getNearPoints());
        emptyPoints.forEach(point -> {
            final Ant ant = ants.poll();
            if(ant == null) return;
            ant.setPoint(point);
            ant.setInAnthill(false);
        });
        
    }

    public float getFoodConsumption() {
        return foodConsumption;
    }

    public float getFoodQuantity() {
        return foodQuantity;
    }
}
