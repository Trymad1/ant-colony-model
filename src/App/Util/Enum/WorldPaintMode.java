package App.Util.Enum;

/**
*List of possible rendering modes. Each object has its own flags,
*regulating which objects in the world will be displayed in the current rendering mode
*/
public enum WorldPaintMode {
    ALL_ENTITIES("Standard",true, true, true, false, false), 
    ONLY_OBJECTS("Objects only",true, true, false, false, false), 
    ONLY_CREATURE("Entities only",false, false, true, false, false),
    PHEROMONE_TO_ANTHILL("Pheromone requested colonies",true, true, true, true, false),
    PHEROMONE_TO_TARGET("Food search pheromone",true, true, true, false, true);

    public final String name;
    public final boolean displayAnthill;
    public final boolean displayObjects;
    public final boolean displayCreature;
    public final boolean displayPheromoneToAnthill;
    public final boolean displayPheromoneToTarget;

    WorldPaintMode(  String name, boolean displayAnthill, boolean displayObjects, 
                    boolean displayCreature, boolean displayPheromoneToAnthill, 
                    boolean displayPheromoneToTarget) {
        this.name = name;
        this.displayAnthill = displayAnthill;
        this.displayObjects = displayObjects;
        this.displayCreature = displayCreature;
        this.displayPheromoneToAnthill = displayPheromoneToAnthill;
        this.displayPheromoneToTarget = displayPheromoneToTarget;
    }

    @Override
    public String toString() {
        return name;
    }
}
