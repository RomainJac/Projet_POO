package fr.pantheonsorbonne.miage.game.classes.Joueur;

import java.util.Collections;
import java.util.List;
import java.util.Random;

import fr.pantheonsorbonne.miage.game.classes.Cartes.Card;
import fr.pantheonsorbonne.miage.game.classes.Cartes.CardColor;
import fr.pantheonsorbonne.miage.game.classes.Logique.CombinaisonGagnante;
import fr.pantheonsorbonne.miage.game.classes.Superpouvoir.GestionSuperpouvoir;
import fr.pantheonsorbonne.miage.game.classes.Table.MainDuCroupier;
import fr.pantheonsorbonne.miage.game.classes.Cartes.CardRank;



public class Joueur implements Comparable<Joueur> {

    protected String nom;
    protected int pileDeJetons;
    protected int mise;
    protected CombinaisonGagnante combinaison;
    protected MainDuJoueur mainDuJoueur;
    protected GestionSuperpouvoir superpouvoir;
    protected CardColor couleurInverse; 


    public boolean estTapis;

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

    public void InverserCouleur(CardColor inverse) {
		this.couleurInverse=inverse;
	}

    public int demandeCouleurInverse() {
		return 2;
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
        CardRank firstCardRank = mainDuJoueur.getMainDuJoueur().get(0).getCardRank();
        CardRank secondCardRank = mainDuJoueur.getMainDuJoueur().get(1).getCardRank();
        if (firstCardRank.equals(secondCardRank)) {
            if (firstCardRank == CardRank.AS)
                return 85;
            else if (firstCardRank == CardRank.ROI)
                return 80;
            else if (firstCardRank == CardRank.DAME)
                return 75;
            else if (firstCardRank == CardRank.VALET)
                return 70;
            else if (firstCardRank == CardRank.DIX)
                return 65;
            else if (firstCardRank == CardRank.NEUF)
                return 60;
            else if (firstCardRank == CardRank.HUIT)
                return 55;
            else if (firstCardRank == CardRank.SEPT)
                return 50;
            else if (firstCardRank == CardRank.SIX)
                return 45;
            else if (firstCardRank == CardRank.CINQ)
                return 40;
            else if (firstCardRank == CardRank.QUATRE)
                return 35;
            else if (firstCardRank == CardRank.TROIS)
                return 30;
            else if (firstCardRank == CardRank.DEUX)
                return 20;
        }

        else if (firstCardRank == CardRank.AS && secondCardRank == CardRank.ROI
                || firstCardRank == CardRank.ROI && secondCardRank == CardRank.AS)
            return 60;
        else if (firstCardRank == CardRank.AS && secondCardRank == CardRank.DAME
                || firstCardRank == CardRank.DAME && secondCardRank == CardRank.AS)
            return 55;
        else if (firstCardRank == CardRank.AS && secondCardRank == CardRank.VALET
                || firstCardRank == CardRank.VALET && secondCardRank == CardRank.AS)
            return 50;
        else if (firstCardRank == CardRank.DAME && secondCardRank == CardRank.ROI
                || firstCardRank == CardRank.ROI && secondCardRank == CardRank.DAME)
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

    public int faireChoixSuperPouvoir() {
        if (getPileDeJetons() > 300) {
            return 4;
        }
        return 5;

    }

    public GestionSuperpouvoir getSuperpouvoir() {
        return this.superpouvoir;
    }

}
