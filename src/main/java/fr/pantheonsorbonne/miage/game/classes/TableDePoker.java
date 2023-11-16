package fr.pantheonsorbonne.miage.game.classes;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

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
	private final int petiteBlindParDefaut = 0;
	private final int toursPourAugmentationBlind = 0;
	private List<Pot> pots = new ArrayList<>();
	private Scanner scanner = new Scanner(System.in);

	public TableDePoker() {
		this.joueurs = new ArrayList<>();
		this.joueursActifs = new ArrayList<>();
		this.deck = new Deck();
		this.croupier = new MainDuCroupier(deck);
		misesTotales = 0;
		this.miseMaximale = 0;
	}

	public TableDePoker(Joueur joueur) {
		this();
		this.ajouterJoueur(joueur);
	}

	public TableDePoker(List<Joueur> joueurs) {
		this.joueurs = new ArrayList<>(joueurs);
		this.deck = new Deck();
		this.croupier = new MainDuCroupier(deck);
		this.joueursActifs = new ArrayList<>(joueurs);
		this.joueursActifs.removeIf(joueur -> !joueur.estEnJeu());
	}

	public void jouerTour() {
		deck.initialiserDeck();
		this.distribuerCartes();
		this.initialiserBlinds();
		this.demanderPaiementBlinds();
		this.trouverMiseMaximale();
	
		for (int etape = 0; etape < 4; etape++) {
			switch (etape) {
				case 0:
					System.out.println("Les Cartes du croupier ne sont pas encore tirées ");
					break;
				case 1:
					System.out.println("Cartes du croupier après le flop : ");
					croupier.flop();
					break;
				case 2:
					System.out.println("Cartes du croupier après le turn : ");
					croupier.turn();
					break;
				case 3:
					System.out.println("Cartes du croupier après la river : ");
					croupier.river();
					break;
			}
	
			for (Joueur joueur : this.joueursActifs) {
				afficherInfosJoueur(joueur);
			}
	
			if (etape > 0) {
				croupier.afficherMain();
			}
	
			this.demanderMise();
		}
	
		this.réinitialiserTable();
	}
	

	public void demanderMise() {
		boolean toutLeMondeACallé = false;
		int relanceActuelle = 0;
	
		while (!toutLeMondeACallé) {
			toutLeMondeACallé = true;
	
			for (Joueur joueur : this.joueursActifs) {
				if (joueur.estEnJeu()) {
					afficherInfosJoueur(joueur);
	
					if (joueursActifs.size() > 1 && joueur.suit() && !joueur.estTapis()
							&& !joueur.estEnTrainDeRelancer()) {
						relanceActuelle = gererRelance(joueur, relanceActuelle);
	
						if (joueur.getMise() < this.miseMaximale) {
							toutLeMondeACallé = false;
						}
					}
				}
			}
	
			fairePotAllInSiNécessaire(joueursActifs.size());
	
			if (toutLeMondeACallé) {
				break;
			}
		}
	
		réinitialiserMisesJoueurs();
	}

	private void afficherInfosJoueur(Joueur joueur) {
		effacerTerminal();

		String message = String.format(
				"%s : Pour suivre, vous devez avoir misé %d, votre mise est actuellement de %d",
				joueur.getNom(), miseMaximale, joueur.getMise());

		System.out.println(message);

		List<String> cardNames = joueur.getCardNames();
		System.out.print("Vos cartes : ");
		for (String cardName : cardNames) {
			System.out.print(cardName + " ");
		}
		System.out.println();

	}

	private int gererRelance(Joueur joueur, int relanceActuelle) {
		System.out.print(joueur.getNom() + ", Choisissez une option : \n");
		System.out.println("1. Suivre");
		System.out.println("2. Se Coucher");
		System.out.println("3. Relancer");
	
		int choix = scanner.nextInt();
	
		switch (choix) {
			case 1:
				joueur.suivre(this.miseMaximale - joueur.getMise());
				joueur.setEstEnTrainDeRelancer(false);
				break;
			case 2:
				joueur.setEnJeu(false);
				joueur.setEstEnTrainDeRelancer(false);
				break;
			case 3:
				joueur.setEstEnTrainDeRelancer(true);
	
				while (true) {
					System.out.print("Voulez-vous relancer ? (Oui/Non) : ");
					String reponse = scanner.next().toLowerCase();
	
					if (reponse.equals("oui")) {
						System.out.print("Combien voulez-vous relancer ? : ");
						int x = scanner.nextInt();
						if (x > 0) {
							joueur.miser(this.miseMaximale - joueur.getMise() + x);
							relanceActuelle = x;
							break;
						} else {
							joueur.suivre(this.miseMaximale - joueur.getMise());
							joueur.setEstEnTrainDeRelancer(false);
							relanceActuelle = 0;
							break;
						}
					} else if (reponse.equals("non")) {
						joueur.suivre(this.miseMaximale - joueur.getMise());
						joueur.setEstEnTrainDeRelancer(false);
						relanceActuelle = 0;
						break;
					} else {
						System.out.println("Réponse invalide. Veuillez répondre par 'Oui' ou 'Non'.");
					}
				}
				break;
			default:
				System.out.println("Option invalide. Veuillez choisir à nouveau.");
				relanceActuelle = gererRelance(joueur, relanceActuelle);
		}
	
		afficherInfosJoueur(joueur);
		return relanceActuelle;
	}	


	private void trouverMiseMaximale() {
		for (Joueur joueur : this.joueursActifs) {
			System.out.println("Mise du joueur " + joueur.getNom() + ": " + joueur.getMise());
			if (joueur.getMise() > this.miseMaximale) {
				this.miseMaximale = joueur.getMise();
			}
		}
		System.out.println("Mise maximale mise à jour : " + this.miseMaximale);
	}
	
	public int getNombreJoueursActifs() {
		return this.joueursActifs.size();
	}

	public void initialiserBlinds() {
		int n = this.joueursActifs.size();
		if (n <= 1) {
			return;
		}
		if (this.grosseBlind == null) {
			this.grosseBlind = new Blind(this.petiteBlindParDefaut, this.joueursActifs.get(n - 1));
			this.petiteBlind = new Blind(this.petiteBlindParDefaut / 2, this.joueursActifs.get(n - 2));
			this.dealerBlind = new Blind(0, this.joueursActifs.get(Math.max(0, n - 3)));
		}
	}

	public void changerBlinds() {
		int n = this.joueursActifs.size();
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
		this.joueurs.removeIf(joueur -> joueur.estEnJeu() && joueur.getPileDeJetons() == 0);
		this.joueursActifs.removeIf(joueur -> joueur.estEnJeu() && joueur.getPileDeJetons() == 0);
	}

	public void ajouterJoueur(Joueur joueur) {
		this.joueurs.add(joueur);
		if (joueur.estEnJeu()) {
			this.joueursActifs.add(joueur);
		}
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
		if (joueurs.isEmpty()) {
			return null;
		}

		List<Joueur> joueursGagnants = new ArrayList<>();

		for (Joueur joueur : joueurs) {
			joueur.setCombinaisonGagnante(
					ConditionDeVictoire.trouverMeilleureCombinaison(croupier, joueur.getMainDuJoueur()));
		}

		Collections.sort(joueurs);

		int index = joueurs.size() - 1;
		Joueur gagnantCertain = joueurs.get(index);

		while (index > 0 && !gagnantCertain.suit()) {
			index--;
			gagnantCertain = joueurs.get(index);
		}

		joueursGagnants.add(gagnantCertain);

		for (Joueur joueur : joueurs) {
			if (joueur.suit() && !joueursGagnants.contains(joueur) && gagnantCertain.compareTo(joueur) == 0) {
				joueursGagnants.add(joueur);
			}
		}

		return joueursGagnants;
	}

	public void calculerPotTotal() {
		for (Joueur joueur : this.joueursActifs) {
			this.ajouterMise(joueur.getMise());
		}
	}

	public void ajouterMise(int mise) {
		this.misesTotales += mise;
	}

	public void terminerTour(List<Joueur> joueursGagnants) {
		this.calculerPotTotal();

		int gainTotal = this.misesTotales;
		int gainPartagé = joueursGagnants.size();
		int gainIndividuel = gainTotal / gainPartagé;

		for (Joueur joueur : joueursGagnants) {
			joueur.aGagné(gainIndividuel);
			System.out.println(
					joueur.getNom() + " a gagné " + gainIndividuel + " avec la main " + joueur.getCombinaison());
		}

		for (Joueur joueur : this.joueursActifs) {
			if (!joueursGagnants.contains(joueur)) {
				System.out.println(joueur.getNom() + " a perdu " + joueur.getMise() + " avec la main "
						+ joueur.getCombinaison());
				joueur.aPerdu();
			}

			joueur.setEnJeu(true);
		}

		enleverJoueurSansJeton();
		Deck.reinitialiserDeck();
		croupier.vider();
		this.changerBlinds();
		this.nombreDeTours++;
		this.misesTotales = 0;
		this.miseMaximale = 0;

		if (this.nombreDeTours % this.toursPourAugmentationBlind == 0) {
			augmenterBlinds();
		}

		viderPots();
	}

	public void réinitialiserMisesJoueurs() {
		for (Joueur joueur : this.joueursActifs) {
			joueur.setEstEnTrainDeRelancer(false);
		}
	}

	public void réinitialiserJoueurs() {
		for (Joueur joueur : this.joueursActifs) {
			joueur.setEstEnTrainDeRelancer(false);
			joueur.setEnJeu(true);
			joueur.setMise(0);
			joueur.setMainDuJoueur(null);
		}
	}

	public void demanderPaiementBlinds() {
		this.grosseBlind.getJoueur().miser(this.grosseBlind.getValeur());
		this.petiteBlind.getJoueur().miser(this.petiteBlind.getValeur());
		this.trouverMiseMaximale();
	}

	public void augmenterBlinds() {
		this.grosseBlind.augmenter(petiteBlindParDefaut);
		this.petiteBlind.augmenter(petiteBlindParDefaut / 2);
	}

	public boolean vérifierSiQuelquUnPeutEncoreMiser() {
		int nombreJoueursPouvantEncoreMiser = this.joueursActifs.size();
		boolean drapeau = false;
		int mise = this.joueursActifs.get(0).getMise();

		for (Joueur joueur : this.joueursActifs) {
			if (joueur.estTapis() || !joueur.suit()) {
				nombreJoueursPouvantEncoreMiser--;
			}

			if (joueur.getMise() != mise) {
				drapeau = true;
			}
		}

		return nombreJoueursPouvantEncoreMiser >= 2 || drapeau;
	}

	public void créerPot() {
		this.pots.add(new Pot());
	}

	public void ajouterAuPot(Joueur joueur, Pot pot) {
		pot.ajouterJoueur(joueur);
	}

	public void mettreÀJourValeurPot(Pot pot) {
		pot.setValeur(0);

		for (Joueur joueur : this.joueursActifs) {
			pot.ajouterMise(joueur.getMise());
		}
	}

	public void créerPotPourJoueurAllIn(Joueur joueur) {
		this.pots.add(new PotTapis(joueur.getMise(), joueur));
		PotTapis pot = (PotTapis) this.pots.get(this.pots.size() - 1);
		int miseJoueur = pot.getMiseAllIn();

		for (Joueur autreJoueur : this.joueursActifs) {
			if (autreJoueur.getMise() > 0) {
				pot.ajouterMise(Math.min(autreJoueur.getMise(), miseJoueur));
				pot.ajouterJoueur(autreJoueur);
			}
		}
	}

	public void mettreÀJourPotTapis(PotTapis pot) {
		pot.setValeur(0);

		for (Joueur joueur : this.joueursActifs) {
			pot.ajouterMise(Math.min(pot.getMiseAllIn(), joueur.getMise()));
		}
	}

	public void viderPots() {
		this.pots.clear();
	}

	public void mettreÀJourValeursTousLesPots() {
		for (Pot pot : this.pots) {
			if (pot instanceof PotTapis) {
				this.mettreÀJourPotTapis((PotTapis) pot);
			} else {
				this.mettreÀJourValeurPot(pot);
			}
		}
	}

	public int fairePotAllInSiNécessaire(int joueursActifs) {
		for (Joueur joueur : this.joueursActifs) {
			if (joueur.suit() && joueur.estTapis()) {
				boolean joueurDansPot = this.pots.stream().anyMatch(pot -> pot.getJoueurs().contains(joueur));

				if (!joueurDansPot) {
					this.créerPotPourJoueurAllIn(joueur);
					joueursActifs--;
				}
			}
		}
		return joueursActifs;
	}

	public void ajouterJoueursEnCompétitionAuPotDeBase() {
		for (Joueur joueur : this.joueursActifs) {
			if (joueur.suit() && joueur.getMise() >= this.pots.get(0).getMiseSeuil()) {
				this.pots.get(0).ajouterJoueur(joueur);
			}
		}
	}

	public void définirSeuilPourPotDeBase() {
		this.pots.get(0).setMiseSeuil(this.miseMaximale);
	}

	public void réinitialiserTable() {
		Deck.reinitialiserDeck();
		croupier.vider();
		this.nombreDeTours++;
		enleverJoueurSansJeton();
		if (this.nombreDeTours % this.toursPourAugmentationBlind == 0) {
			augmenterBlinds();
		}
		this.changerBlinds();
		this.misesTotales = 0;
		this.miseMaximale = 0;
		réinitialiserJoueurs();
		viderPots();
	}

	public void distribuerPot(Pot pot) {
		int valeur = pot.getValeur();
		if (valeur <= 0) {
			return;
		}
		List<Joueur> gagnants = determinerGagnant(pot.getJoueurs());
		if (gagnants == null) {
			return;
		}
		int gainPartagé = gagnants.size();
		for (Joueur joueur : gagnants) {
			joueur.aGagné(valeur / gainPartagé);
		}

		for (Pot unPot : this.pots) {
			unPot.setValeur(unPot.getValeur() - valeur);
		}
	}

	public void afficherToutesLesMains() {
		for (Joueur joueur : this.joueursActifs) {
			if (joueur.suit()) {
				System.out.println(joueur.getNom() + " a la main : " + joueur.getCombinaison());
				joueur.afficherMain();
			}
		}
	}

	public void payerJoueurs() {
		for (Joueur joueur : this.joueursActifs) {
			int miseJoueur = joueur.getMise();
			joueur.miser(-miseJoueur);
			this.ajouterMise(miseJoueur);
		}
	}

	public void gestionPot() {
		créerPot();
		fairePotAllInSiNécessaire(0);
		this.mettreÀJourValeursTousLesPots();
		this.trouverMiseMaximale();
		this.définirSeuilPourPotDeBase();
		this.ajouterJoueursEnCompétitionAuPotDeBase();
		this.pots.sort(null);
		for (Pot pot : this.pots) {
			distribuerPot(pot);
		}
	}

	private void effacerTerminal() {
		try {
			new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
