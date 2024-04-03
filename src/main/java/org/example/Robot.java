package org.example;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Robot {
    private String id;
    private Hexagon position;
    private int direction;
    private List<Map<String, Object>> MoveHistory = new ArrayList<>();
    private Map<String, Object> LastMoves = new HashMap<>();

    private HexGrid hexgrid;
    public Robot() {
    }

    public Robot(String id, Hexagon position,int direction,HexGrid hexgrid) {
        this.id = id;
        this.position = position;
        this.direction = direction;
        this.hexgrid = hexgrid;
    }

    public void performMoves(List<MoveCards> moveCards, int currentTurn) {
        List<Map<String, Object>> moveList = new ArrayList<>();
        //Capture the initial position
        moveList.add(captureState());
        for (MoveCards moveCard : moveCards) {

            switch (moveCard) {
                case FORWARD_ONE:
                    moveForward(1);
                    break;
                case FORWARD_TWO:
                    moveForward(2);
                    break;
                case FORWARD_THREE:
                    moveForward(3);
                    break;
                case TURN_LEFT_ONE:
                    turnRobot(-60);
                    break;
                case TURN_LEFT_TWO:
                    turnRobot(-120);
                    break;
                case TURN_RIGHT_ONE:
                    turnRobot(60);
                    break;
                case TURN_RIGHT_TWO:
                    turnRobot(120);
                    break;
                case TURN_180:
                    turnRobot(180);
                    break;
            }
            // Capture the robot state after each movement.
            moveList.add(captureState());
        }
        // Add the movement of the robot to the JSON
        Map<String, Object> turnInfo = new HashMap<>();
        turnInfo.put("turn", currentTurn);
        turnInfo.put("moves", moveList);
        turnInfo.put("moveCards",moveCards);
        LastMoves = turnInfo;
        MoveHistory.add(turnInfo);
    }

    public String getId() {
        return id;
    }
    public int getDirection(){
        return direction;
    }
    public void setDirection(int direction){
        this.direction=direction;
    }

    public Hexagon getPosition() {
        return position;
    }

    public void setPosition(Hexagon position) {
        this.position = position;
    }

    public void moveForward(int numberOfTiles) {
        Hexagon currentHex = this.position;

        Hexagon neighborHex;
        for (int i = 0; i < numberOfTiles; i++) {
            System.out.println("Moving from:"+currentHex);
            neighborHex = currentHex.getNeighbor(direction);
            System.out.println("neighborHex:"+neighborHex);

            if(!hexgrid.hasHex(neighborHex)){

                setPosition(currentHex);
                break;
            }
            currentHex = neighborHex;
        }
        setPosition(currentHex);

    }

    public void turnRobot(int degrees) {
        direction = (direction + degrees) % 360;
        if (direction < 0) {
            direction += 360;
        }
        /*System.out.println("direction after turning "+degrees+"°:"+direction);*/
    }
    private Map<String, Object> captureState() {
        Map<String, Object> state = new HashMap<>();
        state.put("q", this.position.getQ());
        state.put("r", this.position.getR());
        state.put("direction", this.direction);
        return state;
    }


    public List<Map<String, Object>> getMovementHistory() {
        return this.MoveHistory;
    }




    public Map<String, Object> getRobotState() {
        return captureState();
    }

    public Map<String, Object> getLastMoves() {
        return LastMoves;
    }
}
