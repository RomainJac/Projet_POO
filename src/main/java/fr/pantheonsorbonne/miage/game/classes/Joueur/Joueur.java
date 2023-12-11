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

    /**
     * Effectue une mise.
     * 
     * @param combien Le montant de la mise.
     * @return Le montant effectivement misé.
     */
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

    /**
     * Rend une carte aléatoire de la main du joueur visible.
     */
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

    /**
     * Inverse la couleur du joueur.
     * 
     * @param inverse La nouvelle couleur inversée.
     */
    public void InverserCouleur(CardColor inverse) {
        this.couleurInverse = inverse;
    }

    /**
     * Demande la couleur inversée au joueur.
     * 
     * @return La couleur inversée demandée.
     *         Le Robot retounera toujours la même valeur
     */
    public int demandeCouleurInverse() {
        return 2;
    }

    /**
     * Prend une décision sur la mise pendant un tour de jeu.
     * 
     * @param croupier     La main du croupier.
     * @param miseMaximale La mise maximale autorisée.
     * @param tour         Le numéro du tour actuel.
     * @return Le choix du joueur en matière de mise.
     */
    public int faireChoix(MainDuCroupier croupier, int miseMaximale, int tour) {
        int probabiliteDeGagner = probabiliteDeGagner(croupier);
        if (probabiliteDeGagner > 20 && probabiliteDeGagner <= 50 && tour > 1)
            return 1; // Choisir de miser
        else if (probabiliteDeGagner > 50 && getPileDeJetons() > miseMaximale
                || probabiliteDeGagner > 20 && probabiliteDeGagner <= 50 && tour == 1)
            return 3; // Choisir de relancer
        return 2; // Choisir de suivre
    }

    /**
     * Prend une décision sur l'utilisation du superpouvoir.
     * 
     * @return Le choix du joueur en matière de superpouvoir.
     */
    public int faireChoixSuperPouvoir() {
        if (getPileDeJetons() > 300) {
            return 4; // Utiliser un superpouvoir pour voir une carte de l'adversaire
        }
        return 5; // Ne pas utiliser le superpouvoir
    }

    /**
     * Calcule la probabilité de gagner du joueur en fonction de sa main et de celle
     * du croupier.
     * 
     * @param croupier La main du croupier.
     * @return La probabilité de gagner en pourcentage.
     */
    public int probabiliteDeGagner(MainDuCroupier croupier) {
        CardRank firstCardRank = mainDuJoueur.getMainDuJoueur().get(0).getCardRank();
        CardRank secondCardRank = mainDuJoueur.getMainDuJoueur().get(1).getCardRank();

        // Cas où les deux cartes du joueur ont la même valeur
        if (firstCardRank.equals(secondCardRank)) {
            // Assignation de probabilités en fonction de la valeur des cartes
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
        // Cas où les deux cartes du joueur ont des valeurs différentes
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

        // Comparaison des couleurs des cartes du croupier
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

        // Probabilité par défaut
        return 10;
    }

}
