package com.example.labandroiddemo;

import com.example.labandroiddemo.R;

public class Card {

    private char suit;
    private int rank;

    public Card(){
        this.suit = 'C';
        this.rank = 1;
    }

    public Card(char suit, int rank){
        this.suit = suit;
        this.rank = rank;
    }

    public char getSuit() {
        return suit;
    }

    public int getRank() {
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

    public String getImageName() {
        String suitName = "";
        if (suit == 'C') suitName = "clubs";
        if (suit == 'D') suitName = "diamonds";
        if (suit == 'H') suitName = "hearts";
        if (suit == 'S') suitName = "spades";

        String rankName;
        if (rank == 1) rankName = "a";
        else if (rank == 11) rankName = "j";
        else if (rank == 12) rankName = "q";
        else if (rank == 13) rankName = "k";
        else if (rank < 10) rankName = "0" + rank;
        else rankName = String.valueOf(rank);

        return "card_" + suitName + "_" + rankName;
    }

    public int getDrawableId(){
        switch (getImageName()){
            case "card_clubs_a": return R.drawable.card_clubs_a;
            case "card_clubs_02": return R.drawable.card_clubs_02;
            case "card_clubs_03": return R.drawable.card_clubs_03;
            case "card_clubs_04": return R.drawable.card_clubs_04;
            case "card_clubs_05": return R.drawable.card_clubs_05;
            case "card_clubs_06": return R.drawable.card_clubs_06;
            case "card_clubs_07": return R.drawable.card_clubs_07;
            case "card_clubs_08": return R.drawable.card_clubs_08;
            case "card_clubs_09": return R.drawable.card_clubs_09;
            case "card_clubs_10": return R.drawable.card_clubs_10;
            case "card_clubs_j": return R.drawable.card_clubs_j;
            case "card_clubs_q": return R.drawable.card_clubs_q;
            case "card_clubs_k": return R.drawable.card_clubs_k;

            case "card_diamonds_a": return R.drawable.card_diamonds_a;
            case "card_diamonds_02": return R.drawable.card_diamonds_02;
            case "card_diamonds_03": return R.drawable.card_diamonds_03;
            case "card_diamonds_04": return R.drawable.card_diamonds_04;
            case "card_diamonds_05": return R.drawable.card_diamonds_05;
            case "card_diamonds_06": return R.drawable.card_diamonds_06;
            case "card_diamonds_07": return R.drawable.card_diamonds_07;
            case "card_diamonds_08": return R.drawable.card_diamonds_08;
            case "card_diamonds_09": return R.drawable.card_diamonds_09;
            case "card_diamonds_10": return R.drawable.card_diamonds_10;
            case "card_diamonds_j": return R.drawable.card_diamonds_j;
            case "card_diamonds_q": return R.drawable.card_diamonds_q;
            case "card_diamonds_k": return R.drawable.card_diamonds_k;

            case "card_hearts_a": return R.drawable.card_hearts_a;
            case "card_hearts_02": return R.drawable.card_hearts_02;
            case "card_hearts_03": return R.drawable.card_hearts_03;
            case "card_hearts_04": return R.drawable.card_hearts_04;
            case "card_hearts_05": return R.drawable.card_hearts_05;
            case "card_hearts_06": return R.drawable.card_hearts_06;
            case "card_hearts_07": return R.drawable.card_hearts_07;
            case "card_hearts_08": return R.drawable.card_hearts_08;
            case "card_hearts_09": return R.drawable.card_hearts_09;
            case "card_hearts_10": return R.drawable.card_hearts_10;
            case "card_hearts_j": return R.drawable.card_hearts_j;
            case "card_hearts_q": return R.drawable.card_hearts_q;
            case "card_hearts_k": return R.drawable.card_hearts_k;

            case "card_spades_a": return R.drawable.card_spades_a;
            case "card_spades_02": return R.drawable.card_spades_02;
            case "card_spades_03": return R.drawable.card_spades_03;
            case "card_spades_04": return R.drawable.card_spades_04;
            case "card_spades_05": return R.drawable.card_spades_05;
            case "card_spades_06": return R.drawable.card_spades_06;
            case "card_spades_07": return R.drawable.card_spades_07;
            case "card_spades_08": return R.drawable.card_spades_08;
            case "card_spades_09": return R.drawable.card_spades_09;
            case "card_spades_10": return R.drawable.card_spades_10;
            case "card_spades_j": return R.drawable.card_spades_j;
            case "card_spades_q": return R.drawable.card_spades_q;
            case "card_spades_k": return R.drawable.card_spades_k;

            case "card_back": return R.drawable.card_back;

            default: return R.drawable.card_back;
        }
    }

    public static String getBackImage() {
        return "card_back";
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
