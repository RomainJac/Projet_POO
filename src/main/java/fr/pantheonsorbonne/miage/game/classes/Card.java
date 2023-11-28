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

    public cardRank getCardRank() {
        return cardRank;
    }

    public cardColor getCardColor() {
        return cardColor;
    }

    @Override
    public String toString() {
        return cardRank + " de " + cardColor;
    }

    public String getCardName() {
        return this.toString();
    }
}
