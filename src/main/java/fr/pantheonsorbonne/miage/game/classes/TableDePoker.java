package fr.pantheonsorbonne.miage.game.classes;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

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
	public int petiteBlindParDefaut = 2;

	public TableDePoker(Joueur... joueurs) {
		this.joueurs = new CopyOnWriteArrayList<>(joueurs);
		this.deck = new Deck();
		this.croupier = new MainDuCroupier(deck);
		this.joueursActifs = new ArrayList<>(List.of(joueurs));
		this.misesTotales = 0;
		this.miseMaximale = 0;
	}

	@Override
	public void run() {
		jouer();
	}

	public void jouer() {
		while (joueursActifs.size() > 1) {
			initialiserTour();
			for (int i = 1; i < 4; i++) {
				System.out.println(misesTotales);
				croupier.tirerCarte(i);
				System.out.println("Croupier :");
				for (Card card : croupier.getMainDuCroupier()) {
					System.out.println(card.getCardName());
				}
				for (Joueur joueur : joueursActifs) {
					int choixJoueur = joueur.faireChoix(croupier, miseMaximale, i);
					afficherInfosJoueur(joueur);
					gererChoix(joueur, choixJoueur, miseMaximale);
					System.out.println("Contenu du pot : " + misesTotales);
				}
			}
			System.out.println("Fin du tour");
			afficherToutesLesMains();
			determinerGagnant(joueurs);
			réinitialiserTable();
		}
	}

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
				misesTotales += faireRelance(joueur, 40);
				System.out.println(joueur.getNom() + " a relancé de 40");
			}
		}

		return misesTotales;

	}

	private int faireRelance(Joueur joueur, int montant) {
		joueur.miser(miseMaximale + montant);
		if (joueur.getPileDeJetons() < miseMaximale + montant) {
			return miseMaximale += joueur.getPileDeJetons();
		}
		return miseMaximale += montant;

	}

	public void distribuerGains(List<Joueur> joueursGagnants) {
		if (joueursGagnants != null) {
			for (Joueur joueur : joueursGagnants) {
				if (joueur.estTapis) {
					System.out.println(joueur.getNom() + ", avec tapis a gagné,  " + joueur.getMise() * 2);
					if (misesTotales > joueur.getMise() * 2) {
						joueur.aGagné(joueur.getMise() * 2);
						misesTotales -= joueur.getMise() * 2;
						joueursGagnants.remove(joueur);
						break;
					}
					joueur.aGagné(misesTotales / joueursGagnants.size());
					break;
				} else {
					System.out.println(joueur.getNom() + " a gagné " + misesTotales / joueursGagnants.size());
					joueur.aGagné(misesTotales / joueursGagnants.size());
				}
			}

			for (Joueur joueur : joueurs) {
				if (!joueursGagnants.contains(joueur)) {
					System.out.println(joueur.getNom() + " a perdu : " + joueur.getMise());
				}
			}
		}

	}

	public void réinitialiserTable() {
		réinitialiserJoueurs();
		if (!joueursActifs.isEmpty()) {
			Deck.reinitialiserDeck();
			croupier.vider();
			nombreDeTours++;
			if (nombreDeTours % 5 == 0) {
				augmenterBlinds();
			}
			misesTotales = 0;
			miseMaximale = 0;
		}

	}

	private void afficherInfosJoueur(Joueur joueur) {

		List<String> cardNames = joueur.getCardNames();

		System.out.print("Cartes de " + joueur.getNom() + " :");
		for (String cardName : cardNames) {
			System.out.print(cardName + " ");
		}
		System.out.println();

	}

	private void initialiserTour() {
		deck.initialiserDeck();
		distribuerCartes();
		changerBlinds();
		demanderPaiementBlinds();

	}

	public void changerBlinds() {
		int n = this.joueursActifs.size();
		if (this.grosseBlind == null) {
			this.grosseBlind = new Blind(this.petiteBlindParDefaut, this.joueurs.get(n - 1));
			this.petiteBlind = new Blind(this.petiteBlindParDefaut / 2, this.joueurs.get(n - 2));
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

	public void enleverJoueurSansJeton() {
		this.joueursActifs.removeIf(joueur -> joueur.getPileDeJetons() == 0);
	}

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
		joueursGagnants.add(joueurs.get(joueurs.size() - 1));

		for (Joueur joueur : joueurs) {
			if (joueur.getCombinaison().equals(joueursGagnants.get(0).getCombinaison())
					&& !joueur.getNom().equals(joueursGagnants.get(0).getNom())) {
				joueursGagnants.add(joueur);
			}
		}

		distribuerGains(joueursGagnants);

		return joueursGagnants;
	}

	public List<Joueur> réinitialiserJoueurs() {
		for (Joueur joueur : joueurs) {
			joueur.setMise(0);
			joueur.setMainDuJoueur(null);
			this.joueursActifs = joueurs;
			enleverJoueurSansJeton();
		}
		return joueursActifs;

	}

	public int demanderPaiementBlinds() {
		this.grosseBlind.getJoueur()
				.setPileDeJetons(this.grosseBlind.getJoueur().getPileDeJetons() - this.grosseBlind.getValeur());
		this.grosseBlind.getJoueur().setMise(this.grosseBlind.getValeur());
		this.petiteBlind.getJoueur()
				.setPileDeJetons(this.petiteBlind.getJoueur().getPileDeJetons() - this.petiteBlind.getValeur());
		this.petiteBlind.getJoueur().setMise(this.petiteBlind.getValeur());
		return misesTotales = misesTotales + grosseBlind.getValeur() + petiteBlind.getValeur();
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

	public int getMisesTotales() {
		return misesTotales;
	}

	public List<Joueur> getJoueursActifs() {
		return joueursActifs;
	}

}
