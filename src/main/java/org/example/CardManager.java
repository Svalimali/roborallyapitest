package org.example;

import java.util.*;

public class CardManager {
    private Stack<MoveCards> drawPile = new Stack<>();
    private List<MoveCards> discardPile = new ArrayList<>();
    private List<MoveCards> hand = new ArrayList<>();

    public CardManager() {
        initializeDrawPile();
        shuffleDrawPile();
    }

    private void initializeDrawPile() {
        Map<MoveCards, Integer> cardQuantities = new EnumMap<>(MoveCards.class);
        cardQuantities.put(MoveCards.FORWARD_ONE, 10);
        cardQuantities.put(MoveCards.FORWARD_TWO, 5);
        cardQuantities.put(MoveCards.FORWARD_THREE, 3);
        cardQuantities.put(MoveCards.TURN_LEFT_ONE, 5);
        cardQuantities.put(MoveCards.TURN_RIGHT_ONE, 5);
        cardQuantities.put(MoveCards.TURN_LEFT_TWO, 3);
        cardQuantities.put(MoveCards.TURN_RIGHT_TWO, 3);
        cardQuantities.put(MoveCards.TURN_180, 3);

        cardQuantities.forEach((card, quantity) -> {
            for (int i = 0; i < quantity; i++) {
                drawPile.push(card);
            }
        });
    }

    private void shuffleDrawPile() {
        Collections.shuffle(drawPile);
    }

    public boolean addCardToRegisterSlot(Register register,int slotIndex, MoveCards card) {
        if (hand.contains(card)) {
            boolean success = register.addCardToRegister(slotIndex, card);
            if (success) {
                hand.remove(card);
                return true;
            }
        }
        return false;
    }

    public boolean returnCardToHand(Register register,int slotIndex) {
        Optional<MoveCards> card = register.removeCardFromRegister(slotIndex);
        if (card.isPresent()) {
            hand.add(card.get());
            return true;
        }
        return false;
    }

    public void drawCards() {
        while (hand.size() < 9 && !drawPile.isEmpty()) {
            hand.add(drawPile.pop());
        }
        // If the drawPile is empty then we add the discarded cards to the draw pile and shuffle.
        if (hand.size() < 9) {
            drawPile.addAll(discardPile);
            discardPile.clear();
            shuffleDrawPile();
            drawCards();
        }
    }

    public void discardHand() {
        discardPile.addAll(hand);
        hand.clear();
    }

    // Method to simulate playing a card from the hand (and discarding it)
    public void playCard(int cardIndex) {
        if (cardIndex >= 0 && cardIndex < hand.size()) {
            MoveCards playedCard = hand.remove(cardIndex);
            discardPile.add(playedCard);
        }
    }


    public List<MoveCards> getHand() {
        return hand;
    }

    public List<MoveCards> getDiscardPile() {
        return discardPile;
    }

    public Stack<MoveCards> getDrawPile() {
        return drawPile;
    }
}
