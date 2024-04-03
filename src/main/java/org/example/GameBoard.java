package org.example;

import com.google.gson.Gson;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GameBoard {
    private RobotManager robotManager;
    private HexGrid hexGrid;
    private int boardRadius;

    public GameBoard() {
        this(0); // Initialize with default radius 0
    }

    public GameBoard(int radius) {
        this.boardRadius = radius;
        this.robotManager = new RobotManager();
        this.hexGrid = new HexGrid();
        this.hexGrid.createGrid(radius);
    }

    public void initializeBoard(int boardRadius){
        this.boardRadius = boardRadius;
        this.hexGrid.createGrid(boardRadius);
    }

    public void addRobot(Robot robot) {
        try{
            robotManager.addRobot(robot, hexGrid);
        } catch (RobotPositionException e) {
            throw new RuntimeException(e);
        }

    }

    public Robot findRobotById(String id) {
        return robotManager.findRobotById(id);
    }

    public void reset() {
        robotManager.resetRobots();
        this.hexGrid = new HexGrid();
        this.hexGrid.createGrid(this.boardRadius);
    }

    public String getGameStateInJson() {
        Map<String, Object> gameState = new HashMap<>();
        List<Map<String, Object>> robotStates = new ArrayList<>();

        for (Robot robot : robotManager.getRobots()) {
            Map<String, Object> robotState = new HashMap<>();
            Hexagon position = robot.getPosition();
            Map<String, Integer> positionMap = Map.of("q", position.getQ(), "r", position.getR());

            robotState.put("robotId", robot.getId());
            robotState.put("position", positionMap);
            robotState.put("direction", robot.getDirection());
            robotStates.add(robotState);
        }

        gameState.put("boardRadius", this.boardRadius);
        gameState.put("robots", robotStates);

        Gson gson = new Gson();
        return gson.toJson(gameState);
    }

}
