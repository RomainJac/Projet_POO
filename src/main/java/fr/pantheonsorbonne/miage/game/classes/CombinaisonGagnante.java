package fr.pantheonsorbonne.miage.game.classes;

public class CombinaisonGagnante implements Comparable<CombinaisonGagnante> {
    private Victoire victoire;
    private Card.cardRank cardRank;

    public CombinaisonGagnante(Victoire victoire, Card.cardRank cardRank) {
        this.victoire = victoire;
        this.cardRank = cardRank;
    }

    public CombinaisonGagnante(Victoire victoire) {
        this.victoire = victoire;
    }

    public int compareTo(CombinaisonGagnante combinaison) {
        if (victoire.compareTo(combinaison.victoire) != 0 && cardRank == null)
            return victoire.compareTo(combinaison.victoire);
        else if (cardRank != null && combinaison.cardRank != null)
            return cardRank.compareTo(combinaison.cardRank);
        return 0;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CombinaisonGagnante that = (CombinaisonGagnante) o;
        return victoire == that.victoire && cardRank == that.cardRank;
    }

    @Override
    public String toString() {
        return cardRank + " " + victoire;
    }

    public enum Victoire {
        CARTE_HAUTE, PAIRE, DEUX_PAIRE, BRELAN, SUITE, COULEUR, FULL, CARRE,QUINTE, QUINTE_FLUSH,
        QUINTE_FLUSH_ROYALE;

        }
}