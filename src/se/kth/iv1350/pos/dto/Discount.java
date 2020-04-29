package se.kth.iv1350.pos.dto;

import java.util.Date;

import se.kth.iv1350.pos.util.DiscountID;

/**
 * A discount that can be applied in a sale
 */
public class Discount {
	private DiscountID identifier;
	private String description;
	private float value;
	private Date expiresDate;
	
	public DiscountID getIdentifier() {
		return identifier;
	}
	
	public float getValue() {
		return value;
	}
	
	public String getDescription() {
		return description;
	}
	
	public Discount(String description, float value, Date expiresDate) {
		this.identifier = new DiscountID();
		this.description = description;
		this.value = value;
		this.expiresDate = expiresDate;
	}
}
