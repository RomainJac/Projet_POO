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

	@Override
	public void run() {
		jouer();
	}

	public void jouer() {
		while (joueursActifs.size() > 1) {
			initialiserTour();
			for (int i = 1; i < 4; i++) {
				System.out.println("Mise Totale : " + misesTotales);
				croupier.tirerCarte(i);
				System.out.println("Croupier :");
				for (Card card : croupier.getMainDuCroupier()) {
					System.out.println(card.CardEnChaine(card));
				}
				for (Joueur joueur : joueursActifs) {
					int choixJoueur = joueur.faireChoix(croupier, miseMaximale, i);
					afficherInfosJoueur(joueur);
					gererChoix(joueur, choixJoueur, miseMaximale);
					int choixSuperpouvoir = joueur.faireChoixSuperPouvoir();
					gererSuperpouvoir(joueur, choixSuperpouvoir, deck, joueur);
				}
			}
			afficherToutesLesMains();
			determinerGagnant(joueurs);
			enleverJoueurSansJeton();
			for (Joueur joueur : joueursActifs) {
				System.out.println(joueur.getNom() + " a désormais : " + joueur.getPileDeJetons() + " jetons");
				System.out.println(joueur.getNom() + " a Gagné");

			}
			System.out.println("Fin de la partie");
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
				misesTotales += faireRelance(joueur, 50);
			}
		}

		return misesTotales;

	}

	public int faireRelance(Joueur joueur, int montant) {
		int nouvelleMise = miseMaximale + montant;

		if (joueur.getPileDeJetons() < nouvelleMise) {
			nouvelleMise = joueur.getPileDeJetons();
		}

		miseMaximale = nouvelleMise;
		joueur.miser(nouvelleMise);

		return nouvelleMise;
	}

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

		}
		misesTotales = 0;
		miseMaximale = 0;

	}

	public void afficherInfosJoueur(Joueur joueur) {

		List<String> cardNames = joueur.getCardNames();

		System.out.print("Cartes de " + joueur.getNom() + " : ");
		for (String cardName : cardNames) {
			System.out.print(cardName + " ");
		}
		System.out.println();

	}

	public void initialiserTour() {
		deck.initialiserDeck();
		distribuerCartes();
		changerBlinds();
		demanderPaiementBlinds();
		changerAtout();
	}

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
			default:
				System.out.println("Choix non valide.");
		}

	}

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

	public void enleverJoueurSansJeton() {
		joueursActifs.removeIf(joueur -> joueur.getPileDeJetons() == 0);
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
		if (!joueurs.isEmpty()) {
			joueursGagnants.add(joueurs.get(joueurs.size() - 1));

		}
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
		this.grosseBlind.augmenter(grosseBlindParDefaut);
		this.petiteBlind.augmenter(grosseBlindParDefaut / 2);
	}

	public void afficherToutesLesMains() {
		for (Joueur joueur : joueurs) {
			joueur.afficherMain();
		}
	}

	protected void changerAtout() {
		Joueur joueur = this.joueursActifs.get(0);
		int answer = (joueur).demandeCouleurInverse();
		this.setInvertedColor(answer);
	}

	protected void setInvertedColor(int couleur) {
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
