package App.Util;

public enum WorldPaintMode {
    ALL_ENTITIES("Стандартный",true, true, true, false, false), 
    ONLY_OBJECTS("Только обьекты",true, true, false, false, false), 
    ONLY_CREATURE("Только сущности",false, false, true, false, false),
    PHEROMONE_TO_ANTHILL("Феромон поиска дома",true, true, false, true, false),
    PHEROMONE_TO_TARGET("Феромон поиска еды",true, true, false, false, true);

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
