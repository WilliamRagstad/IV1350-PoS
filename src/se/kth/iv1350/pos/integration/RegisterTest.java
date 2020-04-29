package se.kth.iv1350.pos.integration;

import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import se.kth.iv1350.pos.dto.Discount;
import se.kth.iv1350.pos.dto.Item;
import se.kth.iv1350.pos.dto.Receipt;

public class RegisterTest {
	
	private Register register;
	private ByteArrayOutputStream testOutputStream;

	@Before
	public void setUp() throws Exception {
		testOutputStream = new ByteArrayOutputStream();
		register = new Register(0, new PrintStream(testOutputStream));
	}

	@After
	public void tearDown() throws Exception {
		register = null;
	}

	@Test
	public void testRegisterPayment() {
		float examplePayment = 100;
		float expResult = register.getMoney() + examplePayment;
		register.registerPayment(examplePayment);
		float result = register.getMoney();
		assertEquals(expResult, result);
	}

	@Test
	public void testPrintReceipt() {
		Receipt receipt = new Receipt(123456789, "Test Store", new Date(), new HashMap<Item, Integer>(), new ArrayList<Discount>(), 10f, 0.7f);
		register.printReceipt(receipt);
		
		String result = "";
		try {
			result = testOutputStream.toString(java.nio.charset.StandardCharsets.UTF_8.name());
		} catch (UnsupportedEncodingException e) { /* The code will never jump here because we use the UTF8 charset name directly from the source. */ }

		assertTrue("Wrong id number.", result.contains(Integer.toString(receipt.getID())));
		assertTrue("Wrong store name.", result.contains(receipt.getStore()));
		assertTrue("Wrong date.", result.contains(receipt.getDate().toString()));
		assertTrue("Wrong tax rate.", result.contains(Float.toString((1 - receipt.getTaxRate()))));
		assertTrue("Wrong total.", result.contains(Float.toString(receipt.getTotal())));
		/**
		 * We don't have any items or discounts, therefore this is not included in the expected result.
		 */
	}

}
