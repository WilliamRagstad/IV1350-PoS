package se.kth.iv1350.pos.util;

import java.util.ArrayList;
import java.util.Date;

import se.kth.iv1350.pos.dto.Discount;

/**
 * This class is hidden from our original system and is only used when
 * storing the customer data in the customer database.
 * The data is accessed using the CustomerID.
 */
public class Customer {
	private CustomerID identifier;
	private String name;
	private ArrayList<Discount> eligableDiscounts;
	
	/**
	 * In a real system, this class should hold more data.
	 */
	
	public ArrayList<Discount> getEligableDiscounts() {
		return eligableDiscounts;
	}
	public void setEligableDiscounts(ArrayList<Discount> eligableDiscounts) {
		this.eligableDiscounts = eligableDiscounts;
	}
	public CustomerID getIdentifier() {
		return identifier;
	}

	// This is just for testing purposes
	private static String[] names = { "Bob", "Jessie", "William", "Foo", "Karen", "Jacob", "Ken", "Dolora" };
	public Customer() {
		this(names[(int)(Math.random() * names.length)]);
	}
	
	public Customer(String name) {
		this(new CustomerID(), name, new ArrayList<Discount>() {
			{
				add(new Discount("loyal customer", 0.5f, new Date()));
				add(new Discount("Week Discount", 0.3f, new Date()));
			}
		});
	}
	
	public Customer(CustomerID identifier, String name, ArrayList<Discount> eligableDiscounts) {
		this.identifier = identifier;
		this.name = name;
		this.eligableDiscounts = eligableDiscounts;
	}
}
