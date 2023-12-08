package fr.pantheonsorbonne.miage.game.classes;

import java.util.Collections;
import java.util.List;
import java.util.Random;

public class Joueur implements Comparable<Joueur> {

    private String nom;
    private int pileDeJetons;
    protected int mise;
    private CombinaisonGagnante combinaison;
    private MainDuJoueur mainDuJoueur;

    protected boolean estTapis;

    public Joueur(String nom) {
        this(nom, 0);
    }

    public Joueur(String nom, int jetons) {
        this.nom = nom;
        this.pileDeJetons = jetons;
    }

    public String getNom() {
        return nom;
    }

    public int getPileDeJetons() {
        return pileDeJetons;
    }

    public void setPileDeJetons(int pileDeJetons) {
        this.pileDeJetons = pileDeJetons;
    }

    public int getMise() {
        return mise;
    }

    public void setMise(int mise) {
        this.mise = mise;
    }

    public CombinaisonGagnante getCombinaison() {
        return combinaison;
    }

    public void setMainDuJoueur(MainDuJoueur mainDuJoueur) {
        this.mainDuJoueur = mainDuJoueur;
    }

    public boolean isTapis() {
        return estTapis;
    }

    public void setMain(MainDuJoueur Cards) {
        this.mainDuJoueur = Cards;
    }

    public void setCombinaisonGagnante(CombinaisonGagnante conditionVictoire) {
        this.combinaison = conditionVictoire;
    }

    public void afficherMain() {
        System.out.println(this.nom + " a la main suivante :");
        for (Card Card : mainDuJoueur.getMainDuJoueur()) {
            System.out.println(Card);
        }
    }

    public int miser(int combien) {
        if (this.pileDeJetons <= combien) {
            this.mise += this.pileDeJetons;
            this.pileDeJetons = 0;
            this.estTapis = true;
        } else if (combien > 0) {
            this.mise += combien;
            this.pileDeJetons -= combien;
        }
        return combien;

    }

    public int aGagné(int gain) {
        return this.pileDeJetons += gain;
    }

    public int aPerdu(int perte) {
        return this.pileDeJetons -= perte;
    }

    public void ajouterCarte(Card carte) {
        this.mainDuJoueur.tirerCarte(carte);
    }

    public void enleverCarte() {
        this.mainDuJoueur.supprimerCarte();
    }

    public void rendreCarteVisible() {
        if (this.mainDuJoueur != null && !this.mainDuJoueur.getMainDuJoueur().isEmpty()) {
            Random random = new Random();
            int index = random.nextInt(this.mainDuJoueur.getMainDuJoueur().size());
            Card carte = this.mainDuJoueur.getMainDuJoueur().get(index);
            carte.montre();
        }
    }

    @Override
    public int compareTo(Joueur joueur) {
        return (this.getCombinaison().compareTo(joueur.getCombinaison()));
    }

    public String toString() {
        return this.nom + " jetons actuels : " + this.pileDeJetons;
    }

    public List<String> getCardNames() {
        if (this.getMainDuJoueur() != null) {
            return this.getMainDuJoueur().getCardNames();
        }
        return Collections.emptyList();
    }

    public MainDuJoueur getMainDuJoueur() {
        return this.mainDuJoueur;
    }

    public int faireChoix(MainDuCroupier croupier, int miseMaximale, int tour) {
        int probabiliteDeGagner = probabiliteDeGagner(croupier);
        if (probabiliteDeGagner > 20 && probabiliteDeGagner <= 50 && tour > 1)
            return 1;
        else if (probabiliteDeGagner > 50 && getPileDeJetons() > miseMaximale
                || probabiliteDeGagner > 20 && probabiliteDeGagner <= 50 && tour == 1)
            return 3;
        return 2;
    }

    public int probabiliteDeGagner(MainDuCroupier croupier) {
        Card.cardRank firstCardRank = mainDuJoueur.getMainDuJoueur().get(0).getCardRank();
        Card.cardRank secondCardRank = mainDuJoueur.getMainDuJoueur().get(1).getCardRank();
        if (firstCardRank.equals(secondCardRank)) {
            if (firstCardRank == Card.cardRank.AS)
                return 85;
            else if (firstCardRank == Card.cardRank.ROI)
                return 80;
            else if (firstCardRank == Card.cardRank.DAME)
                return 75;
            else if (firstCardRank == Card.cardRank.VALET)
                return 70;
            else if (firstCardRank == Card.cardRank.DIX)
                return 65;
            else if (firstCardRank == Card.cardRank.NEUF)
                return 60;
            else if (firstCardRank == Card.cardRank.HUIT)
                return 55;
            else if (firstCardRank == Card.cardRank.SEPT)
                return 50;
            else if (firstCardRank == Card.cardRank.SIX)
                return 45;
            else if (firstCardRank == Card.cardRank.CINQ)
                return 40;
            else if (firstCardRank == Card.cardRank.QUATRE)
                return 35;
            else if (firstCardRank == Card.cardRank.TROIS)
                return 30;
            else if (firstCardRank == Card.cardRank.DEUX)
                return 20;
        }

        else if (firstCardRank == Card.cardRank.AS && secondCardRank == Card.cardRank.ROI
                || firstCardRank == Card.cardRank.ROI && secondCardRank == Card.cardRank.AS)
            return 60;
        else if (firstCardRank == Card.cardRank.AS && secondCardRank == Card.cardRank.DAME
                || firstCardRank == Card.cardRank.DAME && secondCardRank == Card.cardRank.AS)
            return 55;
        else if (firstCardRank == Card.cardRank.AS && secondCardRank == Card.cardRank.VALET
                || firstCardRank == Card.cardRank.VALET && secondCardRank == Card.cardRank.AS)
            return 50;
        else if (firstCardRank == Card.cardRank.DAME && secondCardRank == Card.cardRank.ROI
                || firstCardRank == Card.cardRank.ROI && secondCardRank == Card.cardRank.DAME)
            return 50;

        if (croupier.getMainDuCroupier().get(0).getCardColor() != croupier.getMainDuCroupier().get(1).getCardColor()
                && croupier.getMainDuCroupier().get(1).getCardColor() != croupier.getMainDuCroupier().get(2)
                        .getCardColor()
                && croupier.getMainDuCroupier().get(2).getCardColor() != croupier.getMainDuCroupier().get(0)
                        .getCardColor())
            return 40;
        if (croupier.getMainDuCroupier().get(0).getCardColor() == croupier.getMainDuCroupier().get(1).getCardColor()
                && croupier.getMainDuCroupier().get(1).getCardColor() == croupier.getMainDuCroupier().get(2)
                        .getCardColor()
                && croupier.getMainDuCroupier().get(2).getCardColor() == croupier.getMainDuCroupier().get(0)
                        .getCardColor())
            return 35;
        if (croupier.getMainDuCroupier().get(0).getCardColor() == croupier.getMainDuCroupier().get(1).getCardColor()
                || croupier.getMainDuCroupier().get(1).getCardColor() == croupier.getMainDuCroupier().get(2)
                        .getCardColor()
                || croupier.getMainDuCroupier().get(2).getCardColor() == croupier.getMainDuCroupier().get(0)
                        .getCardColor())
            return 30;

        return 10;
    }

}
