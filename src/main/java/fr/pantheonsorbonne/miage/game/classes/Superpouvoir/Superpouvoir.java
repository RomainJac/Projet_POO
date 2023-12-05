package fr.pantheonsorbonne.miage.game.classes.Superpouvoir;

import fr.pantheonsorbonne.miage.game.classes.*;

import java.util.HashSet;

abstract class Superpouvoir implements SuperpouvoirConstante {
    private int prix;
    private HashSet<Joueur> joueursSansPouvoir;
    private int nbUtilisation;

    protected Superpouvoir() {
        this.joueursSansPouvoir = new HashSet<>();
    }

    protected boolean peutSuperpouvoir(Joueur joueur) {
        return joueur.getPileDeJetons() >= this.prix;
    }

    protected boolean sansPouvoir(Joueur joueur) {
        return this.joueursSansPouvoir.contains(joueur);
    }

    public void redonneDroit() {
        this.joueursSansPouvoir.clear();
    }

    protected void verification(Joueur joueur, int tarif) {
        if (this.peutSuperpouvoir(joueur)) {
            joueur.setPileDeJetons(joueur.getPileDeJetons() - tarif);
            this.joueursSansPouvoir.add(joueur);
        } else {
            throw new IllegalArgumentException("Le joueur n'a pas assez de jetons pour utiliser ce superpouvoir");
        }
    }

    public int getNbUtilisations() {
        return nbUtilisation;
    }

    protected void enleverSuperpouvoir() {
        this.nbUtilisation++;
    }

    public abstract void tirerCarteVisible(Joueur joueur, Deck deck);

    public abstract void tirerCarteInvisible(Joueur joueur, Deck deck);

    public abstract void enleverCarte(Joueur joueur, Joueur adversaire);

    public abstract void devoilerCarte(Joueur joueur, Joueur adversaire);

    protected abstract void Utiliser(Joueur joueur, Deck deck);

    protected abstract void Utiliser(Joueur joueur, Joueur adversaire);
}
