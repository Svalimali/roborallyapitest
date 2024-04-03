package org.example;



import com.google.gson.Gson;

import java.util.*;

public class Game {
    private List<Robot> robots;
    private int currentPlayerIndex = 0;
    private String previousPlayerId = "";
    private int currentTurn = 0;
    private HexGrid hexGrid;
    private int boardRadius = 0;
    private List<Map<String, Object>> shotHistory;



    public Game(){
        this.shotHistory = new ArrayList<>();
        this.hexGrid = new HexGrid();
        this.boardRadius = 5;
        this.hexGrid.createGrid(5);
        this.robots = new ArrayList<>();
    }

    public Game(int boardRadius) {
        this.shotHistory = new ArrayList<>();
        this.boardRadius = boardRadius;
        this.hexGrid = new HexGrid();
        this.hexGrid.createGrid(boardRadius);
        this.robots = new ArrayList<>();
    }

    public Game(int boardRadius,List<Robot> robots) {
        this.shotHistory = new ArrayList<>();
        this.boardRadius = boardRadius;
        this.robots = robots;
        this.hexGrid = new HexGrid();
        this.hexGrid.createGrid(boardRadius);
    }

    public void addRobots(List<Robot> robots){
        this.robots = robots;
    }

    public void addRobot(Robot robot){
        this.robots.add(robot);
    }

    public void addPremadeRobots(){
        Robot R1 = new Robot("R1",new Hexagon(1,1),30,this.hexGrid);
        Robot R2 = new Robot("R2",new Hexagon(0,1),90,this.hexGrid);
        Robot R3 = new Robot("R3",new Hexagon(1,0),150,this.hexGrid);
        Robot R4 = new Robot("R4",new Hexagon(0,0),210,this.hexGrid);
        addRobot(R1);
        addRobot(R2);
        addRobot(R3);
        addRobot(R4);

    }

    public void postMoves(String playerId, List<MoveCards> moveCards) {
        Robot robot = findRobotById(playerId);
        if (robot != null) {
            previousPlayerId = playerId;

            robot.performMoves(moveCards,currentTurn);
            shoot(robot);

            currentPlayerIndex = (currentPlayerIndex + 1) % robots.size(); // Proceed to next player

            if (currentPlayerIndex == robots.size()-1){
                currentTurn +=1;
            }
        }
    }

    public Robot findRobotById(String id) {
        return robots.stream()
                .filter(robot -> robot.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    private void shoot(Robot shooter) {
        //Position of the shooter
        Hexagon initialHex = shooter.getPosition();
        Hexagon currentHex = initialHex;
        //Direction the shot will travel
        int direction = shooter.getDirection();

        while (true) {
            System.out.println("Shot from: "+currentHex);
            currentHex = hexGrid.neighborOf(currentHex, direction);
            System.out.println("going to: "+currentHex);

            // If the next hex is out of bounds, stop the process.
            if (!hexGrid.hasHex(currentHex)) {
                break;
            }

            Robot target = findRobotInGrid(currentHex);
            if (target != null) {
                Map<String, Object> shotRecord = new HashMap<>();
                shotRecord.put("turn",currentTurn);
                shotRecord.put("shooter",shooter.getId());
                shotRecord.put("start",initialHex);
                shotRecord.put("target",target.getId());
                shotRecord.put("end",target.getPosition());

                shotHistory.add(shotRecord);
                System.out.println("Robot " + shooter.getId() + " hits " + target.getId());
                break;
            }
        }
    }
    private Robot findRobotInGrid(Hexagon hex) {
        for (Robot robot : robots) {
            if (robot.getPosition().equals(hex)) {
                return robot;
            }
        }
        return null; // No robot in this hexagon
    }

    public String getGameStateJson() {
        Map<String, Object> gameState = new HashMap<>();
        gameState.put("boardRadius", boardRadius);

        List<Map<String, Object>> robotStateJson = new ArrayList<>();
        for (Robot robot : robots) {
            if (robot != null) {
                // Assuming getRobotState returns a Map representing the robot's state
                Map<String, Object> robotState = robot.getRobotState(); // Adjusted method
                robotState.put("id", robot.getId()); // Add the robot's ID to the state map
                robotStateJson.add(robotState); // Add the structured robot state to the list
            }
        }

        gameState.put("current_game_state", robotStateJson);

        Gson gson = new Gson();
        return gson.toJson(gameState);
    }

    public String getGameStateHistoryJson() {
        Map<String, Object> gameStateHistory = new HashMap<>();
        gameStateHistory.put("boardRadius", boardRadius);

        List<Map<String, Object>> robotMovements = new ArrayList<>();
        for (Robot robot : robots) {
            if (robot != null) {
                Map<String, Object> robotHistory = new HashMap<>();
                robotHistory.put("id", robot.getId());
                robotHistory.put("movements", robot.getMovementHistory()); // Use the structured data
                robotMovements.add(robotHistory);
            }
        }
        gameStateHistory.put("game_state_history", robotMovements);
        gameStateHistory.put("shot_history",shotHistory);

        Gson gson = new Gson();
        return gson.toJson(gameStateHistory);
    }

    public String getPreviousPlayerMoves(){
        Robot robot = findRobotById(previousPlayerId);
        Gson gson = new Gson();
        return gson.toJson(robot.getLastMoves());
    }
}
