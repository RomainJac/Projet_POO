package fr.pantheonsorbonne.miage.game.classes;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Joueur implements Comparable<Joueur> {

    private String nom;
    private int pileDeJetons;
    private int mise;
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

    public void setCombinaison(CombinaisonGagnante combinaison) {
        this.combinaison = combinaison;
    }

    public void setMainDuJoueur(MainDuJoueur mainDuJoueur) {
        this.mainDuJoueur = mainDuJoueur;
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

    public void tapis() {
        this.mise += this.pileDeJetons;
        this.pileDeJetons = 0;
        this.estTapis = true;
    }

    public int miser(int combien) {
        if (combien == 0) {
            System.out.print("Check");
        }
        if (this.pileDeJetons - combien < 0) {
            System.out.println(this.getNom() + " n'a pas assez de jetons et va tapis.");
            tapis();
            return getPileDeJetons();
        }
        else {
            this.mise += combien;
            System.out.println(
                    this.getNom() + " a relancé de " + combien + " pour une mise total de " + mise + ".");
            this.pileDeJetons -= combien;
            return pileDeJetons-combien;
        }

    }

    public void aGagné(int gain) {
        this.pileDeJetons += gain;
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
}
