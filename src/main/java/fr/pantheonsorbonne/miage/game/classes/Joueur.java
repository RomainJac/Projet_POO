package fr.pantheonsorbonne.miage.game.classes;

import java.util.ArrayList;

public class Joueur implements Comparable<Joueur> {

    private String nom;
    private int pileDeJetons;
    private int mise;
    private CombinaisonGagnante combinaison;
    private MainDuJoueur mainDuJoueur;
    private boolean enJeu;
    private boolean nAPasSuivi = true;
    private boolean nAPasPassé = true;
    private boolean estEnTrainDeRelancer;

    public Joueur(String nom) {
        this(nom, 0);
    }

    public Joueur(String nom, int jetons) {
        this.nom = nom;
        this.pileDeJetons = jetons;
        this.enJeu = (jetons > 0);
        this.nAPasSuivi = true;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
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

    public void setCombinaison(CombinaisonGagnante combinaison) {
        this.combinaison = combinaison;
    }

    public MainDuJoueur getMainDuJoueur() {
        return mainDuJoueur;
    }

    public void setMainDuJoueur(MainDuJoueur mainDuJoueur) {
        this.mainDuJoueur = mainDuJoueur;
    }

    public boolean estEnJeu() {
        return enJeu;
    }

    public void setEnJeu(boolean enJeu) {
        this.enJeu = enJeu;
    }

    public void setMain(MainDuJoueur Cards) {
        this.mainDuJoueur = Cards;
    }

    public void setCombinaisonGagnante(CombinaisonGagnante conditionVictoire) {
        this.combinaison = conditionVictoire;
    }

    public void ajouterCard(ArrayList<Card> Cards) {
        for (Card Card : Cards) {
            this.mainDuJoueur.ajouter(Card);
        }
    }

    public void afficherMain() {
        System.out.println(this.nom + " a la main suivante :");
        for (Card Card : this.mainDuJoueur.getMainDuJoueur()) {
            System.out.println(Card);
        }
    }

    public int tapis() {
        if (!this.enJeu) {
            return 0;
        }
        this.mise += this.pileDeJetons;
        this.pileDeJetons = 0;
        return this.mise;
    }

    public int miser(int combien) {
        if (!this.enJeu) {
            return 0;
        }
        if (combien < 0) {
            return 0;
        }
        if (this.pileDeJetons - combien < 0) {
            return tapis();
        }
        this.pileDeJetons -= combien;
        this.nAPasSuivi = true;
        this.mise += combien;
        return this.mise;
    }

    public void suivre(int combien) {
        this.miser(combien);
    }

    
    public void relancer(int miseLaPlusHaute, int montantRelance) {
    int montantTotal = miseLaPlusHaute + montantRelance;
    if (this.getPileDeJetons() >= montantTotal) {
        this.miser(montantTotal); 
        System.out.println(this.getNom() + " a relancé de " + montantRelance + " pour un total de " + montantTotal + ".");
    } else {
        System.out.println(this.getNom() + " n'a pas assez de jetons et va tapis.");
        this.miser(this.getPileDeJetons());
    }
}


    public void seCoucher() {
        this.nAPasSuivi = false;
    }

    public void aPerdu() {
        this.nAPasSuivi = false;
        this.mise = 0;
    }

    public void aGagné(int gains) {
        this.pileDeJetons += gains;
        this.mise = 0;
    }

    public void ajouterAStackDeJetons(int n) {
        this.pileDeJetons += n;
    }

    public boolean nAPasSuivi() {
        return nAPasSuivi;
    }

    public boolean nAPasPassé() {
        return nAPasPassé;
    }

    public void setNAPasSuivi(boolean nAPasSuivi) {
        this.nAPasSuivi = nAPasSuivi;
    }

    @Override
    public int compareTo(Joueur joueur) {
        return (this.getCombinaison().compareTo(joueur.getCombinaison()));
    }

    public boolean estEnTrainDeRelancer() {
        return estEnTrainDeRelancer;
    }

    public void setEstEnTrainDeRelancer(boolean estEnTrainDeRelancer) {
        this.estEnTrainDeRelancer = estEnTrainDeRelancer;
    }

    public boolean estTapis() {
        return this.pileDeJetons == 0;
    }

    public int getMontantDeMise() {
        return this.getPileDeJetons();
    }

    public String toString() {
        return this.nom + " jetons actuels : " + this.pileDeJetons;
    }

    public void ajouterCard(Card Card) {
        this.mainDuJoueur.ajouter(Card);
    }

    public void retirerCarteAleatoire() {
        this.mainDuJoueur.retirerCarteAleatoire();
    }
}
