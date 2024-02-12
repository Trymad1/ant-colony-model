package App.Model.Entity.Ant;

import java.awt.Point;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.List;

import App.Model.World;
import App.Model.Entity.Entity;
import App.Model.Entity.Food.Food;
import App.Model.Interface.Updatable;
import App.Util.Enum.EntityTypes;
import App.Util.Static.Entities;
import App.Util.Static.EntityParams;

    /**
     *The anthill, which stores food and ants, is also responsible for food consumption
    */
public class Anthill extends Entity implements Updatable {

    private float foodQuantity; 
    private final Deque<Ant> ants;
    
    //The total amount of food that will be spent on the next iteration of the world
    private float foodConsumption; 

    //Amount of food spent per ant
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
     * Put food in the anthill
     * @param food
     */
    public void putFood(Food food) {
        foodQuantity += food.getFoodValue();
    }
    
    public void clear() {
        foodQuantity = 0;
    }
    
    /**
     * Add an ant inside the anthill
     * @param ant
     */
    public void addInAnthill(Ant ant) {
        ants.add(ant);
    }

    
    /**
     * Spend a certain amount of food depending on the number of ants
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
        final List<Point> emptyPoints = Entities.findEmptyPoint(getNearPoints());
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
