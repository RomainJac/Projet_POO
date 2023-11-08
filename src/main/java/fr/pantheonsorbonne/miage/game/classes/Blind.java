package fr.pantheonsorbonne.miage.game.classes;

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

    @Override
    public String toString() {
        return this.joueur.getNom() + " doit payer " + this.valeur;
    }
}
