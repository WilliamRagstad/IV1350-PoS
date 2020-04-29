package se.kth.iv1350.pos.integration;

import java.util.ArrayList;
import java.util.HashMap;

import se.kth.iv1350.pos.dto.Discount;
import se.kth.iv1350.pos.util.Customer;
import se.kth.iv1350.pos.util.CustomerID;
import se.kth.iv1350.pos.util.CustomerNotFoundException;

public class CustomerDB {

	private HashMap<CustomerID, Customer> customers; // Dictionary<> was replaced with HashMap<>
	
	public HashMap<CustomerID, Customer> getCustomers() {
		return customers;
	}
	
	public CustomerDB() {
		customers = new HashMap<CustomerID, Customer>();
		fetchCustomers();
	}
	
	public CustomerDB(HashMap<CustomerID, Customer> customers) {
		this.customers = customers;
	}
	
	/**
	 * This simulates fetching all customers from the real database. This is just a fake method used when testing.
	 */
	private void fetchCustomers() {
		for(int i = 0; i < 10; i++) {
			Customer c = new Customer();
			customers.put(c.getIdentifier(), c);
		}
	}


	public ArrayList<Discount> getDiscounts(CustomerID customer) throws CustomerNotFoundException {
		for(CustomerID id : customers.keySet()) {
			if (id == customer) {
				return customers.get(customer).getEligableDiscounts();
			}
		}
		throw new CustomerNotFoundException("The customer could not be found in the database");
	}

	public void useDiscount(CustomerID customerID, Discount discount) {
		Customer customer = customers.get(customerID);
		ArrayList<Discount> eligableDiscounts = customer.getEligableDiscounts();
		for(Discount d : eligableDiscounts) {
			if (d.getIdentifier().equals(discount.getIdentifier())) {
				// Remove the discount d from discounts
				eligableDiscounts.remove(d);
				return;
			}
		}
		customer.setEligableDiscounts(eligableDiscounts); // Update discounts array
		customers.replace(customerID, customer);
	}

}
