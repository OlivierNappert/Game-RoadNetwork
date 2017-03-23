package model;

import model.Junction;

public class SimpleJunction extends Junction {
	/**
	 * Constructeur d'une Jonction a ajouter
	 * 
	 * @param name
	 *            de la Jonction
	 */
	public SimpleJunction(String name) {
		super(name, 2);
	}
}