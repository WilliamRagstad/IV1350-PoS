package se.kth.iv1350.pos.integration;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;

import se.kth.iv1350.pos.dto.Item;
import se.kth.iv1350.pos.util.ItemID;
import se.kth.iv1350.pos.util.ItemOutOfStockException;

/**
 * Takes care of the inventory. This is just a simulation of a real item registry.
 */
public class ItemRegistry {

	private HashMap<Item, Integer> inventory; // Dictionary<> was replaced with HashMap<>

	public HashMap<Item, Integer> getInventory() {
		return inventory;
	}
	
	public ItemRegistry(HashMap<Item, Integer> inventory) {
		this.inventory = inventory;
	}

	public ItemRegistry() {
		this.inventory = new HashMap<Item, Integer>();
		reStock();
	}

	/**
	 * Checks if an item is in stock, or is valid
	 * @param identifier to be checked
	 * @return if it's valid
	 */
	public Boolean inStock(ItemID identifier) {
		for(Item i : inventory.keySet())
			if (i.getIdentifier().equals(identifier))
				return true;
		return false;
	}

	/**
	 * Finds an item in the inventory
	 * @param identifier of the item to be found
	 * @return the item
	 * @throws ItemOutOfStockException is thrown if the item does not exist
	 */
	public Item getItem(ItemID identifier) throws ItemOutOfStockException {
		for(Item item : inventory.keySet()) {
			if (item.getIdentifier().equals(identifier)) return item;
		}
		throw new ItemOutOfStockException("The item with id: " + identifier.toString() + " could not be found in the inventory.");
	}

	/**
	 * This simulates stocking the inventory with new items. This is just a fake method used when testing.
	 */
	private void reStock() {
		addItem("Kexchoklad", "Cloetta Kexchoklad", 5, 0.1f, 200);
		addItem("Kaffe", "Löfbergs", 25, 0.4f, 300);
		addItem("Mjölk", "Arlas", 13, 0.35f, 120);
		addItem("Gräddfil", "Arlas", 13, 0.35f, 120);
		addItem("Juice", "Apelsin, äpple", 13, 0.5f, 250);
		addItem("Glass", "GB Glass", 25, 0.5f, 170);
		addItem("Läsk", "Pepsi, Zingo", 17.5f, 0.3f, 295);
		addItem("Fläskkarré", "Benfri", 30f, 0.2f, 100);
		addItem("Gul lök", "", 12f, 0.3f, 100);
		addItem("Potatis", "Färsk", 6f, 0.25f, 500);
	}
	
	/**
	 * Used to add items to the inventory
	 * @param name of the item
	 * @param description -||-
	 * @param price -||-
	 * @param taxRate -||-
	 * @param quantity -||-
	 */
	public void addItem(String name, String description, float price, float taxRate, int quantity) {
		inventory.put(new Item(new ItemID(), name, description, price, taxRate), quantity);
	}
	
	/**
	 * Same as above
	 */
	public void addItem(Item item, int quantity) {
		inventory.put(item, quantity);
	}
	
	/**
	 * Removes the sold items from the inventory so that the system knows what's in store
	 * @param soldItems to be removed from the system
	 */
	public void update(HashMap<Item, Integer> soldItems) {
		 for (Entry<Item, Integer> soldItem : soldItems.entrySet()) {
			 
			 // Remove from the inventory
			 for (Entry<Item, Integer> inventoryItem : inventory.entrySet()) {
				 if (inventoryItem.getKey().getIdentifier().equals(soldItem.getKey().getIdentifier())) {
					 
						if (inventoryItem.getValue() > soldItem.getValue()) { // Compare quantities
							int newQuantity = inventoryItem.getValue() - soldItem.getValue();
							inventoryItem.setValue(newQuantity);
						}
						else {
							// They were equal, the item is now sold out
							inventory.remove(inventoryItem.getKey());
						}
					 
				 }
			 }
		 }
	}
}
