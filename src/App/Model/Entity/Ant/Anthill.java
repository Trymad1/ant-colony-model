package App.Model.Entity.Ant;

import java.awt.Point;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.List;
import java.util.Queue;

import App.Model.World;
import App.Model.Entity.Entity;
import App.Model.Entity.Food.Food;
import App.Model.Interface.Updatable;
import App.Util.Entities;
import App.Util.EntityParams;
import App.Util.EntityTypes;

public class Anthill extends Entity implements Updatable {

    private float foodQuantity; 
    private final Deque<Ant> ants;
    private float foodConsumption;
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

    public float getFoodQuantity() {
        return foodQuantity;
    }

    public void putFood(Food food) {
        foodQuantity += food.getFoodValue();
    }

    public void clear() {
        foodQuantity = 0;
    }

    public void addInAnthill(Ant ant) {
        ants.add(ant);
    }

    public float getFoodConsumption() {
        return foodConsumption;
    }

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
}
