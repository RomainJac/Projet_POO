package fr.pantheonsorbonne.miage.game.classes.Superpouvoir;

import fr.pantheonsorbonne.miage.game.classes.Joueur.Joueur;
import fr.pantheonsorbonne.miage.game.classes.Table.Deck;

import java.util.HashSet;

public abstract class Superpouvoir {
    private int prix;
    private HashSet<Joueur> joueursSansPouvoir;
    public int nbUtilisation;

    public Superpouvoir() {
        this.joueursSansPouvoir = new HashSet<>();
    }

    /**
     * Vérifie si un joueur peut utiliser le superpouvoir en fonction de son nombre
     * de jetons.
     * 
     * @param joueur Le joueur pour lequel la vérification est effectuée.
     * @return true si le joueur peut utiliser le superpouvoir, sinon false.
     */
    public boolean peutSuperpouvoir(Joueur joueur) {
        return joueur.getPileDeJetons() >= this.prix;
    }

    /**
     * Vérifie si un joueur n'a pas le pouvoir actif.
     * 
     * @param joueur Le joueur pour lequel la vérification est effectuée.
     * @return true si le joueur n'a pas le pouvoir actif, sinon false.
     */
    public boolean sansPouvoir(Joueur joueur) {
        return this.joueursSansPouvoir.contains(joueur);
    }

    /**
     * Redonne le droit d'utiliser le superpouvoir à tous les joueurs.
     */
    public void redonneDroit() {
        this.joueursSansPouvoir.clear();
    }

    /**
     * Effectue la vérification et déduit le coût d'utilisation du superpouvoir du
     * nombre de jetons du joueur.
     * 
     * @param joueur Le joueur utilisant le superpouvoir.
     * @param tarif  Le coût d'utilisation du superpouvoir.
     */
    public void verification(Joueur joueur, int tarif) {
        if (this.peutSuperpouvoir(joueur) && joueur.getPileDeJetons() >= tarif) {
            joueur.setPileDeJetons(joueur.getPileDeJetons() - tarif);
            this.joueursSansPouvoir.add(joueur);
        } else {
            nbUtilisation++;
        }
    }

    public int getNbUtilisations() {
        return this.nbUtilisation;
    }

    public void enleverSuperpouvoir() {
        this.nbUtilisation++;
    }

    public abstract void tirerCarteVisible(Joueur joueur, Deck deck);

    public abstract void tirerCarteInvisible(Joueur joueur, Deck deck);

    public abstract void enleverCarte(Joueur joueur, Joueur adversaire);

    public abstract void devoilerCarte(Joueur joueur, Joueur adversaire);
}