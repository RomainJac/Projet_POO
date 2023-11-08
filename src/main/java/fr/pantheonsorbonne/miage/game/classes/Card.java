package fr.pantheonsorbonne.miage.game.classes;

public class Card {

    public enum cardColor {
        CARREAU, TREFLE, COEUR, PIQUE;
    }

    public enum cardRank {
        DEUX, TROIS, QUATRE, CINQ, SIX, SEPT, HUIT, NEUF, DIX, VALET, DAME, ROI, AS;

    }

    private cardRank cardRank;
    private cardColor cardColor;

    public Card(cardRank rank, cardColor suit) {
        this.cardRank = rank;
        this.cardColor = suit;
    }
    

    public cardRank getcardRank() {
        return cardRank;
    }

    public void setcardRank(cardRank rank) {
        this.cardRank = rank;
    }

    public cardColor getcardColor() {
        return cardColor;
    }

    public void setcardColor(cardColor suit) {
        this.cardColor = suit;
    }

    @Override
    public String toString() {
        return "Card{" + "cardRank=" + cardRank + ", cardColor=" + cardColor + '}';
    }

    public boolean aLaMemeValeurQue(Card  Card) {
        return this.getcardRank() == Card.getcardRank();
    }

    public boolean estSuperieurA(Card  Card) {
        return this.getcardRank().compareTo(Card.getcardRank()) > 0;
    }

    public int comparerValeurs(Card Card1, Card Card2) {
        return Card1.getcardRank().compareTo(Card2.getcardRank());
    }
}