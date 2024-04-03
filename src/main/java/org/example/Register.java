package org.example;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Register {
    private MoveCards[] slots = new MoveCards[5]; // Array to hold 5 MoveCards, one for each slot
    public Register(){

    }
    public boolean addCardToRegister(int slotIndex, MoveCards card) {
        if (slotIndex >= 0 && slotIndex < slots.length && slots[slotIndex] == null) {
            slots[slotIndex] = card;
            return true;
        }
        return false;
    }

    public Optional<MoveCards> removeCardFromRegister(int slotIndex) {
        if (slotIndex >= 0 && slotIndex < slots.length && slots[slotIndex] != null) {
            MoveCards card = slots[slotIndex];
            slots[slotIndex] = null;
            return Optional.of(card);
        }
        return Optional.empty();
    }

    public List<MoveCards> getAllCards() {
        List<MoveCards> cards = new ArrayList<>();
        for (MoveCards card : slots) {
            if (card != null) {
                cards.add(card);
            }
        }
        return cards;
    }


    public boolean addCardToRegisterOne(MoveCards card) {
        return addCardToRegister(0, card);
    }

    public boolean addCardToRegisterTwo(MoveCards card) {
        return addCardToRegister(1, card);
    }
    public boolean addCardToRegisterThree(MoveCards card) {
        return addCardToRegister(2, card);
    }
    public boolean addCardToRegisterFour(MoveCards card) {
        return addCardToRegister(3, card);
    }
    public boolean addCardToRegisterFive(MoveCards card) {
        return addCardToRegister(4, card);
    }

}
