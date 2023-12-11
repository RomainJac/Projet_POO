package fr.pantheonsorbonne.miage.game.classes.Superpouvoir;

import fr.pantheonsorbonne.miage.game.classes.Cartes.Card;
import fr.pantheonsorbonne.miage.game.classes.Joueur.Joueur;
import fr.pantheonsorbonne.miage.game.classes.Table.Deck;

public class GestionSuperpouvoir extends Superpouvoir {
    public GestionSuperpouvoir() {
        super();
    }

    @Override
    public void tirerCarteVisible(Joueur joueur, Deck deck) {
        verification(joueur, 50);
        enleverSuperpouvoir();
        Card card = deck.tirer();
        joueur.ajouterCarte(card);
        card.montre();
    }

    @Override
    public void tirerCarteInvisible(Joueur joueur, Deck deck) {
        verification(joueur, 100);
        enleverSuperpouvoir();
        Card card = deck.tirer();
        joueur.ajouterCarte(card);
    }

    @Override
    public void devoilerCarte(Joueur joueur, Joueur ennemis) {
        verification(joueur, 50);
        enleverSuperpouvoir();
        ennemis.rendreCarteVisible();
    }

    @Override
    public void enleverCarte(Joueur joueur, Joueur ennemis) {
        verification(joueur, 100);
        enleverSuperpouvoir();
        ennemis.enleverCarte();
    }

    @Override
    protected void Utiliser(Joueur joueur, Deck deck) {
    }

    @Override
    protected void Utiliser(Joueur joueur, Joueur adversaire) {
    }

}
