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
    private boolean visible;

    public Card(cardRank rank, cardColor suit) {
        this.cardRank = rank;
        this.cardColor = suit;
        this.visible = false;
    }

    public cardRank getCardRank() {
        return cardRank;
    }

    public cardColor getCardColor() {
        return cardColor;
    }

    public boolean estVisible() {
		return visible;
	}

	public void montre() {
		this.visible = true;
	}

    @Override
    public String toString() {
        return cardRank + " de " + cardColor;
    }

    public String getCardName() {
        return this.toString();
    }
}
