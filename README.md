
# ANT COLONY MODEL
This is my university course work on the topic "Ant colony model".
Minimum version to run Java 8

![image](https://github.com/Trymad1/ant-colony-model/assets/137887620/8d69984b-c933-49d1-9962-54f51f66181a)



# Basic rules of model
Ants can build efficient routes using pheromones they leave along their path.
Map size - 100x100 cells, 1k3 is drawn up to 300x300 pixels
On the map there are objects:
+ Anthill in the center of the map
+ Food sources randomly located throughout the map at a certain distance from the anthill. They have a limited amount of food, disappear when the amount of food runs out. New sources appear as existing ones on the map are depleted

There are 2 types of pheromone:
+ Pheromone for searching for food
+ Pheromone for finding a home
  
The pheromone evaporates over time

### Basic Ant Behavior:
At the moment, only collector ants are implemented in the model, so this description is valid for all ants
+ The ant appears in the anthill, and begins to look for a food source, leaving a home search pheromone on its way, guided by the food search pheromone. If there is no pheromone in sight, it moves in a random direction. When it finds food, it goes into ant mode with food
+ Ants with food will leave a food search pheromone and be guided by a home search pheromone. If there is no pheromone in sight, they move in a random direction. Having reached the anthill, they put down the food and go into ant mode without food.

Depending on the amount of food, new ants may appear, however if the amount of food is 0, each iteration there is a chance of a random ant dying on the map

Thus, the program demonstrates how ants can form effective chains of routes from the anthill to food using pheromones

### How pheromones work?
A pheromone is a fingerprint left by an ant containing information about how many cells the ant has traveled from an anthill or food source to that cell, depending on its mode.
Ants that read the pheromone around them choose the cell with the smallest value, thus consistently reaching the goal

