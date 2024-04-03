package org.example;

import java.util.ArrayList;
import java.util.List;

public class RobotManager {
    private List<Robot> robots;

    public RobotManager() {
        this.robots = new ArrayList<>();
    }

    public void addRobot(Robot robot, HexGrid hexGrid) throws RobotPositionException {
        if (hexGrid.hasHex(robot.getPosition())) {
            robots.add(robot);
        } else {
            throw new RobotPositionException("Robot's initial position is outside of the grid.");
        }
    }


    public Robot findRobotById(String id) {
        return robots.stream()
                .filter(robot -> robot.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    public void resetRobots() {
        robots.clear();
    }

    public List<Robot> getRobots() {
        return new ArrayList<>(robots);
    }
}
