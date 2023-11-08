package fr.pantheonsorbonne.miage.game.classes;

import java.util.ArrayList;
import java.util.List;

public class Pot implements Comparable<Pot> {
    private int valeur;
    private int miseSeuil;
    private List<Joueur> joueurs;

    public Pot() {
        this.valeur = 0;
        this.miseSeuil = 0;
        this.joueurs = new ArrayList<>();
    }

    public Pot(List<Joueur> joueurs) {
        this.joueurs = new ArrayList<>(joueurs);
        this.valeur = 0;
        this.miseSeuil = 0;
    }

    public void ajouterJoueur(Joueur joueur) {
        this.joueurs.add(joueur);
    }

    public void ajouterMise(int mise) {
        this.valeur += mise;
    }

    public void reinitialiserPot() {
        this.valeur = 0;
        this.joueurs.clear();
    }

    public int getValeur() {
        return valeur;
    }

    public void setValeur(int valeur) {
        this.valeur = valeur;
    }

    public List<Joueur> getJoueurs() {
        return new ArrayList<>(joueurs);
    }

    public void setJoueurs(List<Joueur> joueurs) {
        this.joueurs = new ArrayList<>(joueurs);
    }

    public int getMiseSeuil() {
        return miseSeuil;
    }

    public void setMiseSeuil(int miseSeuil) {
        this.miseSeuil = miseSeuil;
    }

    @Override
    public int compareTo(Pot autrePot) {
        return this.getValeur() - autrePot.getValeur();
    }

    @Override
    public String toString() {
        return "Ce pot a une valeur de " + this.valeur + " et une mise minimale de " + this.miseSeuil;
    }
}
