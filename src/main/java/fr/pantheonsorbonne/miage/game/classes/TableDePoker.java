package fr.pantheonsorbonne.miage.game.classes;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;


public class TableDePoker {
	protected List<Joueur> joueurs;
	protected List<Joueur> joueursEnJeu;
	protected MainDuCroupier donneur;
	protected Deck Deck;
	protected int misesTotales;
	protected int miseLaPlusHaute;
	protected int nombreDeTours;
	protected Blind PetiteBlind;
	protected Blind GrosseBlind;
	protected Blind Dealer;
	protected final int petiteBlindParDefaut = 5;
	protected final int toursPourAugmenterBlind = 5;
	protected Scanner scanner = new Scanner(System.in);

	protected List<Pot> pots = new ArrayList<>();

	public TableDePoker() {
		this.joueurs = new ArrayList<>();
		this.joueursEnJeu = new ArrayList<>();
		this.Deck = new Deck();
		this.donneur = new MainDuCroupier(Deck);
		misesTotales = 0;
		this.miseLaPlusHaute = 0;
	}

	public TableDePoker(Joueur joueur) {
		this();
		this.joueurs.add(joueur);
		this.joueursEnJeu.add(joueur);
	}

	public TableDePoker(List<Joueur> joueurs) {
		this.joueurs = joueurs;
		this.Deck = new Deck();
		this.donneur = new MainDuCroupier(Deck);
		this.joueursEnJeu = new ArrayList<>();
		for (Joueur joueur : this.joueurs) {
			if (joueur.estEnJeu()) {
				this.joueursEnJeu.add(joueur);
			}
		}
	}

	public int combienSontToujoursEnJeu() {
		return this.joueursEnJeu.size();
	}

	public void initialiserBlinds() {
		int n = this.joueursEnJeu.size();
		if (n <= 1) {
			return;
		}
		if (this.GrosseBlind == null) {
			this.GrosseBlind = new Blind(this.petiteBlindParDefaut, this.joueursEnJeu.get(n - 1));
			this.PetiteBlind = new Blind(this.petiteBlindParDefaut / 2, this.joueursEnJeu.get(n - 2));
			this.Dealer = new Blind(0, this.joueursEnJeu.get(Math.max(0, n - 3)));
		}
	}

	public void changerBlinds() {
		int n = this.joueursEnJeu.size();
		int indexGrosseBlind = 0, indexPetiteBlind = 0, indexDealer = 0;
		for (int i = 0; i < n; i++) {
			if (this.joueursEnJeu.get(i) == this.GrosseBlind.getJoueur()) {
				indexGrosseBlind = i;
			} else if (this.joueursEnJeu.get(i) == this.PetiteBlind.getJoueur()) {
				indexPetiteBlind = i;
			} else if (this.joueursEnJeu.get(i) == this.Dealer.getJoueur()) {
				indexDealer = i;
			}
		}
		indexGrosseBlind = (indexGrosseBlind + 1) % n;
		indexPetiteBlind = (indexPetiteBlind + 1) % n;
		indexDealer = (indexDealer + 1) % n;
		this.GrosseBlind.setJoueur(this.joueursEnJeu.get(indexGrosseBlind));
		this.PetiteBlind.setJoueur(this.joueursEnJeu.get(indexPetiteBlind));
		this.Dealer.setJoueur(this.joueursEnJeu.get(indexDealer));
	}

	public void éjecterJoueursFauchés() {
		for (Joueur joueur : this.joueurs) {
			if (joueur.estEnJeu() && joueur.getPileDeJetons() == 0) {
				joueur.setEnJeu(false);
				this.joueursEnJeu.remove(joueur);
			}
		}
	}

	public void ajouterJoueur(Joueur joueur) {
		this.joueurs.add(joueur);
		if (joueur.estEnJeu()) {
			this.joueursEnJeu.add(joueur);
		}
	}

	public void distribuerCartes() {
		for (Joueur joueur : this.joueursEnJeu) {
			joueur.setMain(new MainDuJoueur(this.Deck.CardsAleatoires(2)));
		}
	}

	public int demanderMises(int joueursEnJeuDansLeTour) {
		int nombreDeJoueursAyantCallé;
		do {
			nombreDeJoueursAyantCallé = 0;
			for (Joueur joueur : this.joueursEnJeu) {
				if (joueur.nAPasPassé() && !joueur.estTapis() && !joueur.estEnTrainDeRelancer()) {
					afficherInfosDeMise(joueur);
					String réponse = lireRéponse();
	
					switch (réponse) {
						case "S":
							joueur.suivre(this.miseLaPlusHaute - joueur.getMise());
							nombreDeJoueursAyantCallé++;
							break;
						case "P":
							joueur.seCoucher();
							break;
						case "R":
							int montantRelance = demanderMontantRelance();
							if (montantRelance > 0) {
								joueur.relancer(this.miseLaPlusHaute, montantRelance);
								this.réinitialiserRelancesJoueurs();
								joueur.setEstEnTrainDeRelancer(true);
							} else {
								joueur.suivre(this.miseLaPlusHaute);
							}
							break;
					}
				}
				if (joueur.estTapis()) {
					joueur.setEstEnTrainDeRelancer(false);
				}
			}
			this.trouverMiseLaPlusHaute();
		} while (nombreDeJoueursAyantCallé < joueursEnJeuDansLeTour);
		this.réinitialiserRelancesJoueurs();
		return this.miseLaPlusHaute;
	}
	
	private void afficherInfosDeMise(Joueur joueur) {
		System.out.println("Mise la plus élevée actuelle : " + this.miseLaPlusHaute);
		System.out.println(joueur.getNom() + ", vous misez actuellement " + joueur.getMise());
		joueur.afficherMain();
		System.out.println("Appuyez sur S pour suivre, P pour passer, R pour relancer");
	}

	private int demanderMontantRelance() {
		try (Scanner scanner = new Scanner(System.in)) {
			System.out.println("De combien voulez-vous relancer ? (négatif pour suivre !)");
			while (!scanner.hasNextInt()) {
				System.out.println("Veuillez entrer un nombre valide.");
				scanner.next();
			}
			return scanner.nextInt();
		}
	}

	private String lireRéponse() {
		try (Scanner scanner = new Scanner(System.in)) {
			String réponse = scanner.nextLine();
			while (!réponse.equals("S") && !réponse.equals("P") && !réponse.equals("R")) {
				System.out.println("Commande incorrecte ! Réessayez : ");
				réponse = scanner.nextLine();
			}
			return réponse;
		}
	}
	
	public void calculerTotalPot() {
		for (Joueur joueur : this.joueursEnJeu) {
			this.ajouterMise(joueur.getMise());
		}
	}

	public void ajouterMise(int mise) {
		this.misesTotales += mise;
	}

	public void distribuerArgentAuxGagnants(List<Joueur> joueursQuiOntGagné) {
		int partageGains = joueursQuiOntGagné.size();
		this.calculerTotalPot();
		for (Joueur joueur : joueursQuiOntGagné) {
			joueur.aGagné(this.misesTotales / partageGains);
			System.out.println(joueur.getNom() + " a gagné " + this.misesTotales / partageGains + " avec une combinaison de "
					+ joueur.getCombinaison());
		}
		for (Joueur joueur : this.joueursEnJeu) {
			if (!joueursQuiOntGagné.contains(joueur)) {
				System.out.println(joueur.getNom() + " a perdu " + joueur.getMise() + " avec une combinaison de "
						+ joueur.getCombinaison());
				joueur.aPerdu();
			}
			joueur.réinitialiserPasse();
		}
		this.éjecterJoueursFauchés();
		Deck.reinitialiserDeck();
		this.changerBlinds();
		this.nombreDeTours++;
		this.misesTotales = 0;
		this.miseLaPlusHaute = 0;
		if (this.nombreDeTours % this.toursPourAugmenterBlind == 0) {
			augmenterBlinds();
		}
		reinitialiserPot();
	}

	public void réinitialiserRelancesJoueurs() {
		for (Joueur joueur : this.joueursEnJeu) {
			joueur.setEstEnTrainDeRelancer(false);
		}
	}

	public void réinitialiserJoueurs() {
		for (Joueur joueur : this.joueursEnJeu) {
			joueur.setEstEnTrainDeRelancer(false);
			joueur.réinitialiserPasse();
			joueur.réinitialiserMise();
			joueur.setMain(null);
		}
	}

	public void demanderPaiementBlinds() {
		this.GrosseBlind.getJoueur().miser(this.GrosseBlind.getValeur());
		this.PetiteBlind.getJoueur().miser(this.PetiteBlind.getValeur());
		this.trouverMiseLaPlusHaute();
	}

	public void trouverMiseLaPlusHaute() {
		for (Joueur joueur : this.joueursEnJeu) {
			if (joueur.getMise() > this.miseLaPlusHaute) {
				this.miseLaPlusHaute = joueur.getMise();
			}
		}
	}

	public void augmenterBlinds() {
		this.GrosseBlind.augmenter(petiteBlindParDefaut);
		this.PetiteBlind.augmenter(petiteBlindParDefaut / 2);
	}

	public boolean vérifierSiCréerNouveauPotAllInEstNécessaire() {
		int nombreDeJoueursEncoreEnJeup = this.joueursEnJeu.size();
		boolean drapeau = false;
		int mise = this.joueursEnJeu.get(0).getMise();
		for (Joueur joueur : this.joueursEnJeu) {
			if (joueur.estTapis() || !joueur.nAPasPassé()) {
				nombreDeJoueursEncoreEnJeup--;
			}
			if (joueur.getMise() != mise) {
				drapeau = true;
			}
		}
		return nombreDeJoueursEncoreEnJeup >= 2 || drapeau;
	}

	public void créerPot() {
		this.pots.add(new Pot());
	}

	public void ajouterAuPot(Joueur joueur, Pot pot) {
		pot.ajouterJoueur(joueur);
	}
}