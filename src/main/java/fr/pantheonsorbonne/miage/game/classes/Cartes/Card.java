package fr.pantheonsorbonne.miage.game.classes.Cartes;

public class Card {
    private CardRank cardRank;
    private CardColor cardColor;
    private boolean faceVisible;

    public Card(CardRank valeur, CardColor couleur) {
        this.cardRank = valeur;
        this.cardColor = couleur;
        this.faceVisible = false;
    }

    public CardRank getCardRank() {
        return cardRank;
    }

    public CardColor getCardColor() {
        return cardColor;
    }

    @Override
    public String toString() {
        return this.cardRank.getStringRepresentation() + " de " + this.cardColor;
    }

    public boolean compareTo(Card Card) {
        return this.getCardRank().compareTo(Card.getCardRank()) > 0;
    }

    public boolean estVisible() {
        return faceVisible;
    }

    public void montre() {
        this.faceVisible = true;
    }

    public String CardEnChaine(Card Card) {
        return Card.getCardRank().getStringRepresentation() + " de " + this.cardColor;
    }
}
