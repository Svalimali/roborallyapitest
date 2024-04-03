package com.restapi.controller;
import org.example.Game;
import org.example.GameBoard;
import org.example.MoveCards;
import org.example.Robot;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/game")
public class RoboRallyController {


    private final Game game;

    @Autowired
    public RoboRallyController(Game game) {
        this.game = game;
    }



    @GetMapping("/addPremadeRobots")
    public ResponseEntity<String> addPremadeRobots() {
        game.addPremadeRobots();

        return ResponseEntity.ok("Added some robots");
    }

    @GetMapping("/getLastPlayersMoves")
    public ResponseEntity<String> getLastPlayersMoves() {
        String previousPlayerMovesJson = game.getPreviousPlayerMoves();
        return ResponseEntity.ok(previousPlayerMovesJson);
    }

    @GetMapping("/currentState")
    public ResponseEntity<String> getCurrentGameState() {
        String gameStateJson = game.getGameStateJson();
        return ResponseEntity.ok(gameStateJson);
    }

    @GetMapping("/getGameStateHistory")
    public ResponseEntity<String>getGameStateHistory(){
        String gameStateHistory = game.getGameStateHistoryJson();
        return ResponseEntity.ok(gameStateHistory);
    }

    @PostMapping("/postMoves")
    public ResponseEntity<String> postMoves(
            @RequestParam String playerId,
            @RequestBody List<MoveCards> moveCards) {

        Robot robot = game.findRobotById(playerId);
        if (robot != null) {
            game.postMoves(playerId, moveCards);
            return ResponseEntity.ok("Moves posted successfully for player " + playerId);
        } else {
            return ResponseEntity.badRequest().body("Robot with ID " + playerId + " not found");
        }
    }





    /*@PostMapping("/addRobot")
    public String addRobot(@RequestBody Robot robot) {
        gameBoard.addRobot(robot);
        return "Robot added successfully";
    }

    @PostMapping("/initializeBoard")
    public String initializeBoard(@RequestBody int boardRadius) {
        gameBoard.initializeBoard(boardRadius);
        return "Game board initialized successfully";
    }

    @GetMapping("/gameState")
    public String gameState(){
        return gameBoard.getGameStateInJson();
    }



    *//*@PostMapping("/moveRobotForward")
    public String moveRobotForward(String robotId,int numberOfTiles){
        Robot robot = gameBoard.findRobotById(robotId);
        robot.moveForward(numberOfTiles);
        return "Robot moved forward";
    }*//*
    @PostMapping("/turnRobot")
    public String turnRobot(String robotId,int rotation){
        Robot robot = gameBoard.findRobotById(robotId);
        robot.turnRobot(rotation);
        return "Robot turned " + rotation + "°";
    }
    @PostMapping("/reset")
    public ResponseEntity<String> resetGameBoard() {
        gameBoard.reset();
        return ResponseEntity.ok("GameBoard has been reset.");
    }*/




}