package fr.pantheonsorbonne.miage.game.classes.Table;

import java.util.ArrayList;
import java.util.List;

import fr.pantheonsorbonne.miage.game.classes.Cartes.Card;

public class MainDuCroupier {
    private List<Card> mainDuCroupier;
    private Deck deck;

    public MainDuCroupier(Deck deck) {
        this.deck = deck;
        this.mainDuCroupier = new ArrayList<>();
    }

    /**
     * Tire une carte en fonction du paramètre spécifié.
     *
     * @param i Paramètre indiquant l'action à effectuer (1: turn, 2: flop, 3:
     *          river).
     */
    public void tirerCarte(int i) {
        switch (i) {
            case 1:
                initialiserMainDuCroupier(3);
                break;
            case 2:
            case 3:
                ajouterALaMainDuCroupier();
                break;
        }
    }

    /**
     * Ajoute une carte tirée du paquet à la main du croupier, si la main n'est pas
     * pleine.
     */
    public void ajouterALaMainDuCroupier() {
        if (mainDuCroupier.size() < 5) {
            mainDuCroupier.add(this.deck.tirer());
        }
    }

    /**
     * Initialise la main du croupier avec un nombre spécifié de cartes.
     *
     * @param nombreDeCarte Le nombre de cartes à initialiser dans la main du
     *                      croupier.
     */
    public void initialiserMainDuCroupier(int nombreDeCarte) {
        for (int i = 0; i < nombreDeCarte; i++) {
            ajouterALaMainDuCroupier();
        }
    }

    /**
     * Ajoute une carte spécifiée à la main du croupier, si la main n'est pas
     * pleine.
     *
     * @param card La carte à ajouter à la main du croupier.
     */
    public void ajouterALaMainDuCroupierCarte(Card card) {
        if (mainDuCroupier.size() < 5) {
            mainDuCroupier.add(card);
        }
    }

    public List<Card> getMainDuCroupier() {
        return this.mainDuCroupier;
    }

    /**
     * Affiche les cartes dans la main du croupier une par une.
     */
    public void afficherMain() {
        for (Card card : this.mainDuCroupier) {
            System.out.println(card.toString());
        }
    }

    public void viderMainCroupier() {
        this.mainDuCroupier.clear();
    }
}
