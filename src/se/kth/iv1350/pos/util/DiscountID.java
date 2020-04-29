package se.kth.iv1350.pos.util;

public class DiscountID {
	private int identifier;

	public DiscountID() {
		this((int)(Math.random() * 10000));
	}
	
	public DiscountID(int identifier) {
		this.identifier = identifier;
	}
	
	public int getIdentifier() {
		return identifier;
	}
}
