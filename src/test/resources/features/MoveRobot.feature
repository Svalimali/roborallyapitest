Feature: Forward movement of the robot

  Scenario: Successful move of the robot
    Given an empty Board
    And a Robot at coordinates 0, 0 facing "North"
    And a "Move forward 1" card
    When the Card executes
    Then the Robot is on coordinates 0, 1 facing "North"

  Scenario: Unsuccessful move of the robot, because the robot cannot move out of the board
    Given an empty Board
    And a Robot at coordinates 0, 0 facing "North"
    And a "Move forward 1" card
    When the Card executes
    Then the Robot is on coordinates 0, 0 facing "North"
    And an error "message" has returned

  Scenario: Successful rotation of robot
    Given an empty Board
    And a Robot at coordinates 0, 0 facing "South"
    And a "Rotate left" card
    When the Card executes
    Then the Robot is on coordinates 0, 0 facing "East"

