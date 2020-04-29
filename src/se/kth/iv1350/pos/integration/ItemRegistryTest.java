package se.kth.iv1350.pos.integration;

import static org.junit.Assert.*;

import java.util.HashMap;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;

import se.kth.iv1350.pos.dto.Item;
import se.kth.iv1350.pos.util.ItemID;
import se.kth.iv1350.pos.util.ItemOutOfStockException;

public class ItemRegistryTest {
	
	private ItemRegistry itemRegistry;

	@Before
	public void setUp() throws Exception {
		HashMap<Item, Integer> emptyInventory = new HashMap<Item, Integer>();
		itemRegistry = new ItemRegistry(emptyInventory);
	}

	@After
	public void tearDown() throws Exception {
		itemRegistry = null;
	}

	@Test
	public void testInStock() {
		Item exampleItem = new Item(new ItemID(), "Test Item", "Testing", 1, 1);
		itemRegistry.addItem(exampleItem, 1);
		assertTrue("Item in inventory was said not to be in stock", itemRegistry.inStock(exampleItem.getIdentifier()));
	}

	@Test(expected=ItemOutOfStockException.class)
	public void testGetItem_NotFound() throws ItemOutOfStockException {
		itemRegistry.getItem(new ItemID());
	}

	@Test
	public void testGetItem_Found() throws ItemOutOfStockException {
		Item exampleItem = new Item(new ItemID(), "Test Item", "Testing", 1, 1);
		itemRegistry.addItem(exampleItem, 1);
		Item result = itemRegistry.getItem(exampleItem.getIdentifier());
		assertEquals("Item added to inventory could not be found later", exampleItem, result);
	}

	@Test
	public void testUpdate() {
		Item exampleItem = new Item(new ItemID(), "Test Item", "Testing", 1, 1);
		itemRegistry.addItem(exampleItem, 1);
		HashMap<Item, Integer> exampleSoldItems = new HashMap<Item, Integer>();
		exampleSoldItems.put(exampleItem, 1);
		itemRegistry.update(exampleSoldItems);
		assertTrue("Added and sold/remove item was not successful", itemRegistry.getInventory().size() == 0);
	}

}
