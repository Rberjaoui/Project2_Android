package com.example.labandroiddemo;

public class Card {

    private char suit;
    private int rank;

    public Card() {
        this.suit = 'C';
        this.rank = 1;
    }

    public Card(char suit, int rank){
        this.suit = suit;
        this.rank = rank;
    }

    public char getSuit(){
        return suit;
    }

    public int getRank(){
        return rank;
    }

    public void setSuit(char suit) {
        suit = Character.toUpperCase(suit);
        if (suit == 'C' || suit == 'D' || suit == 'H' || suit == 'S') {
            this.suit = suit;
        }
    }

    public void setRank(int rank) {
        if (rank >= 1 && rank <= 13) {
            this.rank = rank;
        }
    }

    public int getValue() {
        if (rank == 1) return 11;
        if (rank >= 10) return 10;
        return rank;
    }

    @Override
    public String toString() {
        String r;
        switch (rank) {
            case 1:  r = "A"; break;
            case 11: r = "J"; break;
            case 12: r = "Q"; break;
            case 13: r = "K"; break;
            default: r = String.valueOf(rank);
        }
        return r + suit;
    }
}
