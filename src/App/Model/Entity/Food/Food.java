package App.Model.Entity.Food;

import App.Model.Interface.Item;

public class Food implements Item {

    private float foodValue;

    public Food(float foodValue) {
        this.foodValue = foodValue;
    }

    public float getFoodValue() {
        return foodValue;
    }
}
