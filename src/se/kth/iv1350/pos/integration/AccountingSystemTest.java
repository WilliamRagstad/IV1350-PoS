package se.kth.iv1350.pos.integration;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import se.kth.iv1350.pos.model.Sale;

public class AccountingSystemTest {

	private AccountingSystem accountingSystem;
		
	@Before
	public void setUp() throws Exception {
		accountingSystem = new AccountingSystem();
	}

	@After
	public void tearDown() throws Exception {
		accountingSystem = null;
	}

	@Test
	public void testLog() {
		Sale exampleSale = new Sale();
		accountingSystem.log(exampleSale);
		assertTrue("Sale was not logged to Accounting System", accountingSystem.getSales().contains(exampleSale));		
	}

}
