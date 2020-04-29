package se.kth.iv1350.pos.controller;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;

import se.kth.iv1350.pos.dto.Discount;
import se.kth.iv1350.pos.integration.AccountingSystem;
import se.kth.iv1350.pos.integration.CustomerDB;
import se.kth.iv1350.pos.integration.ItemRegistry;
import se.kth.iv1350.pos.integration.Register;
import se.kth.iv1350.pos.model.Sale;
import se.kth.iv1350.pos.util.Customer;
import se.kth.iv1350.pos.util.CustomerID;
import se.kth.iv1350.pos.util.CustomerNotFoundException;
import se.kth.iv1350.pos.util.ItemID;
import se.kth.iv1350.pos.util.ItemOutOfStockException;

public class ControllerTest {
	
	private CustomerDB customerDB;
	private Controller controller;
	private int randomTestID;

	@Before
	public void setUp() throws Exception {
		customerDB = new CustomerDB();
		controller = new Controller(new AccountingSystem(), customerDB, new  ItemRegistry(), new Register());
		randomTestID = 1234567890;
	}
	
	@After
	public void tearDown() throws Exception {
		customerDB = null;
		controller = null;
		randomTestID = 0;
	}

	@Test
	public void testInitializeSale() {
		controller.initializeSale();
		Sale after = controller.getSale();
		assertNotNull("Checks if a new sale object has been created", after);
	}

	@Test (expected=ItemOutOfStockException.class)
	public void testAddItem() throws ItemOutOfStockException {
		controller.addItem(new ItemID(randomTestID), 1);
	}

	@Test(expected=CustomerNotFoundException.class)
	public void testRequestDiscount_NotFound() throws CustomerNotFoundException {
		controller.requestDiscount(new CustomerID(randomTestID));
	}

	@Test
	public void testRequestDiscount_Found() {
		try {
			Customer customer = customerDB.getCustomers().values().iterator().next();
			ArrayList<Discount> discounts = controller.requestDiscount(customer.getIdentifier());
			assertNotNull("Null array", discounts);
		}
		catch (CustomerNotFoundException e) {
			fail("Existing customer should have been found!");
		}
	}
	
	/**
	 * useDiscount testas i CustomerDBTest och SaleTest
	 */

	@Test
	public void testEndSale() {
		controller.initializeSale(); // Start a sale to end it
		controller.endSale(0); // because we doesn't buy anything, we won't pay anything
		Sale after = controller.getSale();
		assertNull("Ended sale was not removed", after);
	}

}
