package se.kth.iv1350.pos.util;

/**
 * Hold data about a customer's identifier.
 * In this case, it's formatted as an integer.
 */
public class CustomerID {
	private int identifier;

	public CustomerID() {
		this((int)(Math.random() * 10000));
	}
	
	public CustomerID(int identifier) {
		this.identifier = identifier;
	}
	
	public int getIdentifier() {
		return identifier;
	}
}
