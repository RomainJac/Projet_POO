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

    public void setCardRank(cardRank rank) {
        this.cardRank = rank;
    }

    public cardColor getCardColor() {
        return cardColor;
    }

    public void setCardColor(cardColor suit) {
        this.cardColor = suit;
    }

    @Override
    public String toString() {
        return cardRank + " de " + cardColor;
    }

    public boolean hasSameValueAs(Card card) {
        return this.getCardRank() == card.getCardRank();
    }

    public boolean isGreaterThan(Card card) {
        return this.getCardRank().compareTo(card.getCardRank()) > 0;
    }

    public int compareValues(Card card1, Card card2) {
        return card1.getCardRank().compareTo(card2.getCardRank());
    }

    public String getCardName() {
        return this.toString();
    }
}
