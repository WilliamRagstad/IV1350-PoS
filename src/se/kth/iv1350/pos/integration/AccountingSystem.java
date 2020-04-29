package se.kth.iv1350.pos.integration;

import java.util.ArrayList;

import se.kth.iv1350.pos.model.Sale;

public class AccountingSystem {
	private ArrayList<Sale> sales;
	
	public ArrayList<Sale> getSales() {
		return sales;
	}
	
	public AccountingSystem() {
		this.sales = new ArrayList<Sale>();
	}

	public void log(Sale sale) {
		sales.add(sale);
	}

}
