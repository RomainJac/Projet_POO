
package fr.pantheonsorbonne.miage.game.classes;

import java.util.ArrayList;
import java.util.List;

public class PokerLocal {

    public static void main(String[] args) {

        Joueur j1 = new Joueur("Romain", 400);
        Joueur j2 = new Joueur("Aymeric", 400);
        List<Joueur> listeJoueurs = new ArrayList<>();
        listeJoueurs.add(j1);
        listeJoueurs.add(j2);
        TableDePoker table = new TableDePoker(listeJoueurs);
        while (table.joueursActifs.size() > 1) {
            table.jouerTour();
            System.out.println(j1.getNom() + ": " + j1.getPileDeJetons());
            System.out.println(j2.getNom() + ": " + j2.getPileDeJetons());

        }
    }
}
