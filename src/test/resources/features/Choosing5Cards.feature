Feature: As a Player I want to choose 5 card out of 9, So that I can fill all of my card registers

  Scenario: Filling the register
    Given a hand of 9 Cards
    And 5 empty registers
    When card selection is performed
    Then 4 cards are left in the hand
    And 5 registers have a card