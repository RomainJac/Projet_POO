package fr.pantheonsorbonne.miage.game.classes.Superpouvoir;

import fr.pantheonsorbonne.miage.game.classes.Cartes.Card;
import fr.pantheonsorbonne.miage.game.classes.Joueur.Joueur;
import fr.pantheonsorbonne.miage.game.classes.Table.Deck;

public class GestionSuperpouvoir extends Superpouvoir {
    public GestionSuperpouvoir() {
        super();
    }

    /**
     * Permet au joueur de tirer une carte visible des autres joueurs du deck.
     * 
     * @param joueur Le joueur effectuant l'action.
     * @param deck   Le deck depuis lequel la carte est tirée.
     */
    @Override
    public void tirerCarteVisible(Joueur joueur, Deck deck) {
        verification(joueur, 50);
        enleverSuperpouvoir();
        Card card = deck.tirer();
        joueur.ajouterCarte(card);
        card.montre();
    }

    /**
     * Permet au joueur de tirer une carte invisible de autres joueurs du deck.
     * 
     * @param joueur Le joueur effectuant l'action.
     * @param deck   Le deck depuis lequel la carte est tirée.
     */
    @Override
    public void tirerCarteInvisible(Joueur joueur, Deck deck) {
        verification(joueur, 100);
        enleverSuperpouvoir();
        Card card = deck.tirer();
        joueur.ajouterCarte(card);
    }

    /**
     * Permet au joueur de dévoiler une carte d'un ennemi.
     * 
     * @param joueur  Le joueur effectuant l'action.
     * @param ennemis Le joueur ennemi dont la carte doit être dévoilée.
     */
    @Override
    public void devoilerCarte(Joueur joueur, Joueur ennemis) {
        verification(joueur, 50);
        enleverSuperpouvoir();
        ennemis.rendreCarteVisible();
    }

    /**
     * Permet au joueur de retirer une carte d'un ennemi.
     * 
     * @param joueur  Le joueur effectuant l'action.
     * @param ennemis Le joueur ennemi dont la carte doit être retirée.
     */
    @Override
    public void enleverCarte(Joueur joueur, Joueur ennemis) {
        verification(joueur, 100);
        enleverSuperpouvoir();
        ennemis.enleverCarte();
    }
}