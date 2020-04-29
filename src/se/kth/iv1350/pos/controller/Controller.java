package se.kth.iv1350.pos.controller;

import java.util.ArrayList;

import se.kth.iv1350.pos.dto.*;
import se.kth.iv1350.pos.integration.*;
import se.kth.iv1350.pos.model.*;
import se.kth.iv1350.pos.util.*;
/**
 * This is the application controller.
 * All calls to the model pass through this class.
 */
public class Controller {
	private AccountingSystem accountingSystem;
	private CustomerDB customerDB;
	private ItemRegistry itemRegistry;
	private Register register;
	
	private Sale sale;
	
	public Sale getSale() {
		return sale;
	}
	
	public Controller(AccountingSystem accountingSystem, CustomerDB customerDB, ItemRegistry itemRegistry, Register register) {
		this.accountingSystem = accountingSystem;
		this.customerDB = customerDB;
		this.itemRegistry = itemRegistry;
		this.register = register;
	}
	
	/**
	 * Starts a new sale, allowing other methods to be called
	 */
	public void initializeSale() {
		sale = new Sale();
	}
	
	/**
	 * Add an item to the current sale
	 * @param identifier specifies the item to be added
	 * @param quantity specifies how many of that item should be added
	 * @throws ItemOutOfStockException is thrown if the item id is not valid
	 */
	public void addItem(ItemID identifier, int quantity) throws ItemOutOfStockException
	{
		if (itemRegistry.inStock(identifier)) {
			Item item = itemRegistry.getItem(identifier);
			sale.addItem(item, quantity);
		}
		else
			throw new ItemOutOfStockException("The item with id: " + identifier.toString() + " could not be found in the inventory.");
	}

	/**
	 * Fetches an array of eligable discounts that a customer have.
	 * @param customer specifies which customer's discounts should be fetched
	 * @return an array of discounts
	 * @throws CustomerNotFoundException is thrown if the customer does not exist in the customer database.
	 */
	public ArrayList<Discount> requestDiscount(CustomerID customer) throws CustomerNotFoundException {
		return customerDB.getDiscounts(customer);
	}

	/**
	 * Consumes and applies a discount that a customer have
	 * @param customer the customer that uses the discount
	 * @param discount the discount in question
	 */
	public void useDiscount(CustomerID customer, Discount discount) {
		customerDB.useDiscount(customer, discount);
		sale.applyDiscount(discount);
	}

	/**
	 * Ends the current sale by calculating the change after payment and removes the sale object.
	 * @param payment is the amount that should be payed. This is shown in the View.
	 * @return change
	 */
	public float endSale(int payment) {
		float total = sale.getTotal();
		itemRegistry.update(sale.getItems());
		accountingSystem.log(sale);
		register.registerPayment(total);
		register.printReceipt(sale.getReceipt());
		sale = null;
		return payment - total;
	}
}
