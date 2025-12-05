package com.example.labandroiddemo;

public class Deck {

    private Card[] cards;
    private int index;

    public Deck(){
        cards = new Card[52];
        index = 0;

        char[] suits = {'C','D','H','S'};
        int k = 0;

        for (char s : suits) {
            for (int r = 1; r <= 13; r++) {
                cards[k++] = new Card(s, r);
            }
        }
    }

    public void shuffle() {
        index = 0;
        for (int i = 0; i < cards.length; i++) {
            int j = (int)(Math.random() * cards.length);
            Card temp = cards[i];
            cards[i] = cards[j];
            cards[j] = temp;
        }
    }

    public boolean hasNext() {
        return index < 52;
    }

    public Card deal() {
        if (!hasNext()) return null;
        return cards[index++];
    }

    public int remaining() {
        return 52 - index;
    }
}