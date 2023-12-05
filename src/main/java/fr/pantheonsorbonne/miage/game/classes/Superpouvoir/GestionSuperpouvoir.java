package fr.pantheonsorbonne.miage.game.classes.Superpouvoir;

import fr.pantheonsorbonne.miage.game.classes.*;

public class GestionSuperpouvoir extends Superpouvoir {
    public GestionSuperpouvoir() {
        super();
    }

    @Override
    public void tirerCarteVisible(Joueur joueur, Deck deck) {
        int prix = 50;
        verification(joueur, prix);
        enleverSuperpouvoir();
        Card card = deck.tirer();
        joueur.ajouterCarte(card);
        card.montre();
    }

    @Override
    public void tirerCarteInvisible(Joueur joueur, Deck deck) {
        int prix = 100;
        verification(joueur, prix);
        enleverSuperpouvoir();
        Card card = deck.tirer();
        joueur.ajouterCarte(card);
    }

    @Override
    public void devoilerCarte(Joueur joueur, Joueur ennemis) {
        int prix = 50;
        verification(joueur, prix);
        enleverSuperpouvoir();
        ennemis.rendreCarteVisible();
    }

    @Override
    public void enleverCarte(Joueur joueur, Joueur ennemis) {
        int prix = 100;
        verification(joueur, prix);
        enleverSuperpouvoir();
        ennemis.enleverCarte();
    }

    @Override
    public int getPrix() {
        return 0;
    }

    @Override
    protected void Utiliser(Joueur joueur, Deck deck) {
    }

    @Override
    protected void Utiliser(Joueur joueur, Joueur adversaire) {
    }

}
