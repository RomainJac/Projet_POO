
package fr.pantheonsorbonne.miage.game.classes;

public class PokerLocal {

    public static void main(String[] args) {
        TableDePoker table = new TableDePoker(new Joueur("Romain", 400), new Joueur("Aymeric", 400), new Joueur("Abel", 400), new Joueur("Noam", 400));
        table.run();

    }
}
