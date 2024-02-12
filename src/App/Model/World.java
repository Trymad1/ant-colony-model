package App.Model;

import java.awt.Dimension;
import java.awt.Point;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Random;
import java.util.stream.Collectors;

import App.Model.Entity.Entity;
import App.Model.Entity.Ant.Ant;
import App.Model.Entity.Ant.Anthill;
import App.Model.Entity.Ant.CollectorAnt;
import App.Model.Entity.Food.FoodSource;
import App.Model.Entity.Pheromone.Pheromone;
import App.Model.Entity.Pheromone.PheromoneUtil;
import App.Model.Entity.Pheromone.ToAnthillPheromone;
import App.Model.Entity.Pheromone.ToTargetPheromone;
import App.Model.Interface.Updatable;
import App.Model.Setting.SettingBuilder;
import App.Util.Enum.EntityTypes;
import App.Util.Enum.PheromoneTypes;
import App.Util.Static.Entities;
import App.Util.Static.EntityParams;
import App.Util.Static.Worlds;

/**
*Main class, contains the main logic of the model,
*handling all events and delegating to the process
*/
public class World implements Updatable {

    //Contains a list of all entities on the map that will be drawn
    private final Map<EntityTypes, Map<Point, Entity>> entities;

    //Contains objects for which the update method will be called at each iteration of the world
    private final List<Updatable> updatableObjects;

    //Information about pheromones: their type, coordinates, current value
    private final Map<PheromoneTypes, Map<Point, Pheromone>> pheromones;

    //List of points where new food sources will randomly appear in the future
    private final List<Point> areaForRandomSpawn;

    //All objects contained in this list will be deleted during the next iteration of the world
    private final Queue<Entity> toRemove;

    //Helper class for pheromone management
    private final PheromoneUtil pheromoneUtil;

    private Dimension worldSize;

    private final int DEFAULT_SIZE_WIDTH = 100;
    private final int DEFAULT_SIZE_HEIGHT = 100;

    private Setting setting;
    private int diedAnt;
    private int iterationCount = 0;

    
    public World() {

        this.setting = SettingBuilder.createDefaultSetting();

        final Map<EntityTypes, Map<Point, Entity>> tempEntities = new HashMap<>();
        tempEntities.put(EntityTypes.ANTHILL, new HashMap<>());
        tempEntities.put(EntityTypes.CREATURE, new HashMap<>());
        tempEntities.put(EntityTypes.OBJECT, new HashMap<>());
        entities = Collections.unmodifiableMap(tempEntities);
        
        worldSize = new Dimension(DEFAULT_SIZE_WIDTH, DEFAULT_SIZE_HEIGHT);
        updatableObjects = new ArrayList<>();
        areaForRandomSpawn = new ArrayList<>();

        toRemove = new ArrayDeque<>();
        
        Map<PheromoneTypes, Map<Point, Pheromone>> tempPheromoneMap = new HashMap<>();
        tempPheromoneMap.put(PheromoneTypes.TO_ANTHILL, new HashMap<>());
        tempPheromoneMap.put(PheromoneTypes.TO_TARGET, new HashMap<>());
        pheromones = Collections.unmodifiableMap(tempPheromoneMap);

        pheromoneUtil = new PheromoneUtil(pheromones);
        
    }
    
    public World(Dimension worldSize) {
        this();
        this.worldSize = worldSize;
    }

    /**
    *The method call is one iteration in the world, calling the update method on all Updatable objects
    */
    public void update() {
        removeFromQueue(toRemove);
        randomActionsInWorld();
        final Anthill anthill 
            = (Anthill) entities.get(EntityTypes.ANTHILL).values().toArray()[0];
        anthill.foodConsum(getAntQuant());
        updatableObjects.stream().forEach(Updatable::update);
        pheromoneUtil.update();
        iterationCount++;
    }

    /**
    *Called every time the world is empty. Creates one anthill, a given number of ants, food sources.
    */
    public void createRandomWorld() {
        
        final List<Point> allPointsInWorld = 
        Worlds.getArea(new Point(0, 0), 
                new Point(worldSize.width - 1, worldSize.height - 1));
                
        //Initialization hashmap with pheromones
        allPointsInWorld.stream().forEach( point -> {
            pheromones.get(PheromoneTypes.TO_ANTHILL).put(point, new ToAnthillPheromone(this, point));
            pheromones.get(PheromoneTypes.TO_TARGET).put(point, new ToTargetPheromone(this, point));
        });

        //Let's create an anthill
        final Point anthillPoint = new Point(
            ((worldSize.width - 1) / 2),
            ((worldSize.height - 1) / 2));
            createEntity(new Anthill(
                anthillPoint, this, setting.getStartFoodValue(), setting.getFoodConsumption()));

        final Point pointNearAnthillA = 
            new Point((worldSize.width - 1) / 3, (worldSize.height -1) / 3);

        final Point pointNearAnthillB 
            = new Point((worldSize.width - 1) - pointNearAnthillA.x, 
                        (worldSize.height - 1) - pointNearAnthillA.y); 
                  
        //Creating and placing ants
        final List<Point> pointsNearAnthill = Worlds.getArea(
            pointNearAnthillA, pointNearAnthillB);
        
        for (int i = 0; i < setting.getAntQuant(); i++) {
            final Entity entity = createEntity(new CollectorAnt(this));
            entities.get(EntityTypes.ANTHILL).values().forEach(
                element -> {
                    Anthill anthill = (Anthill) element;
                    anthill.addInAnthill((Ant) entity);
                }
            );
        }

        //Designation of a zone for the spawning of other entities
        allPointsInWorld.stream()
        .filter(point -> !(pointsNearAnthill.contains(point)))
        .forEach(point -> areaForRandomSpawn.add(point));
    
        //Randomly placing food in the world at a certain distance from the colony
        final List<Point> emptyPointsForRespawn 
            = Worlds.getEmptyPointFromArea(areaForRandomSpawn, this.getAllEntities());

        for (int i = 0; i < setting.getFoodSourceQuant(); i++) {  
            final Entity entity = randomSpawn(new FoodSource(this, setting.getFoodInSourceQuant()), emptyPointsForRespawn);
            if(entity != null) emptyPointsForRespawn.removeAll(
                Entities.getPointsForEntity(entity.getPoint(), entity.getSize()));
        }
    }

    /**
    *@param entity -entity to spawn
    *@param area -the area in which the entity will randomly spawn
    *If placement is successful, return the passed entity, otherwise null
    */
    public Entity randomSpawn(Entity entity, List<Point> area) {
        
        try {
            if(area.size() < Math.pow(entity.getSize(), 2)) 
                throw new RuntimeException("No place for spawn");
        } catch (Exception e) {
            return null;
        }

        for (int i = 0; i < area.size(); i++) {
            if(i == area.size() - 1) return null;
            
            int randomPointForSpawn = (int) (Math.random() * area.size());
            final Point emptyPoint = area.get(randomPointForSpawn);
            final List<Point> entityPoints = Entities.getPointsForEntity(emptyPoint, entity.getSize());
            boolean canPlace = entityPoints.stream().allMatch(point -> area.contains(point));

            if(!canPlace) continue;
            entity.setPoint(emptyPoint);
            createEntity(entity);
            break;
        }
        return entity;
    }
    
    /**
    *Responsible for random events in the world such as the appearance of new entities or the death of old ones
    */
    private final int ATTEMP_TO_RANDOM_SPAWN = 10;
    private final int PERSENT_TO_ANT_DIE = 1;
    private final int PERSENT_TO_ANT_SPAWN = 1;

    private void randomActionsInWorld() {
        
        //Random events in the world will begin to occur a little later than the moment when all the ants
        //left the anthill
        if (iterationCount < setting.getAntQuant() + 10) {
            return;
        }

        final int minimumFoodSourceForSpawn = setting.getFoodSourceQuant() * (EntityParams.Sizes.FOOD_SOURCE * 2);

        final List<Entity> foodSource = 
            entities.get(EntityTypes.OBJECT).values().stream()
            .filter(entity -> entity instanceof FoodSource)
            .collect(Collectors.toList());
        if (foodSource.size() < minimumFoodSourceForSpawn) {
            Entity newFoodSource = null;
            int attempCounter = 0;
            while (newFoodSource == null && attempCounter <= ATTEMP_TO_RANDOM_SPAWN) {
                newFoodSource = randomSpawn(new FoodSource(this, setting.getFoodInSourceQuant()), 
                           Worlds.getEmptyPointFromArea(areaForRandomSpawn, getAllEntities()));
                attempCounter++;
            }
        }

        final Anthill anthill 
            = (Anthill) entities.get(EntityTypes.ANTHILL).values().toArray()[0];
        final Random random = new Random();
        final List<Entity> ants = entities.get(EntityTypes.CREATURE).values().stream()
        .filter(entity -> entity instanceof Ant).collect(Collectors.toList());

        if(ants.size() == 0) return;
        if(anthill.getFoodQuantity() <= 0f && random.nextInt(100) < PERSENT_TO_ANT_DIE) {
            toRemove.add((ants.get(random.nextInt(ants.size()))));
            diedAnt++;
        }

        //The value of food on the chance of the next ant spawning increases exponentially
        //depending on the number of ants,
        final double formulaForNewAnt = (Math.pow(1.16, getAntQuant()));

        if (formulaForNewAnt < anthill.getFoodQuantity() && random.nextInt(100) < PERSENT_TO_ANT_SPAWN) {
            final Ant newAnt = new CollectorAnt(this);
            createEntity(newAnt);
            anthill.addInAnthill(newAnt);
        }
    }

    /**
    *
    *@param entity -The entity that will be created in the world
    *@return entity passed in parameters
    */
    private Entity createEntity(Entity entity) {
        // Единица в четном случае отнимается для того, чтобы
        // центр элемента оставался в том же месте
        if (entity.getPoint() != null) {
            final List<Point> coord = Entities.getPointsForEntity(entity.getPoint(), entity.getSize());
            if (isPossibleToCreate(coord) == false) return null;
            coord.forEach(emptyPoint -> {
                final EntityTypes entityType = entity.getEntityType();
                entities.get(entityType).put(emptyPoint, entity);
            });
        }

        if (entity instanceof Updatable) updatableObjects.add( (Updatable) entity);

        return entity;
    }

    /**
    *@param entity that will be queued for deletion
    */
    public void addToRemoveEntity(Entity entity) {
        toRemove.add(entity);
    }

    /**
    *@param toRemove the queue containing entities will be removed from the world
    */
    private void removeFromQueue(Queue<Entity> toRemove) {
        while(!toRemove.isEmpty()) {
            removeEntity(toRemove.poll());
        }
    }

    /**
    *
    *@param entity -the entity that will be removed from the world
    *@return -entity passed in parameters
    */
    private Entity removeEntity(Entity entity) {
        final List<Point> entityPoints = 
            Entities.getPointsForEntity(entity.getPoint(), entity.getSize());
        if(entity instanceof Updatable) updatableObjects.remove( (Updatable) entity);
        entityPoints.stream().forEach(point -> {
            entities.get(entity.getEntityType()).remove(point);
        });
        
        return entity;
    }

    /**
    *@param coord list of coordinates at which a new entity can potentially be created, if they are free
    *@return -false if the passed coordinates are not included in the dimensions of the world, or are occupied by other entities, otherwise true
    */
    private boolean isPossibleToCreate(List<Point> coord) {
        final boolean isNotEmptyCoord = 
        coord.stream()
        .anyMatch(point -> 
            (point.x >= 0 && point.y >= 0) &&
            !(point.x <= worldSize.width - 1 && point.y <= worldSize.height - 1) &&
            (this.getAllEntities().get(point) != null));
        return !isNotEmptyCoord;
    }
    
    /**
    *Completely clears the current model
    */
    public void clearWorld() {
        updatableObjects.clear();
        pheromones.get(PheromoneTypes.TO_ANTHILL).clear();
        pheromones.get(PheromoneTypes.TO_TARGET).clear();
        
        entities.values().forEach(Map::clear);
        toRemove.clear();
        iterationCount = 0;
        diedAnt = 0;
    }

    /**
    *
    *@param entity -the entity that should change coordinates
    *@param point -coordinates to which the entity should be moved
    */
    public void relocateEntity(Entity entity, Point point) {
        if(entity.getPoint() != null) entities.get(entity.getEntityType()).remove(entity.getPoint());
        entities.get(entity.getEntityType()).put(point, entity);
    }

    /**
    *Below are getters and setters
    */

    public Map<Point, Pheromone> getSoldierPheromone() {
        return pheromones.get(PheromoneTypes.TO_TARGET);
    }

    public Map<Point, Pheromone> getCollectorPheromone() {
        return pheromones.get(PheromoneTypes.TO_ANTHILL);
    }

    public Map<Point, Entity> getAllEntities() {
        final Map<Point, Entity> allEntities = new HashMap<>();

        entities.values().stream()
        .forEach(entitiesMap -> allEntities.putAll(entitiesMap));

        return allEntities;
    }

    public int getAntQuant() {
        return entities.get(EntityTypes.CREATURE).values().stream()
        .filter(entity -> entity instanceof Ant)
        .collect(Collectors.toList()).size();
    }

    public int getAntWithFood() {
        return entities.get(EntityTypes.CREATURE).values().stream()
        .filter(entity -> entity instanceof Ant)
        .filter(entity -> entity.getColor() == EntityParams.Colors.FOOD)
        .collect(Collectors.toList()).size();
    }

    public int getAntWithoutFood() {
        return entities.get(EntityTypes.CREATURE).values().stream()
        .filter(entity -> entity instanceof Ant)
        .filter(entity -> entity.getColor() == EntityParams.Colors.ANT_COLLECTOR)
        .collect(Collectors.toList()).size();
    }

    public Map<Point, Entity> getCreatures() {
        return entities.get(EntityTypes.CREATURE);
    }

    public Map<Point, Entity> getObjects() {
        return entities.get(EntityTypes.OBJECT);
    }

    public float getAnthillFoodQuant() {
        return entities.get(EntityTypes.ANTHILL).values().stream()
        .map(entity -> (Anthill) entity)
        .findFirst().get().getFoodQuantity();
    }

    public Map<Point, Entity> getAnthill() {
        return entities.get(EntityTypes.ANTHILL);
    }

    public boolean isCreated() {
        return !entities.get(EntityTypes.ANTHILL).isEmpty();
    }

    public Dimension getWorldSize() {
        return worldSize;
    }

    public PheromoneUtil getPheromoneUtil() {
        return pheromoneUtil;
    }

    public Map<PheromoneTypes, Map<Point, Pheromone>> getPheromones() {
        return pheromones;
    }

    public void setWorldSize(Dimension worldSize) {
        this.worldSize = worldSize;
    }

    public void setSetting(Setting setting) {
        this.setting = setting;
    }

    public int getDiedAnt() {
        return diedAnt;
    }
}
