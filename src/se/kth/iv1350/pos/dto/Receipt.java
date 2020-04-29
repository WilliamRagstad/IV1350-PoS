package se.kth.iv1350.pos.dto;

import java.util.ArrayList;
import java.util.Date;
import java.util.Dictionary;
import java.util.HashMap;

public class Receipt {
	private int ID;
	private String store;
	private Date date;
	private HashMap<Item, Integer> items;
	private ArrayList<Discount> discounts;
	private float total;
	private float taxRate;

	public int getID() {
		return ID;
	}
	public String getStore() {
		return store;
	}
	public Date getDate() {
		return date;
	}
	public HashMap<Item, Integer> getItems() {
		return items;
	}
	public ArrayList<Discount> getDiscounts() {
		return discounts;
	}
	public float getTotal() {
		return total;
	}
	public float getTaxRate() {
		return taxRate;
	}
	
	public Receipt(int ID, String store, Date date, HashMap<Item, Integer> items, ArrayList<Discount> discounts, float total, float taxRate) {
		this.ID = ID;
		this.store = store;
		this.date = date;
		this.items = items;
		this.discounts = discounts;
		this.total = total;
		this.taxRate = taxRate;
	}
}
