package fr.pantheonsorbonne.miage.game.classes.Table;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import fr.pantheonsorbonne.miage.game.classes.Cartes.Card;
import fr.pantheonsorbonne.miage.game.classes.Cartes.CardColor;
import fr.pantheonsorbonne.miage.game.classes.Joueur.Joueur;
import fr.pantheonsorbonne.miage.game.classes.Joueur.MainDuJoueur;
import fr.pantheonsorbonne.miage.game.classes.Logique.ConditionDeVictoire;
import fr.pantheonsorbonne.miage.game.classes.Superpouvoir.GestionSuperpouvoir;

public class TableDePoker implements Runnable {
	public List<Joueur> joueurs;
	public List<Joueur> joueursActifs;
	public MainDuCroupier croupier;
	public Deck deck;
	public int misesTotales;
	public int miseMaximale;
	public int nombreDeTours;
	public Blind grosseBlind;
	public Blind petiteBlind;
	public Blind dealerBlind;
	public int grosseBlindParDefaut = 10;
	protected CardColor couleurAtout;

	public TableDePoker(Joueur... joueurs) {
		this.joueurs = new CopyOnWriteArrayList<>(joueurs);
		this.deck = new Deck();
		this.croupier = new MainDuCroupier(deck);
		this.joueursActifs = new ArrayList<>(List.of(joueurs));
		this.misesTotales = 0;
		this.miseMaximale = 0;
	}

	/**
	 * Utilisation d'un runnable afin de rendre l'appel du jeu plus propre.
	 */
	@Override
	public void run() {
		jouer();
	}

	/**
	 * Méthode principale pour gérer le déroulement d'une partie de poker.
	 */
	public void jouer() {
		while (joueursActifs.size() > 1) {
			initialiserTour();

			// Trois phases de tirage de cartes
			for (int i = 1; i < 4; i++) {
				croupier.tirerCarte(i);
				System.out.println("Croupier :");
				for (Card card : croupier.getMainDuCroupier()) {
					System.out.println(card.CardToString(card));
				}

				// Phase où chaque joueur prend des décisions et utilise éventuellement un
				// superpouvoir
				for (Joueur joueur : joueursActifs) {
					int choixJoueur = joueur.faireChoix(croupier, miseMaximale, i);
					afficherInfosJoueur(joueur);
					gererChoix(joueur, choixJoueur, miseMaximale);
					int choixSuperpouvoir = joueur.faireChoixSuperPouvoir();
					gererSuperpouvoir(joueur, choixSuperpouvoir, deck, joueur);
				}
			}

			// Affichage des mains, détermination des gagnants, enlèvement des joueurs sans
			// jetons
			afficherToutesLesMains();
			determinerGagnant(joueurs);
			enleverJoueurSansJeton();

			// Affichage des gagnants et réinitialisation de la table
			for (Joueur joueur : joueursActifs) {
				System.out.println(joueur.getNom() + " a Gagné");
			}
			System.out.println("Fin de la partie");
			réinitialiserTable();
		}
	}

	/**
	 * Méthode pour gérer les choix des joueurs pendant le tour.
	 *
	 * @param joueur       Le joueur effectuant le choix.
	 * @param choix        Le choix effectué par le joueur.
	 * @param miseMaximale La mise maximale du joueur ayant le plus misé.
	 * @return Les mises totales après le choix du joueur.
	 */
	public int gererChoix(Joueur joueur, int choix, int miseMaximale) {
		switch (choix) {
			case 1 -> {
				if (miseMaximale - joueur.getMise() >= 0 && miseMaximale > 0) {
					misesTotales += joueur.miser(miseMaximale - joueur.getMise());
				} else
					misesTotales += joueur.miser(joueur.getMise() - miseMaximale);
				System.out.println(joueur.getNom() + " a suivi");
			}
			case 2 -> {
				System.out.println(joueur.getNom() + " s'est couché");
				joueursActifs.remove(joueur);
			}
			case 3 -> {
				misesTotales += faireRelance(joueur, 50);
			}
		}

		return misesTotales;
	}

	/**
	 * Méthode pour effectuer une relance pendant le tour.
	 *
	 * @param joueur  Le joueur effectuant la relance.
	 * @param montant Le montant de la relance.
	 * @return La nouvelle mise après la relance.
	 */
	public int faireRelance(Joueur joueur, int montant) {
		int nouvelleMise = miseMaximale + montant;

		if (joueur.getPileDeJetons() < nouvelleMise) {
			nouvelleMise = joueur.getPileDeJetons();
		}

		miseMaximale = nouvelleMise;
		joueur.miser(nouvelleMise);

		return nouvelleMise;
	}

	/**
	 * Méthode pour distribuer les gains aux joueurs gagnants.
	 *
	 * @param joueursGagnants Liste des joueurs gagnants.
	 */
	public void distribuerGains(List<Joueur> joueursGagnants) {
		if (joueursGagnants != null) {
			for (Joueur joueur : joueursGagnants) {
				if (joueur.estTapis) {
					if (misesTotales > joueur.getMise() * 2) {
						joueur.aGagné(joueur.getMise() * 2);
						misesTotales -= joueur.getMise() * 2;
						joueursGagnants.remove(joueur);
						break;
					}
					joueur.aGagné(misesTotales / joueursGagnants.size());
					break;
				} else {
					joueur.aGagné(misesTotales / joueursGagnants.size());
				}
			}

			for (Joueur joueur : joueurs) {
				if (!joueursGagnants.contains(joueur)) {
				}
			}
		}
	}

	public void réinitialiserTable() {
		réinitialiserJoueurs();
		if (!joueursActifs.isEmpty()) {
			Deck.reinitialiserDeck();
			croupier.viderMainCroupier();
			nombreDeTours++;
			if (nombreDeTours % 5 == 0) {
				augmenterBlinds();
			}
		}
		misesTotales = 0;
		miseMaximale = 0;
	}

	/**
	 * Méthode pour afficher les cartes dans la main d'un joueur.
	 *
	 * @param joueur Le joueur dont on affiche les cartes.
	 */
	public void afficherInfosJoueur(Joueur joueur) {
		List<String> cardNames = joueur.getCardNames();

		System.out.print("Cartes de " + joueur.getNom() + " : ");
		for (String cardName : cardNames) {
			System.out.print(cardName + " ");
		}
		System.out.println();
	}

	/**
	 * Méthode pour initialiser un nouveau tour de jeu.
	 */
	public void initialiserTour() {
		deck.initialiserDeck();
		distribuerCartes();
		changerBlinds();
		demanderPaiementBlinds();
		changerAtout();
	}

	/**
	 * Méthode pour gérer l'utilisation d'un superpouvoir par un joueur.
	 *
	 * @param joueur  Le joueur utilisant le superpouvoir.
	 * @param choix   Le choix du superpouvoir effectué par le joueur.
	 * @param deck    Le paquet de cartes.
	 * @param ennemis Le joueur cible du superpouvoir.
	 */
	public void gererSuperpouvoir(Joueur joueur, int choix, Deck deck, Joueur ennemis) {
		GestionSuperpouvoir superpouvoir = new GestionSuperpouvoir();
		switch (choix) {
			case 1:
				superpouvoir.tirerCarteVisible(joueur, deck);
				break;
			case 2:
				superpouvoir.tirerCarteInvisible(joueur, deck);
				break;
			case 3:
				superpouvoir.enleverCarte(joueur, ennemis);
				break;
			case 4:
				superpouvoir.devoilerCarte(joueur, ennemis);
				break;
			case 5:
				break;
		}
	}

	/**
	 * Méthode pour changer les positions des blinds (grosse, petite et dealer
	 * blinds).
	 */
	public void changerBlinds() {
		int n = this.joueursActifs.size();
		if (this.grosseBlind == null) {
			this.grosseBlind = new Blind(this.grosseBlindParDefaut, this.joueurs.get(n - 1));
			this.petiteBlind = new Blind(this.grosseBlindParDefaut / 2, this.joueurs.get(n - 2));
			this.dealerBlind = new Blind(0, this.joueurs.get(Math.max(0, n - 3)));
		}

		int indexGrosseBlind = 0, indexPetiteBlind = 0, indexcroupier = 0;
		for (int i = 0; i < n; i++) {
			if (this.joueursActifs.get(i) == this.grosseBlind.getJoueur()) {
				indexGrosseBlind = i;
			} else if (this.joueursActifs.get(i) == this.petiteBlind.getJoueur()) {
				indexPetiteBlind = i;
			} else if (this.joueursActifs.get(i) == this.dealerBlind.getJoueur()) {
				indexcroupier = i;
			}
		}
		indexGrosseBlind = (indexGrosseBlind + 1) % n;
		indexPetiteBlind = (indexPetiteBlind + 1) % n;
		indexcroupier = (indexcroupier + 1) % n;
		this.grosseBlind.setJoueur(this.joueursActifs.get(indexGrosseBlind));
		this.petiteBlind.setJoueur(this.joueursActifs.get(indexPetiteBlind));
		this.dealerBlind.setJoueur(this.joueursActifs.get(indexcroupier));
	}

	/**
	 * Méthode pour enlever les joueurs sans jetons de la liste des joueurs actifs.
	 */
	public void enleverJoueurSansJeton() {
		joueursActifs.removeIf(joueur -> joueur.getPileDeJetons() == 0);
	}

	/**
	 * Méthode pour distribuer deux cartes à chaque joueur actif.
	 */
	public void distribuerCartes() {
		for (Joueur joueur : this.joueursActifs) {
			joueur.setMain(new MainDuJoueur(this.deck.CarteAleatoires(2)));
		}
	}

	public List<Joueur> determinerGagnant(List<Joueur> joueurs) {
		List<Joueur> joueursGagnants = new CopyOnWriteArrayList<>();

		for (Joueur joueur : joueurs) {
			joueur.setCombinaisonGagnante(
					ConditionDeVictoire.trouverMeilleureCombinaison(croupier, joueur.getMainDuJoueur()));
		}

		Collections.sort(joueurs);
		if (!joueurs.isEmpty()) {
			joueursGagnants.add(joueurs.get(joueurs.size()-2));

		}
		// for (Joueur joueur : joueurs) {
		// 	if (joueur.getCombinaison().equals(joueursGagnants.get(0).getCombinaison())
		//  			&& !joueur.getNom().equals(joueursGagnants.get(0).getNom())) {
		// 		joueursGagnants.add(joueur);
		// 	}
		//  }

		distribuerGains(joueursGagnants);

		return joueursGagnants;
	}

	/**
	 * Méthode pour réinitialiser les joueurs à la fin d'une partie.
	 *
	 * @return Liste des joueurs actifs après réinitialisation.
	 */
	public List<Joueur> réinitialiserJoueurs() {
		for (Joueur joueur : joueurs) {
			joueur.setMise(0);
			joueur.setMainDuJoueur(null);
			this.joueursActifs = joueurs;
			enleverJoueurSansJeton();
		}
		return joueursActifs;
	}

	/**
	 * Méthode pour demander le paiement des blinds aux joueurs concernés.
	 *
	 * @return Total des mises des blinds.
	 */
	public int demanderPaiementBlinds() {
		this.grosseBlind.getJoueur()
				.setPileDeJetons(this.grosseBlind.getJoueur().getPileDeJetons() - this.grosseBlind.getValeur());
		this.grosseBlind.getJoueur().setMise(this.grosseBlind.getValeur());
		this.petiteBlind.getJoueur()
				.setPileDeJetons(this.petiteBlind.getJoueur().getPileDeJetons() - this.petiteBlind.getValeur());
		this.petiteBlind.getJoueur().setMise(this.petiteBlind.getValeur());
		return misesTotales = misesTotales + grosseBlind.getValeur() + petiteBlind.getValeur();
	}

	/**
	 * Méthode pour augmenter la valeur des blinds au bout de 5 tours.
	 */
	public void augmenterBlinds() {
		this.grosseBlind.augmenter(grosseBlindParDefaut);
		this.petiteBlind.augmenter(grosseBlindParDefaut / 2);
	}

	public void afficherToutesLesMains() {
		for (Joueur joueur : joueurs) {
			joueur.afficherMain();
		}
	}

	/**
	 * Méthode choisir l'atout de la donne.
	 */
	protected void changerAtout() {
		Joueur joueur = this.joueursActifs.get(0);
		int answer = (joueur).demandeCouleurInverse();
		this.setAtout(answer);
	}

	/**
	 * Méthode pour définir la couleur inversée (atout) du jeu.
	 *
	 * @param couleur La couleur choisie pour inverser.
	 */
	protected void setAtout(int couleur) {
		CardColor inverse = null;
		switch (couleur) {
			case 0:
				inverse = CardColor.PIQUE;
				break;
			case 1:
				inverse = CardColor.COEUR;
				break;
			case 2:
				inverse = CardColor.CARREAU;
				break;
			case 3:
				inverse = CardColor.TREFLE;
				break;
			default:
				break;
		}
		this.couleurAtout = inverse;
		for (Joueur joueur : joueursActifs) {
			joueur.InverserCouleur(inverse);
		}
	}

	public int getMisesTotales() {
		return misesTotales;
	}

	public List<Joueur> getJoueursActifs() {
		return joueursActifs;
	}

	public int getMiseMaximale() {
		return this.miseMaximale;
	}

	public void setMisesTotales(int misesTotales) {
		this.misesTotales = misesTotales;
	}

	public void setMiseMaximale(int miseMaximale) {
		this.miseMaximale = miseMaximale;
	}

	public Integer getNombreDeTours() {
		return nombreDeTours;
	}

	public void setJoueursActifs(List<Joueur> joueurActifs) {
		this.joueursActifs = joueurActifs;
	}

	public Blind getGrosseBlind() {
		return this.grosseBlind;
	}

	public Blind getPetiteBlind() {
		return this.petiteBlind;
	}

	public Blind getDealerBlind() {
		return this.dealerBlind;
	}

}
