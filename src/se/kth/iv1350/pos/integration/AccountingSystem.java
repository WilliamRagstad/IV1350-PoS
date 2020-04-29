package se.kth.iv1350.pos.integration;

import java.util.ArrayList;

import se.kth.iv1350.pos.model.Sale;

/**
 * Takes care of the accounting system. This is just a simulation of a real accounting system.
 */
public class AccountingSystem {
	private ArrayList<Sale> sales;
	
	public ArrayList<Sale> getSales() {
		return sales;
	}
	
	public AccountingSystem() {
		this.sales = new ArrayList<Sale>();
	}

	/**
	 * Logs to the accounting system
	 * @param sale to be logged
	 */
	public void log(Sale sale) {
		sales.add(sale);
	}

}
