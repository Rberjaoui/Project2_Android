package com.example.labandroiddemo;

public class BlackJack {

    private Deck deck;
    private Card[] playerHand = new Card[12];
    private Card[] dealerHand = new Card[12];

    private int playerCount = 0;
    private int dealerCount = 0;

    public BlackJack(Deck deck){
        this.deck = deck;
    }

    public void startRound(){
        playerCount = 0;
        dealerCount = 0;
        deck.shuffle();

        playerHand[playerCount++] = deck.deal();
        playerHand[playerCount++] = deck.deal();

        dealerHand[dealerCount++] = deck.deal();
        dealerHand[dealerCount++] = deck.deal();
    }

    public void playerHit() {
        playerHand[playerCount++] = deck.deal();
    }

    public void dealerTurn() {
        while (getHandValue(dealerHand, dealerCount) < 17) {
            dealerHand[dealerCount++] = deck.deal();
        }
    }

    public Card[] getPlayerHand() {
        Card[] arr = new Card[playerCount];
        for (int i = 0; i < playerCount; i++) {
            arr[i] = playerHand[i];
        }
        return arr;
    }

    public Card[] getDealerHand() {
        Card[] arr = new Card[dealerCount];
        for (int i = 0; i < dealerCount; i++) {
            arr[i] = dealerHand[i];
        }
        return arr;
    }

    public int getPlayerValue() {
        return getHandValue(playerHand, playerCount);
    }

    public int getDealerValue() {
        return getHandValue(dealerHand, dealerCount);
    }

    public String getResult() {
        int p = getPlayerValue();
        int d = getDealerValue();

        if (p > 21) return "You bust!";
        if (d > 21) return "Dealer busts! You win!";
        if (p > d) return "You win!";
        if (d > p) return "Dealer wins!";
        return "Tie!";
    }

    private int getHandValue(Card[] hand, int count) {
        int total = 0;
        int aces = 0;

        for (int i = 0; i < count; i++) {
            int r = hand[i].getRank();
            if (r == 1) {
                aces++;
                total += 11;
            } else if (r > 10) {
                total += 10;
            } else {
                total += r;
            }
        }

        while (total > 21 && aces > 0) {
            total -= 10;
            aces--;
        }

        return total;
    }
}
