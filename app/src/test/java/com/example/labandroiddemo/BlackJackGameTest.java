package com.example.labandroiddemo;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class BlackJackGameTest {

    private Deck deck;
    private BlackJackGame game;

    @Before
    public void setup() {
        deck = new Deck();
        deck.shuffle();
        game = new BlackJackGame(deck);
    }

    @Test
    public void testStartRound_DealsCorrectNumberOfCards() {
        game.startRound();

        Card[] playerHand = game.getPlayerHand();
        Card[] dealerHand = game.getDealerHand();

        assertEquals(2, playerHand.length);
        assertEquals(2, dealerHand.length);

        assertNotNull(playerHand[0]);
        assertNotNull(playerHand[1]);
        assertNotNull(dealerHand[0]);
        assertNotNull(dealerHand[1]);
    }

    @Test
    public void testPlayerBust_ReturnsCorrectResult(){
        game.startRound();

        while(game.getPlayerValue() <= 21) {
            game.playerHit();
            if(game.getPlayerValue() > 21) {
                break;
            }
        }

        if(game.getPlayerValue() > 21) {
            String result = game.getResult();
            assertEquals("You bust!", result);
        }
    }
}