package fr.pantheonsorbonne.miage.game.classes;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.CopyOnWriteArrayList;

public class TableDePoker {
	private List<Joueur> joueurs;
	public List<Joueur> joueursActifs;
	private MainDuCroupier croupier;
	private Deck deck;
	private int misesTotales;
	private int miseMaximale;
	private int nombreDeTours;
	private Blind grosseBlind;
	private Blind petiteBlind;
	private Blind dealerBlind;
	private final int petiteBlindParDefaut = 2;
	private Scanner scanner = new Scanner(System.in);

	public TableDePoker() {
		this.joueurs = new CopyOnWriteArrayList<>();
		this.joueursActifs = new CopyOnWriteArrayList<>();
		this.deck = new Deck();
		this.croupier = new MainDuCroupier(deck);
		this.misesTotales = 0;
		this.miseMaximale = 0;
	}


	public TableDePoker(List<Joueur> joueurs) {
		this.joueurs = new CopyOnWriteArrayList<>(joueurs);
		this.deck = new Deck();
		this.croupier = new MainDuCroupier(deck);
		this.joueursActifs = new CopyOnWriteArrayList<>(joueurs);
	}

	public void demanderMise() {
		if (joueursActifs.size() > 0) {
			for (Joueur joueur : joueursActifs) {
				if (!joueur.estTapis) {
					trouverMiseMaximale();
					afficherInfosJoueur(joueur);
					gererChoix(joueur, miseMaximale);
					System.out.println("Contenu du pot : " + misesTotales);
				} else {
					joueursActifs.remove(joueur);
					break;
				}
			}
		}

	}


	private void gererChoix(Joueur joueur, int miseMaximale) {
		System.out.print(joueur.getNom() + ", Choisissez une option : \n");
		System.out.println("1. Suivre");
		System.out.println("2. Se Coucher");
		System.out.println("3. Relancer");

		int choix = scanner.nextInt();

		switch (choix) {
			case 1:
				if (miseMaximale - joueur.getMise() > 0 && miseMaximale > 0) {
					misesTotales += joueur.miser(miseMaximale - joueur.getMise());
				}
				misesTotales += joueur.miser(joueur.getMise() - miseMaximale);
				break;
			case 2:
				System.out.println("Vous vous êtes couchés");
				joueursActifs.remove(joueur);
				break;
			case 3:
				if (joueur.getPileDeJetons() > miseMaximale) {
					misesTotales += faireRelance(joueur);
					break;
				} else {
					System.out.println("Vous n'avez pas assez pour relancer.");
				}
				gererChoix(joueur, miseMaximale);
				break;
			default:
				System.out.println("Option invalide. Veuillez choisir à nouveau.");
				gererChoix(joueur, miseMaximale);
		}

	}

	private int faireRelance(Joueur joueur) {
		System.out.print("Combien voulez-vous relancer ? : ");
		int x = scanner.nextInt();
		joueur.miser(miseMaximale + x);
		if (joueur.getPileDeJetons() < miseMaximale + x) {
			miseMaximale += joueur.getPileDeJetons();
		}
		return miseMaximale += x;

	}


	public void distribuerGains(List<Joueur> joueursGagnants) {
		if (joueursGagnants != null) {
			for (Joueur joueur : joueursGagnants) {
				if (joueur.estTapis) {
					System.out.println(joueur.getNom() + ", avec tapis a gagné,  " + joueur.getMise() * 2);
					joueur.aGagné(joueur.getMise() * 2);
				} else {
					System.out.println(joueur.getNom() + " a gagné " + misesTotales / joueursGagnants.size());
					joueur.aGagné(misesTotales / joueursGagnants.size());
				}
			}

		}
		for (Joueur joueur : joueurs) {
			if (!joueursGagnants.contains(joueur)) {
				System.out.println(joueur.getNom() + " a perdu : " + joueur.getMise());
			}
			joueursActifs.add(joueur);
		}
	}

	public void réinitialiserTable() {
		Deck.reinitialiserDeck();
		croupier.vider();
		nombreDeTours++;
		réinitialiserJoueurs();
		if (nombreDeTours % 5 == 0) {
			augmenterBlinds();
		}
		changerBlinds();
		misesTotales = 0;
		miseMaximale = 0;

	}


	public void jouerTour() {
		initialiserTour();
		for (int etape = 1; etape < 4; etape++) {
			switch (etape) {
				case 1:
					System.out.println("Cartes du croupier après le flop : ");
					croupier.flop();
					croupier.afficherMain();
					break;
				case 2:
					System.out.println("Cartes du croupier après le turn : ");
					croupier.turn();
					croupier.afficherMain();
					break;
				case 3:
					System.out.println("Cartes du croupier après la river : ");
					croupier.river();
					croupier.afficherMain();
					break;
			}

			demanderMise();

		}

	}

	private void afficherInfosJoueur(Joueur joueur) {
		effacerTerminal();

		String message = joueur.getNom() + " Votre mise est actuellement de " + joueur.getMise() + ", pour suivre, vous devez avoir misé " + miseMaximale;

		System.out.println(message);

		List<String> cardNames = joueur.getCardNames();


		System.out.print("Vos cartes : ");
		for (String cardName : cardNames) {
			System.out.print(cardName + " ");
		}
		System.out.println();

	}


	private void initialiserTour() {
		deck.initialiserDeck();
		distribuerCartes();
		initialiserBlinds();
		demanderPaiementBlinds();
		System.out.println("Contenu du pot : " + misesTotales);
	}

	private void trouverMiseMaximale() {
		for (Joueur joueur : this.joueursActifs) {
			if (joueur.getMise() > this.miseMaximale) {
				this.miseMaximale = joueur.getMise();
			}
		}
	}


	public void initialiserBlinds() {
		int n = this.joueurs.size();
		if (n <= 1) {
			return;
		}
		if (this.grosseBlind == null) {
			this.grosseBlind = new Blind(this.petiteBlindParDefaut, this.joueurs.get(n - 1));
			this.petiteBlind = new Blind(this.petiteBlindParDefaut / 2, this.joueurs.get(n - 2));
			this.dealerBlind = new Blind(0, this.joueurs.get(Math.max(0, n - 3)));
		}
		misesTotales = grosseBlind.getValeur() + petiteBlind.getValeur() + dealerBlind.getValeur();
	}

	public void changerBlinds() {
		int n = this.joueurs.size();
		int indexGrosseBlind = 0, indexPetiteBlind = 0, indexcroupier = 0;
		for (int i = 0; i < n; i++) {
			if (this.joueurs.get(i) == this.grosseBlind.getJoueur()) {
				indexGrosseBlind = i;
			} else if (this.joueurs.get(i) == this.petiteBlind.getJoueur()) {
				indexPetiteBlind = i;
			} else if (this.joueurs.get(i) == this.dealerBlind.getJoueur()) {
				indexcroupier = i;
			}
		}
		indexGrosseBlind = (indexGrosseBlind + 1) % n;
		indexPetiteBlind = (indexPetiteBlind + 1) % n;
		indexcroupier = (indexcroupier + 1) % n;
		this.grosseBlind.setJoueur(this.joueurs.get(indexGrosseBlind));
		this.petiteBlind.setJoueur(this.joueurs.get(indexPetiteBlind));
		this.dealerBlind.setJoueur(this.joueurs.get(indexcroupier));
	}

	public void enleverJoueurSansJeton() {
		this.joueursActifs.removeIf(joueur -> joueur.getPileDeJetons() == 0);
	}

	public void distribuerCartes() {
		if (deck == null) {
			System.out.println("Erreur : le deck n'est pas correctement initialisé.");
			return;
		}

		for (Joueur joueur : this.joueursActifs) {
			joueur.setMain(new MainDuJoueur(this.deck.CardsAleatoires(2)));
		}
	}

	public List<Joueur> determinerGagnant(List<Joueur> joueurs) {
		List<Joueur> joueursGagnants = new CopyOnWriteArrayList<>();

		for (Joueur joueur : joueurs) {
			joueur.setCombinaisonGagnante(
					ConditionDeVictoire.trouverMeilleureCombinaison(croupier, joueur.getMainDuJoueur()));
		}

		Collections.sort(joueurs);
		joueursGagnants.add(joueurs.get(joueurs.size() - 1));

		for (Joueur joueur : joueurs) {
			if (joueur.getCombinaison().equals(joueursGagnants.get(0).getCombinaison()) && !joueur.getNom().equals(joueursGagnants.get(0).getNom())) {
				joueursGagnants.add(joueur);
			}
		}

		distribuerGains(joueursGagnants);

		return joueursGagnants;
	}

	public void ajouterMise(int mise) {
		this.misesTotales += mise;
	}

	public void réinitialiserMisesJoueurs() {
		for (Joueur joueur : this.joueursActifs) {
			joueur.setMise(0);
		}
	}

	public void réinitialiserJoueurs() {
		for (Joueur joueur : joueurs) {
			joueur.setMise(0);
			joueur.setMainDuJoueur(null);
			this.joueursActifs = joueurs;
			enleverJoueurSansJeton();
		}

	}

	public void demanderPaiementBlinds() {
		this.grosseBlind.getJoueur().setPileDeJetons(this.grosseBlind.getJoueur().getPileDeJetons() - this.grosseBlind.getValeur());
		this.grosseBlind.getJoueur().setMise(this.grosseBlind.getValeur());
		this.petiteBlind.getJoueur().setPileDeJetons(this.grosseBlind.getJoueur().getPileDeJetons() - this.petiteBlind.getValeur());
		this.petiteBlind.getJoueur().setMise(this.petiteBlind.getValeur());
	}

	public void augmenterBlinds() {
		this.grosseBlind.augmenter(petiteBlindParDefaut);
		this.petiteBlind.augmenter(petiteBlindParDefaut / 2);
	}

	public void afficherToutesLesMains() {
		for (Joueur joueur : joueurs) {
			joueur.afficherMain();
		}
	}

	private void effacerTerminal() {
		for (int i = 0; i < 50; ++i) System.out.println();
	}
}
