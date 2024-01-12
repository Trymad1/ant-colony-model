package Model;

/**
 * Настройки для модели. Создаются при помощи Builder класса.
 */
public class Setting {

    private int antQuant;
    int foodSourceQuant;
    int foodInSourceQuant;
    float startFoodValue;
    float foodConsumption;

    public static final int DEFAULT_ANT_QUANT = 50;
    public static final int DEFAULT_FOOD_SOURCE_QUANT = 6;
    public static final int DEFAULT_FOOD_IN_SOURCE_QUANT = 200;
    public static final float DEFAULT_START_FOOD_VALUE = 100;
    public static final float DEFAULT_FOOD_CONSUMPTION = 0.004f;

    private Setting(int antQuant, int foodSourceQuant, int foodInSourceQuant, 
    float startFoodValue, float foodConsuption) {
        this.antQuant = antQuant;
        this.foodSourceQuant = foodSourceQuant;
        this.foodInSourceQuant = foodInSourceQuant;
        this.startFoodValue = startFoodValue;
        this.foodConsumption = foodConsuption;
    }   

    public void setAntQuant(int antQuant) {
        this.antQuant = antQuant;
    }

    public void setFoodConsumption(float foodConsumption) {
        this.foodConsumption = foodConsumption;
    }

    public void setFoodInSourceQuant(int foodInSourceQuant) {
        this.foodInSourceQuant = foodInSourceQuant;
    }

    public void setFoodSourceQuant(int foodSourceQuant) {
        this.foodSourceQuant = foodSourceQuant;
    }

    public void setStartFoodValue(float startFoodValue) {
        this.startFoodValue = startFoodValue;
    }

    public int getAntQuant() {
        return antQuant;
    }

    public float getFoodConsumption() {
        return foodConsumption;
    }

    public int getFoodInSourceQuant() {
        return foodInSourceQuant;
    }

    public int getFoodSourceQuant() {
        return foodSourceQuant;
    }

    public float getStartFoodValue() {
        return startFoodValue;
    }

    public static SettingBuilder builder() {
        return new SettingBuilder();
    }

    /**
     * Builder класс, который создает экземпляры класса Setting методом build
     */
    public static class SettingBuilder {

        int antQuant;
        int foodSourceQuant;
        int foodInSourceQuant;
        float startFoodValue;
        float foodConsumption;

        public SettingBuilder setAntQuant(int antQuant) {
            this.antQuant = antQuant;
            return this;
        }

        public SettingBuilder setFoodConsumption(float foodConsumption) {
            this.foodConsumption = foodConsumption;
            return this;
        }

        public SettingBuilder setFoodInSourceQuant(int foodInSourceQuant) {
            this.foodInSourceQuant = foodInSourceQuant;
            return this;
        }
        
        public SettingBuilder setFoodSourceQuant(int foodSourceQuant) {
            this.foodSourceQuant = foodSourceQuant;
            return this;
        }

        public SettingBuilder setStartFoodValue(float startFoodValue) {
            this.startFoodValue = startFoodValue;
            return this;
        }

        public Setting build() {
            final int antQuant = this.antQuant;
            final int foodSourceQuant = this.foodSourceQuant;
            final int foodInSourceQuant = this.foodInSourceQuant;
            final float startFoodValue = this.startFoodValue;
            final float foodConsumption = this.foodConsumption;
            
            this.antQuant = 0;
            this.foodSourceQuant = 0;
            this.foodInSourceQuant = 0;
            this.startFoodValue = 0;
            this.foodConsumption = 0;
            
            return new Setting(antQuant, foodSourceQuant, foodInSourceQuant, 
            startFoodValue, foodConsumption);
        }

    public static Setting createDefaultSetting() {
        return new Setting(
        DEFAULT_ANT_QUANT, 
        DEFAULT_FOOD_SOURCE_QUANT, 
        DEFAULT_FOOD_IN_SOURCE_QUANT, 
        DEFAULT_START_FOOD_VALUE, DEFAULT_FOOD_CONSUMPTION);
    }
    }
}
    
