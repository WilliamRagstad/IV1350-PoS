package se.kth.iv1350.pos.view;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;
import java.util.stream.Collectors;

import se.kth.iv1350.pos.controller.Controller;
import se.kth.iv1350.pos.dto.Discount;
import se.kth.iv1350.pos.dto.Item;
import se.kth.iv1350.pos.startup.Main;
import se.kth.iv1350.pos.util.Customer;
import se.kth.iv1350.pos.util.CustomerID;
import se.kth.iv1350.pos.util.CustomerNotFoundException;
import se.kth.iv1350.pos.util.ItemID;
import se.kth.iv1350.pos.util.ItemOutOfStockException;


/**
 * This is a placeholder for the real view. It contains a hard-coded execution with calls to all
 * system operations in the controller.
 */
public class View {
	private Controller controller;

	public View(Controller controller) {
		this.controller = controller;
	}
	
	/**
	 * Simulates a inputs from a real PoS situation.
	 */
	public void sampleExecution() {
		// Initialize scanner for user input
		Scanner sc = new Scanner(System.in);
		
		// Peek at the store's customer database and pretend that we are the first customer
		HashMap<CustomerID, Customer> customers = Main.getCustomerDB().getCustomers();
		CustomerID customer = customers.keySet().iterator().next();
		
		System.out.println("Starting new sale!");
		controller.initializeSale();

		// Peek at the store's inventory and select items from there.
		HashMap<Item, Integer> inv = Main.getItemRegistry().getInventory();
		for(int i = 0; i < 5; i++) {
			Item item = (Item) inv.keySet().toArray()[(int)(Math.random()*inv.size())];
			int ammount = (int)(Math.random()*5) + 1;
			System.out.print("Adding " + ammount + "st " + item.getName() + " to sale: ");
			try {
				controller.addItem(item.getIdentifier(), ammount);
				System.out.println("Added!");
			}
			catch(ItemOutOfStockException e) {
				System.out.println("The item was out of stock.");
			}
		}
		
		// Print sale
		System.out.println("\n========= SALE =========");
		HashMap<Item, Integer> sale = controller.getSale().getItems();
		for(Item item : sale.keySet()) {
			System.out.println(item.getName() + ", " + item.getDescription() + ": " + item.getPrice() + "kr x" + sale.get(item).toString());
		}
		System.out.println("\nTotal: " + controller.getSale().getTotal() + "kr");
		System.out.println("===========================");

		System.out.print("Add discounts? : ");
		String discounts = sc.nextLine();
		if (discounts.toLowerCase().equals("yes")) {
			// We want to use a discount
			System.out.println("Looking for eligable discounts...");
			try {
				ArrayList<Discount> eligableDiscounts = controller.requestDiscount(customer);
				if (eligableDiscounts.size() > 0) {
					for(Discount d : eligableDiscounts)
					{
						System.out.println("  Added: " + d.getDescription() + ": " + (int)(d.getValue() * 100) + "%");
						controller.useDiscount(customer, d);
					}
				}
			}
			catch(CustomerNotFoundException e) {
				System.out.println(e.getMessage());
			}
		}
		
		
		System.out.println("Total: " + controller.getSale().getTotal() + "kr");
		System.out.print("Pay: ");
		int payment = Integer.parseUnsignedInt(sc.nextLine());
		
		// End sale
		if (payment > controller.getSale().getTotal()) {
			float change = controller.endSale(payment);
			System.out.println("Change: " + change + "kr.");
		}
		else {
			System.out.println("Not enought money provided");
		}
	}
}
