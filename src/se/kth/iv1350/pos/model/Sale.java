package se.kth.iv1350.pos.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import se.kth.iv1350.pos.dto.Discount;
import se.kth.iv1350.pos.dto.Item;
import se.kth.iv1350.pos.dto.Receipt;

/**
 * Holds information about a sale at the POS
 */
public class Sale {
	private Date date;
	private float total;
	private float taxRate;
	private HashMap<Item, Integer> items; // Dictionary<> was replaced with HashMap<>
	private ArrayList<Discount> discounts;
	private String storeName;

	public HashMap<Item, Integer> getItems() {
		return items;
	}
	
	public ArrayList<Discount> getDiscounts() {
		return discounts;
	}
	
	public float getTotal() {
		return total;
	}
	
	/**
	 * Creates a new sale
	 */
	public Sale() {
		this.date = new Date(); // This will automatically be the current date which makes setTimeOfSale() redundant
		this.total = 0;
		this.items = new HashMap<Item, Integer>();
		this.discounts = new ArrayList<Discount>();
		this.storeName = "Father PO's";
	}
	
	// setTimeOfSale() was removed
	
	
	/** Alternative Flow
	 *  3-4b. An item with the specified identifier has already been entered in the current sale.
	 *  	  1. Program increases the sold quantity of the item, and presents item description,
	 * 	  	  price, and running total.
	 */
	public void addItem(Item item, int quantity) {
		if (containsItem(item)) {
			int prevQuantity = items.get(item);
			items.replace(item, prevQuantity + quantity);
		}
		else
			items.put(item, quantity);
		calculateTotalAndTaxRate();
	}
	
	/**
	 * Checks if the sale already have registered an item.
	 * @param item to be found
	 * @return If the item was in the sale
	 */
	private Boolean containsItem(Item item) {
		for(Item i : items.keySet())
			if (i.getIdentifier().equals(item.getIdentifier()))
				return true;
		return false;
	}
	
	/**
	 * This function adds an discount to the sale and updates the total price.
	 * @param discount is the discount to be applied.
	 */
	public void applyDiscount(Discount discount) {
		discounts.add(discount);
		calculateTotalAndTaxRate();
	}
	
	/**
	 * This function calculates and updates the total property
	 */
	private void calculateTotalAndTaxRate() {
		total = 0; // reset
		taxRate = 1;
		
		// sum up items
		for(Item i : items.keySet()) {
			int quantity = items.get(i);
			total += i.getPrice() * quantity;
			taxRate *= 1 - i.getTaxRate();
		}
		
		// Calculate discounts
		for(Discount d : discounts) {
			total *= 1 - d.getValue();
		}
	}
	
	public Receipt getReceipt() {
		return new Receipt((int)(Math.random() * 10000), storeName, date, items, discounts, total, taxRate);
	}
}
