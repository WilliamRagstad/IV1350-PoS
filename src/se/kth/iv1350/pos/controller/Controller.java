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
	
	public void initializeSale() {
		sale = new Sale();
	}
	
	public void addItem(ItemID identifier, int quantity) throws ItemOutOfStockException
	{
		if (itemRegistry.inStock(identifier)) {
			Item item = itemRegistry.getItem(identifier);
			sale.addItem(item, quantity);
		}
		else
			throw new ItemOutOfStockException("The item with id: " + identifier.toString() + " could not be found in the inventory.");
	}

	public ArrayList<Discount> requestDiscount(CustomerID customer) throws CustomerNotFoundException {
		return customerDB.getDiscounts(customer);
	}

	public void useDiscount(CustomerID customer, Discount discount) {
		customerDB.useDiscount(customer, discount);
		sale.applyDiscount(discount);
	}

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
