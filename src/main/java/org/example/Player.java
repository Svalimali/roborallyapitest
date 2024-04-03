package org.example;

import java.util.List;
import java.util.Optional;

public class Player {
    private String id;
    private Robot robot;
    private CardManager cardManager;
    private Register register;

    public Player(){

    }

    public Player(String id) {
        this.id = id;
    }

    public void setID(String id){
        this.id = id;
    }


    public void setRobot(Robot robot) {
        this.robot = robot;
    }

    public void setCardManager(CardManager cardManager) {
        this.cardManager = cardManager;
    }

    public void setRegister(Register register) {
        this.register = register;
    }


    public void executeRegister() {
        List<MoveCards> moveCards = register.getAllCards();
        /*robot.performMoves(moveCards);*/
    }

}
