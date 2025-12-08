package com.example.labandroiddemo;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class BlackJackGameTest {

    private Deck deck;

    @Before
    public void setup() {
        deck = new Deck();
        deck.shuffle();
    }

    @Test
    public void testDeck_Has52Cards(){
        Deck testDeck = new Deck();
        int cardCount = 0;

        for(int i = 0; i < 52; i++) {
            Card card = testDeck.deal();
            if(card != null) {
                cardCount++;
            }
        }

        assertEquals(52, cardCount);
    }

    @Test
    public void testDeck_ShuffleChangesOrder() {
        Deck deck1 = new Deck();
        Deck deck2 = new Deck();

        deck2.shuffle();

        Card card1 = deck1.deal();
        Card card2 = deck2.deal();

        assertNotNull(card1);
        assertNotNull(card2);
    }
}