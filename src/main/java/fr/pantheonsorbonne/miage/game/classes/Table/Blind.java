package fr.pantheonsorbonne.miage.game.classes.Table;

import fr.pantheonsorbonne.miage.game.classes.Joueur.Joueur;

/**
 * La classe Blind représente les mises obligatoires (blinds) dans une partie de
 * poker.
 */
public class Blind {

    private int valeur;
    private Joueur joueur;

    public Blind(int valeur) {
        this(valeur, null);
    }

    public Blind(int valeur, Joueur joueur) {
        this.valeur = valeur;
        this.joueur = joueur;
    }

    public int getValeur() {
        return this.valeur;
    }

    public Joueur getJoueur() {
        return this.joueur;
    }

    public void augmenter(int combien) {
        this.valeur += combien;
    }

    public void setJoueur(Joueur joueur) {
        this.joueur = joueur;
    }

}
