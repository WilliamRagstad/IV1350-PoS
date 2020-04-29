package se.kth.iv1350.pos.startup;

import se.kth.iv1350.pos.integration.*;
import se.kth.iv1350.pos.controller.*;
import se.kth.iv1350.pos.view.*;

/**
 * Starts the entire application, contains the main mathod used to start the application.
 */
public class Main {
	private static AccountingSystem accountingSystem;
	private static CustomerDB customerDB;
	private static ItemRegistry itemRegistry;
	private static Register register;

	public static ItemRegistry getItemRegistry() {
		return itemRegistry;
	}
	
	public static CustomerDB getCustomerDB() {
		return customerDB;
	}
	
	private static Controller controller;
	private static View view;
	
	/**
	 * The main method used to start the entire application.
	 * @param args The application does not take any command line parameters.
	 */
	public static void main(String[] args) {
		// Create integration layer
		accountingSystem = new AccountingSystem();
		customerDB = new CustomerDB();
		itemRegistry = new ItemRegistry();
		register = new Register();
		
		// Create Controller and View
		controller = new Controller(accountingSystem, customerDB, itemRegistry, register);
		view = new View(controller);
		
		// Run sample execution that simulates a real PoS situation
		view.SampleExecution();
	}
	
}
