package fr.pantheonsorbonne.miage.game.classes.Superpouvoir;

import fr.pantheonsorbonne.miage.game.classes.Joueur.Joueur;
import fr.pantheonsorbonne.miage.game.classes.Table.Deck;

import java.lang.IllegalArgumentException;

import java.util.HashSet;

public abstract class Superpouvoir {
    private int prix;
    private HashSet<Joueur> joueursSansPouvoir;
    public int nbUtilisation;

    public Superpouvoir() {
        this.joueursSansPouvoir = new HashSet<>();
    }

    public boolean peutSuperpouvoir(Joueur joueur) {
        return joueur.getPileDeJetons() >= this.prix;
    }

    public boolean sansPouvoir(Joueur joueur) {
        return this.joueursSansPouvoir.contains(joueur);
    }

    public void redonneDroit() {
        this.joueursSansPouvoir.clear();
    }

    public void verification(Joueur joueur, int tarif) throws IllegalArgumentException {

        if (this.peutSuperpouvoir(joueur) && joueur.getPileDeJetons() > tarif) {
            joueur.setPileDeJetons(joueur.getPileDeJetons() - tarif);
            this.joueursSansPouvoir.add(joueur);
        } else {
            nbUtilisation++;
        }
    }

    public int getNbUtilisations() {
        return nbUtilisation;
    }

    public void enleverSuperpouvoir() {
        this.nbUtilisation++;
    }    

    public abstract void tirerCarteVisible(Joueur joueur, Deck deck);

    public abstract void tirerCarteInvisible(Joueur joueur, Deck deck);

    public abstract void enleverCarte(Joueur joueur, Joueur adversaire);

    public abstract void devoilerCarte(Joueur joueur, Joueur adversaire);

    protected abstract void Utiliser(Joueur joueur, Deck deck);

    protected abstract void Utiliser(Joueur joueur, Joueur adversaire);
}
