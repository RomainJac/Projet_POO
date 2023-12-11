
package fr.pantheonsorbonne.miage.game.classes.Main;

import fr.pantheonsorbonne.miage.game.classes.Joueur.Joueur;
import fr.pantheonsorbonne.miage.game.classes.Table.TableDePoker;

public class PokerLocal {

    public static void main(String[] args) {
        TableDePoker table = new TableDePoker(new Joueur("Romain", 400), new Joueur("Aymeric", 400));

        int i = 0;
        int nbParties = 100;
        while (i < nbParties)
            table.run();
        i++;

    }
}
