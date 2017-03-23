package model;

import java.util.HashMap;
import java.util.Random;
import java.util.Scanner;

public class RoadNetwork {
	private HashMap<String, Vehicle> vehicleList = new HashMap<String, Vehicle>();
	private HashMap<String, RoadPiece> roadPieceList = new HashMap<String, RoadPiece>();
	private int currentTime = 0;
	private Scanner sc = new Scanner(System.in);

	/**
	 * Ajoute un véhicule au réseau.
	 * 
	 * @param vehicle
	 *            Véhicule à ajouter
	 * @return null s'il a bien été ajouté
	 */
	public Vehicle addVehicle(Vehicle vehicle) {
		return this.vehicleList.put(vehicle.getName(), vehicle);
	}

	/**
	 * Ajoute une pièce de route au réseau.
	 * 
	 * @param piece
	 *            Pièce à ajouter
	 * @return null si elle a bien été ajoutée
	 */
	public RoadPiece addRoadPiece(RoadPiece piece) {
		return this.roadPieceList.put(piece.getName(), piece);
	}

	/**
	 * Ajoute un capteur à une pièce du réseau.
	 * 
	 * @param sensor
	 *            Capteur à ajouter
	 * @param piece
	 *            Pièce sur laquelle l'ajouter
	 * @param direction
	 *            Direction du capteur
	 */
	public void addSensor(Sensor sensor, String piece, String direction) {
		// Recherche des pièces dans le réseau
		RoadPiece p = getRoadPiece(piece);
		RoadPiece d = getRoadPiece(direction);

		// Ajout du capteur
		p.addSensor(sensor, d);
	}

	/**
	 * Ajoute un sémaphore à une pièce du réseau.
	 * 
	 * @param semaphore
	 *            Sémaphore à ajouter
	 * @param piece
	 *            Pièce sur laquelle l'ajouter
	 * @param direction
	 *            Direction du capteur
	 */
	public void addSemaphore(Semaphore semaphore, String piece, String direction) {
		// Recherche des pièces dans le réseau
		RoadPiece p = getRoadPiece(piece);
		RoadPiece d = getRoadPiece(direction);

		// Ajout du capteur
		p.addSemaphore(semaphore, d);
	}

	/**
	 * Retourne le temps courrant.
	 * 
	 * @return Temps
	 */
	public int getCurrentTime() {
		return this.currentTime;
	}

	/**
	 * Change la valeur du temps courant.
	 * 
	 * @param currentTime
	 *            Temps
	 */
	public void setCurrentTime(int currentTime) {
		this.currentTime = currentTime;
	}

	/**
	 * Retourne un véhicule grâce à son nom.
	 * 
	 * @param name
	 *            Nom du véhicule
	 * @return Le véhicule
	 * @throws RoadPieceException
	 */
	private Vehicle getVehicle(String name) throws VehicleException {
		Vehicle vehicle = this.vehicleList.get(name);
		if (vehicle == null) {
			throw new VehicleException("Le véhicule " + name + " n'existe pas dans le réseau actuel.");
		}
		return vehicle;
	}

	/**
	 * Retourne une pièce grâce à son nom.
	 * 
	 * @param name
	 *            Nom de la pièce
	 * @return La pièce
	 * @throws RoadPieceException
	 */
	private RoadPiece getRoadPiece(String name) throws RoadPieceException {
		RoadPiece piece = this.roadPieceList.get(name);
		if (piece == null) {
			throw new RoadPieceException("La pièce " + name + " n'existe pas dans le réseau actuel.");
		}
		return piece;
	}

	/**
	 * Lie deux pièces ensemble en respectant certaines contraintes: on ne peux
	 * pas lier une pièce à elle même ni lier deux pièces de type identique.
	 * C'est la seule fonction à modifier si on ajoute de nouveaux types de
	 * pièces.
	 * 
	 * @param name1
	 *            Nom de la pièce n°1
	 * @param name2
	 *            Nom de la pièce n°2
	 * @throws RoadPieceException
	 */
	public void linkPieces(String name1, String name2) throws RoadPieceException {
		// Recherche des pièces dans le réseau
		if (name1 == name2) {
			throw new RoadPieceException("Impossible de lier " + name1 + " avec lui-même.");
		}
		RoadPiece p1 = this.getRoadPiece(name1);
		RoadPiece p2 = this.getRoadPiece(name2);

		// Contraintes liées aux sous-classes
		if (p1 instanceof Segment && p2 instanceof Segment || p1 instanceof Junction && p2 instanceof Junction) {
			throw new RoadPieceException("Impossible de lier " + name1 + " et " + name2
					+ " : ils sont tous les deux de type " + p1.getClass().getName() + ".");
		}

		// Ajout des liens
		p1.addEnd(p2);
		p2.addEnd(p1);
	}

	/**
	 * Supprime le lien entre deux pièces.
	 * 
	 * @param name1
	 *            Nom de la pièce n°1
	 * @param name2
	 *            Nom de la pièce n°2
	 * @throws RoadPieceException
	 */
	public void unlinkPieces(String name1, String name2) throws RoadPieceException {
		// Recherche des pièces dans le réseau
		RoadPiece p1 = this.getRoadPiece(name1);
		RoadPiece p2 = this.getRoadPiece(name2);

		// Suppression des liens
		p1.removeEnd(p2);
		p2.removeEnd(p1);
	}

	/**
	 * Ajoute un regulateur a une Junction
	 * 
	 * @param roadpiecename
	 *            La RoadPiece qui doit etre une Junction a laquelle on va
	 *            ajouter le regulator
	 * @param reg
	 *            Le regulator a ajouter
	 * @throws RoadPieceException
	 *             Erreur si la RoadPiece n'est pas une Junction
	 */
	public void addRegulator(String roadpiecename, Regulator reg) throws RoadPieceException {
		RoadPiece r = getRoadPiece(roadpiecename);
		if (!(r instanceof Junction))
			throw new RoadPieceException(
					"Erreur, vous tentez d'ajouter un régulateur a un élément qui n'est pas une Junction !");

		Junction j = (Junction) r;
		j.setRegulator(reg);
		reg.setJunction(j);
	}

	/**
	 * Lance la simulation du réseau jusqu'à ce que l'utilisateur demande
	 * l'arrêt. Les objets du réseau doivent être initialisés correctement.
	 * 
	 * @param rand
	 *            Variable d'aléatoire
	 * @return false si l'initialisation n'a pas été faite correctement
	 */
	public boolean run(Random rand) {
		// Vérification de la bonne initialisation des objets
		for (RoadPiece e : roadPieceList.values()) {
			if (!e.isInitialized())
				return false;
		}
		for (Vehicle e : vehicleList.values()) {
			if (!e.isInitialized())
				return false;
		}

		// Message de démarrage
		System.out.println("Bienvenue dans cette simulation routière.");
		System.out
				.println("Pour une visualisation plus simple, il est conseillé d'utiliser la carte fournise en pdf.\n");

		// Boucle principale
		String answer;
		int increment = 0;
		currentTime = 0;
		do {
			for (int i = 0; i < increment; i++) {
				// Réinitialisation des capteurs
				for (RoadPiece e : roadPieceList.values()) {
					e.preTimeOperations();
				}
				// Déplacement des véhicules
				for (Vehicle e : vehicleList.values()) {
					e.move(rand);
				}
				currentTime++;

				// Modification des sémaphores à l'aide des régulateurs
				for (RoadPiece e : roadPieceList.values()) {
					e.postTimeOperations();
				}

				for (RoadPiece e : roadPieceList.values()) {
					e.printAlertsMsg();
				}
			}

			// Affichage des résultats
			System.out.format("\n------------------------------" + " Temps: %4d " + "------------------------------\n",
					currentTime);
			for (Vehicle e : vehicleList.values()) {
				System.out.println(e.printState());
			}

			// Demande à l'utilisateur de continuer ou non le programme
			System.out.println("Combien d'unités de temps doivent s'écouler? (exit pour arrêter le programme)");
			answer = sc.nextLine();
			if (answer.equals("")) { // Touche entrée pour faire un seul pas
				increment = 1;
			} else if (answer.equals("exit")) { // exit pour quitter
				increment = 0;
			} else // Sinon, on récupère le nombre de pas à faire
			{
				try {
					increment = Integer.parseInt(answer);
					if (increment < 1) {
						increment = 1;
					}
				} catch (NumberFormatException e) {
					increment = 1;
				}
			}
		} while (increment >= 1);

		System.out.println("\n\nMerci d'avoir choisi cette simulation routière pour la réalisation de votre projet! "
				+ "\nA bientôt pour d'autres simulations toujours plus excitantes!");
		sc.close();
		return true;
	}

	/**
	 * Place un véhicule sur une position du réseau.
	 * 
	 * @param vehicle
	 *            Véhicule à déplacer
	 * @param piece
	 *            Pièce sur laquelle le déplacer
	 * @param position
	 *            Sa position sur cette pièce
	 * @param direction
	 *            Pièce vers laquelle le véhicule va
	 * @throws RoadPieceException
	 */
	public void setVehiclePosition(String vehicle, String piece, int position, String direction)
			throws RoadPieceException, VehicleException {
		// Recherche du véhicule et des pièces dans le réseau
		Vehicle v = getVehicle(vehicle);
		RoadPiece p = getRoadPiece(piece);
		RoadPiece d = getRoadPiece(direction);
		// Positionnement du véhicule
		v.setPosition(p, position, d);
	}

	/**
	 * Fonction principale du programme: utilisée pour le tester.
	 * 
	 * @param args
	 *            Paramètres du programme: non utilisés
	 */
	public static void main(String[] args) {
		// Instance du réseau routier
		RoadNetwork net = new RoadNetwork();

		// Variable d'aléatoire
		Random rand = new Random(12345);

		// Création et ajout des segments au réseau
		net.addRoadPiece(new Segment("Voie rapide Chômage", 60));
		net.addRoadPiece(new Segment("Avenue de la Baguette", 80));
		net.addRoadPiece(new Segment("Voie de la Nage", 60));
		net.addRoadPiece(new Segment("Tunnel du Manchot", 60));
		net.addRoadPiece(new Segment("Route du Vin", 50));
		net.addRoadPiece(new Segment("Route de la Fraude", 55));
		net.addRoadPiece(new Segment("Autoroute Sans Limites", 100));
		net.addRoadPiece(new Segment("Nationale N-WWIII", 70));
		net.addRoadPiece(new Segment("Voie des Blonds", 75));
		net.addRoadPiece(new Segment("Rue Détournée", 40));

		// Création et ajout des jonctions au réseau
		net.addRoadPiece(new DeadEndJunction("Impasse de la Sieste"));
		net.addRoadPiece(new CrossroadsJunction("Rond-point Massif", 4));
		net.addRoadPiece(new SimpleJunction("Passage Pluvieux"));
		net.addRoadPiece(new SimpleJunction("Passage du Dentiste"));
		net.addRoadPiece(new CrossroadsJunction("Intersection Ter-ter", 3));
		net.addRoadPiece(new SimpleJunction("Passage des Banques"));
		net.addRoadPiece(new CrossroadsJunction("Intersection de la Saucisse", 3));
		net.addRoadPiece(new SimpleJunction("Passage de la Frite"));
		net.addRoadPiece(new DeadEndJunction("Impasse du Squat"));

		// Liens entre les pièces de route
		net.linkPieces("Impasse de la Sieste", "Voie rapide Chômage");
		net.linkPieces("Voie rapide Chômage", "Rond-point Massif");
		net.linkPieces("Rond-point Massif", "Avenue de la Baguette");
		net.linkPieces("Avenue de la Baguette", "Passage Pluvieux");
		net.linkPieces("Passage Pluvieux", "Voie de la Nage");
		net.linkPieces("Voie de la Nage", "Passage du Dentiste");
		net.linkPieces("Passage du Dentiste", "Tunnel du Manchot");
		net.linkPieces("Tunnel du Manchot", "Intersection Ter-ter");
		net.linkPieces("Intersection Ter-ter", "Route du Vin");
		net.linkPieces("Route du Vin", "Rond-point Massif");
		net.linkPieces("Rond-point Massif", "Route de la Fraude");
		net.linkPieces("Route de la Fraude", "Passage des Banques");
		net.linkPieces("Passage des Banques", "Autoroute Sans Limites");
		net.linkPieces("Autoroute Sans Limites", "Intersection de la Saucisse");
		net.linkPieces("Intersection de la Saucisse", "Nationale N-WWIII");
		net.linkPieces("Nationale N-WWIII", "Impasse du Squat");
		net.linkPieces("Intersection de la Saucisse", "Voie des Blonds");
		net.linkPieces("Voie des Blonds", "Passage de la Frite");
		net.linkPieces("Passage de la Frite", "Rue Détournée");
		net.linkPieces("Rue Détournée", "Intersection Ter-ter");

		// Ajout des sémaphores
		net.addSemaphore(new BicolorTrafficLight(Color.RED), "Voie rapide Chômage", "Rond-point Massif");
		net.addSemaphore(new BicolorTrafficLight(Color.GREEN), "Avenue de la Baguette", "Rond-point Massif");
		net.addSemaphore(new BicolorTrafficLight(Color.RED), "Route du Vin", "Rond-point Massif");
		net.addSemaphore(new BicolorTrafficLight(Color.RED), "Route de la Fraude", "Rond-point Massif");
		net.addSemaphore(new TricolorTrafficLight(Color.GREEN), "Route de la Fraude", "Passage des Banques");
		net.addSemaphore(new TricolorTrafficLight(Color.GREEN), "Autoroute Sans Limites", "Passage des Banques");
		net.addSemaphore(new StopSign(), "Avenue de la Baguette", "Passage Pluvieux");
		net.addSemaphore(new StopSign(), "Voie de la Nage", "Passage du Dentiste");
		net.addSemaphore(new SpeedLimit(50), "Route du Vin", "Intersection Ter-ter");
		net.addSemaphore(new SpeedLimit(50), "Tunnel du Manchot", "Passage du Dentiste");

		// Ajout des capteurs
		net.addSensor(new PresenceSensor(50), "Voie rapide Chômage", "Rond-point Massif");
		net.addSensor(new PresenceSensor(70), "Avenue de la Baguette", "Rond-point Massif");
		net.addSensor(new PresenceSensor(40), "Route du Vin", "Rond-point Massif");
		net.addSensor(new PresenceSensor(45), "Route de la Fraude", "Rond-point Massif");

		// Ajout des régulateurs
		net.addRegulator("Rond-point Massif", new PresenceRegulator());
		net.addRegulator("Passage des Banques", new SpeedRegulator(30));

		// Création et position initiale des véhicules sur le réseau
		net.addVehicle(new Vehicle("307 break", 70));
		net.addVehicle(new Vehicle("Spitfire Mk XII", 140));
		net.addVehicle(new Vehicle("Eurotruck", 50));
		net.addVehicle(new Vehicle("T-14 Armata", 30));
		net.setVehiclePosition("307 break", "Intersection Ter-ter", 0, "Tunnel du Manchot");
		net.setVehiclePosition("Spitfire Mk XII", "Voie de la Nage", 50, "Passage du Dentiste");
		net.setVehiclePosition("Eurotruck", "Voie des Blonds", 10, "Intersection de la Saucisse");
		net.setVehiclePosition("T-14 Armata", "Nationale N-WWIII", 1, "Intersection de la Saucisse");

		// Test des capteurs de collision
		// net.setVehiclePosition("Spitfire Mk XII", "Route du Vin", 10,
		// "Rond-point Massif");
		// net.setVehiclePosition("Eurotruck", "Route du Vin", 20, "Rond-point "
		// + "Massif");

		net.run(rand);
	}
}