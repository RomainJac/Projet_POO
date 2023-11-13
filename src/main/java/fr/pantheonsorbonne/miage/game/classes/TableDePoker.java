package fr.pantheonsorbonne.miage.game.classes;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class TableDePoker {
	private List<Joueur> joueurs;
	public List<Joueur> joueursActifs;
	private MainDuCroupier croupier;
	private Deck Deck;
	private int misesTotales;
	private int miseMaximale;
	private int nombreDeTours;
	private Blind grosseBlind;
	private Blind petiteBlind;
	private Blind dealerBlind;
	private final int petiteBlindParDefaut = 20;
	private final int toursPourAugmentationBlind = 3;
	private Scanner scanner = new Scanner(System.in);

	private List<Pot> pots = new ArrayList<>();

	public TableDePoker() {
		this.joueurs = new ArrayList<>();
		this.joueursActifs = new ArrayList<>();
		this.Deck = new Deck();
		this.croupier = new MainDuCroupier(Deck);
		misesTotales = 0;
		this.miseMaximale = 0;
	}

	public TableDePoker(Joueur joueur) {
		this();
		this.joueurs.add(joueur);
		this.joueursActifs.add(joueur);
	}

	public TableDePoker(List<Joueur> joueurs) {
		this.joueurs = joueurs;
		this.Deck = new Deck();
		this.croupier = new MainDuCroupier(Deck);
		this.joueursActifs = new ArrayList<>();
		for (Joueur joueur : this.joueurs) {
			if (joueur.estEnJeu()) {
				this.joueursActifs.add(joueur);
			}
		}
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
		this.joueursActifs.forEach(joueur -> joueur.setMain(new MainDuJoueur(this.Deck.CardsAleatoires(2))));
	}

	public int demanderMises(int joueursActifs) {
		boolean tousOntSuivi = false;
		List<Boolean> joueursOntSuivi = new ArrayList<>();

		while (!tousOntSuivi) {
			joueursOntSuivi.clear();

			for (Joueur joueur : this.joueursActifs) {
				if (joueursActifs > 1 && joueur.suit() && !joueur.estTapis()
						&& !joueur.estEnTrainDeRelancer()) {
					System.out.println("Pour suivre vous devez payer " + this.miseMaximale);
					System.out.println(joueur.getNom() + " votre mise est actuellement de " + joueur.getMise());
					joueur.afficherMain();
					System.out.println("Appuyez sur S pour suivre, C pour vous coucher, R pour relancer");
					String reponse = scanner.nextLine();

					while (reponse != "S" && reponse != "C" && reponse != "R") {
						System.out.println("Il ne s'agit pas d'une commande valide ");
						reponse = scanner.nextLine();
					}

					switch (reponse) {
						case "S":
							joueur.suivre(this.miseMaximale - joueur.getMise());
							joueur.setEstEnTrainDeRelancer(false);
							break;
						case "C":
							joueur.seCoucher();
							joueur.setEstEnTrainDeRelancer(false);
							joueursActifs--;
							break;
						case "R":
							System.out.println("De combien voulez-vous relancer ? (négatif pour suivre !)");
							int x = scanner.nextInt();

							if (x > 0) {
								joueur.miser(miseMaximale - joueur.getMise() + x);

								if (joueur.estTapis()) {
									this.créerPotPourJoueurAllIn(joueur);
								}

								for (Joueur unJoueur : this.joueursActifs) {
									unJoueur.setEstEnTrainDeRelancer(false);
								}

								joueur.setEstEnTrainDeRelancer(true);
								joueursOntSuivi.add(false);
							} else {
								joueur.suivre(this.miseMaximale - joueur.getMise());
								joueur.setEstEnTrainDeRelancer(false);
								joueursOntSuivi.add(true);
							}
							break;
					}
				}

				if (joueur.estTapis()) {
					joueursActifs--;
				}

				this.trouvermiseMaximale();
			}

			tousOntSuivi = true;

			for (Boolean joueurOntSuivi : joueursOntSuivi) {
				if (!joueurOntSuivi) {
					tousOntSuivi = false;
				}
			}
		}

		this.réinitialiserMisesJoueurs();
		return joueursActifs;
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
		int gainDivise = joueursGagnants.size();
		this.calculerPotTotal();
		
		for (Joueur joueur : joueursGagnants) {
			joueur.aGagné(this.misesTotales / gainDivise);
			System.out.println(joueur.getNom() + " a gagné " + this.misesTotales / gainDivise + " avec la main "
					+ joueur.getCombinaison());
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
		this.trouvermiseMaximale();
	}
	
	public void trouvermiseMaximale() {
		for (Joueur joueur : this.joueursActifs) {
			if (joueur.getMise() > this.miseMaximale) {
				this.miseMaximale = joueur.getMise();
			}
		}
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

	public int demanderMisesAvecPots(int joueursActifs) {
		boolean toutLeMondeACallé = false;
		while (!toutLeMondeACallé) {
			for (Joueur joueur : this.joueursActifs) {
				if (joueursActifs > 1 && joueur.suit() && !joueur.estTapis() && !joueur.estEnTrainDeRelancer()) {
					System.out.println("Mise actuelle la plus élevée est de " + this.miseMaximale);
					System.out.println(joueur.getNom() + ", vous misez actuellement " + joueur.getMise());
					joueur.afficherMain();
					String réponse;
					do {
						System.out.println("Appuyez sur S pour suivre, C pour se coucher, R pour relancer");
						réponse = scanner.nextLine();
					} while (!réponse.equals("S") && !réponse.equals("C") && !réponse.equals("R"));
	
					switch (réponse) {
						case "S":
							joueur.suivre(this.miseMaximale - joueur.getMise());
							joueur.setEstEnTrainDeRelancer(false);
							break;
						case "C":
							joueur.seCoucher();
							joueur.setEstEnTrainDeRelancer(false);
							joueursActifs--;
							break;
						case "R":
							System.out.println("Combien voulez-vous relancer ? (négatif pour suivre !)");
							int x = scanner.nextInt();
							if (x > 0) {
								for (Joueur autreJoueur : this.joueursActifs) {
									autreJoueur.setEstEnTrainDeRelancer(false);
								}
								joueur.setEstEnTrainDeRelancer((true));
								joueur.miser(this.miseMaximale - joueur.getMise() + x);
							} else {
								joueur.suivre(this.miseMaximale - joueur.getMise());
								joueur.setEstEnTrainDeRelancer(false);
							}
							break;
					}
				}
				this.trouvermiseMaximale();
			}
			toutLeMondeACallé = true;
			for (Joueur joueur : this.joueursActifs) {
				if (joueur.getMise() != this.miseMaximale) {
					toutLeMondeACallé = false;
					break;
				}
			}
			fairePotAllInSiNécessaire(joueursActifs);
		}
		this.réinitialiserMisesJoueurs();
		return joueursActifs;
	}
	
	public int fairePotAllInSiNécessaire(int joueursActifs) {
		for (Joueur joueur : this.joueursActifs) {
			if (joueur.suit() && joueur.estTapis()) {
				joueursActifs--;
				if (this.vérifierSiQuelquUnPeutEncoreMiser())
					this.créerPotPourJoueurAllIn(joueur);
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
		réinitialiserJoueurs();
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
	
	public void deroulerDuJeu() {
		this.distribuerCartes();
		this.initialiserBlinds();
		this.demanderPaiementBlinds();
		this.trouvermiseMaximale();
		int joueursActifsCount = joueursActifs.size();
		joueursActifsCount = this.demanderMisesAvecPots(joueursActifsCount);
		croupier.flop();
		croupier.afficherMain();
		joueursActifsCount = this.demanderMisesAvecPots(joueursActifsCount);
		croupier.turn();
		croupier.afficherMain();
		joueursActifsCount = this.demanderMisesAvecPots(joueursActifsCount);
		croupier.river();
		croupier.afficherMain();
		this.demanderMisesAvecPots(joueursActifsCount);
	}
	
	public void gestionPot() {
		créerPot();
		fairePotAllInSiNécessaire(0);
		this.mettreÀJourValeursTousLesPots();
		this.trouvermiseMaximale();
		this.définirSeuilPourPotDeBase();
		this.ajouterJoueursEnCompétitionAuPotDeBase();
		this.pots.sort(null);
		for (int i = 0; i < this.pots.size(); i++) {
			distribuerPot(this.pots.get(i));
		}
	}
	
	public void commencerTourAvecPots() {
		deroulerDuJeu();
		gestionPot();
		this.afficherToutesLesMains();
		this.getcroupier().afficherMain();
		this.réinitialiserTable();
	}
	
	public MainDuCroupier getcroupier() {
		return this.croupier;
	}

	
	
	
}
