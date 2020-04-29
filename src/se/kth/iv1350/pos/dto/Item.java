package se.kth.iv1350.pos.dto;

import se.kth.iv1350.pos.util.ItemID;

/**
 * An item that can exist in a store
 */
public class Item {
	private ItemID identifier;
	private String name;
	private String description;
	private float price;
	private float taxRate;
	
	public Item(ItemID identifier, String name, String description, float price, float taxRate) {
		this.identifier = identifier;
		this.name = name;
		this.description = description;
		this.price = price;
		this.taxRate = taxRate;
	}
	
	public ItemID getIdentifier() {
		return identifier;
	}
	public String getName() {
		return name;
	}
	public String getDescription() {
		return description;
	}
	public float getPrice() {
		return price;
	}
	public float getTaxRate() {
		return taxRate;
	}
}
