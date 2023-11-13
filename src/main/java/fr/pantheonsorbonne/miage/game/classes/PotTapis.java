package fr.pantheonsorbonne.miage.game.classes;

import java.util.ArrayList;

public class PotTapis extends Pot {
    private Joueur joueur;
    private int miseAllIn;

    public PotTapis(int miseAllIn, Joueur joueur) {
        super();
        this.setJoueur(joueur);
        this.setMiseAllIn(miseAllIn);
    }

    public PotTapis(ArrayList<Joueur> joueurs) {
        super(joueurs);
        this.miseAllIn = joueurs.get(0).getMise();
    }

    public int getMiseAllIn() {
        return miseAllIn;
    }

    public void setMiseAllIn(int miseAllIn) {
        this.miseAllIn = miseAllIn;
    }

    public Joueur getJoueur() {
        return joueur;
    }

    public void setJoueur(Joueur joueur) {
        this.joueur = joueur;
    }
}
