# 8-Puzzle Problem

This project aims to solve the 8-puzzle problem using A* search algorithm.

The 8-puzzle problem is a puzzle invented by Noyes Palmer Chapman in the 1870s. It is played on a 3-by-3 grid with 8 square blocks labeled 1 through 8 and a blank square. 
The goal is to rearrange the blocks so that they are in order, using as few moves as possible.
It is permitted to slide blocks horizontally or vertically into the blank square.

## Note

This project has a dependency to the [hasanguner/ai-search-algorithms](https://github.com/hasanguner/ai-search-algorithms). 
Checkout the project to see to how A* Search Algorithm is implemented.

## Build

You can build the project and create .jar file.

```
./gradlew clean build
```

## Run
Once you have build the project, you can pass the puzzle file path as argument to jar.

```
java -jar build/libs/*.jar problems/puzzle04.txt
```

### Input
Puzzle file must be in the following format.

```
  3
   0  1  3 
   4  2  5  
   7  8  6  
```
### Output
Output will be in the following format and will print every move required to reach to the goal state.

```
Minimum number of moves = 4
(3x3)
0 1 3
4 2 5
7 8 6
(3x3)
1 0 3
4 2 5
7 8 6
(3x3)
1 2 3
4 0 5
7 8 6
(3x3)
1 2 3
4 5 0
7 8 6
(3x3)
1 2 3
4 5 6
7 8 0
```

## Tests

You can run all tests.

```
./gradlew test
```