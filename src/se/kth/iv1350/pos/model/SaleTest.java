package se.kth.iv1350.pos.model;

import static org.junit.Assert.*;

import java.util.Date;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import se.kth.iv1350.pos.dto.Discount;
import se.kth.iv1350.pos.dto.Item;
import se.kth.iv1350.pos.dto.Receipt;
import se.kth.iv1350.pos.util.ItemID;

public class SaleTest {
	
	private Sale sale;

	@Before
	public void setUp() throws Exception {
		sale = new Sale();
	}

	@After
	public void tearDown() throws Exception {
		sale = null;
	}

	@Test
	public void testAddItem_NotContains() {
		Item exampleItem = new Item(new ItemID(), "Test Item", "Testing", 1, 1);
		sale.addItem(exampleItem, 1);
		int expAmmountOfItems = 1;
		int ammountOfItems = sale.getItems().size();
		assertEquals("Item was not added to empty HashMap", ammountOfItems, expAmmountOfItems);
	}

	@Test
	public void testAddItem_Contains() {
		Item exampleItem = new Item(new ItemID(), "Test Item", "Testing", 1, 1);
		sale.addItem(exampleItem, 1);
		sale.addItem(exampleItem, 1);
		int expAmmountOfItems = 2;
		int ammountOfItems = sale.getItems().get(exampleItem);
		assertEquals("Item was not added to empty HashMap", ammountOfItems, expAmmountOfItems);
	}

	@Test
	public void testApplyDiscount() {
		Discount exampleDiscount = new Discount("Test Discount", 1, new Date());
		sale.applyDiscount(exampleDiscount);
		assertTrue("Discount was not added to sale discounts array", sale.getDiscounts().contains(exampleDiscount));
	}

	@Test
	public void testGetReceipt() {
		Receipt receipt = sale.getReceipt();
		assertNotNull("Receipt object was null", receipt);
	}

}
