package se.kth.iv1350.pos.integration;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import se.kth.iv1350.pos.dto.Discount;
import se.kth.iv1350.pos.util.Customer;
import se.kth.iv1350.pos.util.CustomerID;
import se.kth.iv1350.pos.util.CustomerNotFoundException;

public class CustomerDBTest {
	
	private ArrayList<Discount> discounts;
	private CustomerID customerID;
	private CustomerDB customerDB;
	private int randomTestID;

	@Before
	public void setUp() throws Exception {
		randomTestID = 1234567890;
		discounts = new ArrayList<Discount>();
		discounts.add(new Discount("Test discount", 1, new Date()));
		customerID = new CustomerID(randomTestID);
		HashMap<CustomerID, Customer> customersInDatabase = new HashMap<CustomerID, Customer>();
		customersInDatabase.put(customerID, new Customer(customerID, "Test Customer", discounts));
		customerDB = new CustomerDB(customersInDatabase);
	}

	@After
	public void tearDown() throws Exception {
		customerDB = null;
		randomTestID = 0;
	}

	@Test(expected=CustomerNotFoundException.class)
	public void testGetDiscounts_NotFound() throws CustomerNotFoundException {
		CustomerID randomNewCustomer = new CustomerID();
		customerDB.getDiscounts(randomNewCustomer);
	}
	
	@Test
	public void testGetDiscounts_Found() throws CustomerNotFoundException {
		ArrayList<Discount> eligableDiscounts = customerDB.getDiscounts(customerID);
		assertNotNull("Non-empty discount array list was null", eligableDiscounts);
	}

	@Test
	public void testUseDiscount() {
		customerDB.useDiscount(customerID, discounts.get(0));
		int expDiscountsLeft = 0;
		
		try {
			int discountsLeft = customerDB.getDiscounts(customerID).size();
			assertEquals("Discount found after removing last discount from array", discountsLeft, expDiscountsLeft);
		}  
		catch (CustomerNotFoundException e) {
			fail("Existing customer should have been found!");
		}
	}

}
