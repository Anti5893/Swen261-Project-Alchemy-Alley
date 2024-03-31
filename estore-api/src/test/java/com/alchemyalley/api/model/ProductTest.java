package com.alchemyalley.api.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

/**
 * Tests the Product class.
 * @author Group 2
 */
@Tag("Model-tier")
public class ProductTest {

    @Test
    public void testConstructor() {
        // Setup
        int id = 99;
        String name = "Steam";
		ElementType type = ElementType.AIR;
		double price = 19.99;
		int quantity = 50;

        // Invoke
        Product product = new Product(id, name, type, price, quantity,"fake/url");

        // Analyze
        assertEquals(id, product.getId());
        assertEquals(name, product.getName());
	    assertEquals(type, product.getType());
	    assertEquals(price, product.getPrice());
	    assertEquals(quantity, product.getQuantity());
    }

	@Test
	public void testConstructorException() {
		// Setup
		int id = -1;
		String name = "Steam";
		ElementType type = ElementType.AIR;
		double price = 19.99;
		int quantity = 50;
		String imageURL = "fake/url";

		// Invoke & Analyze
		assertThrows(IllegalArgumentException.class,
				() -> new Product(id, name, type, price, quantity, imageURL),
				"IllegalStateException not thrown");
	}

    @Test
    public void testToString() {
        // Setup
	    int id = 99;
	    String name = "Steam";
	    ElementType type = ElementType.AIR;
	    double price = 19.99;
	    int quantity = 50;
		String URL = "fake/url";

	    Product product = new Product(id, name, type, price, quantity, URL);
        String expected = String.format(Product.STRING_FORMAT, id, name, type, price, quantity);

        // Invoke
        String actual = product.toString();

        // Analyze
        assertEquals(expected, actual);
    }

	@Test
	public void testEquals(){
		// Setup
	    int id = 99;
	    String name = "Steam";
	    ElementType type = ElementType.AIR;
	    double price = 19.99;
	    int quantity = 50;
		String imageURL = "url";

	    Product product = new Product(id, name, type, price, quantity, imageURL);
		Product product2 = new Product(id, name, type, price, quantity, imageURL);

		// Invoke
		boolean actual = product.equals(product2);

		// Analyze
		assertTrue(actual);
	}

	@Test
	public void testNotEqual(){
		// Setup
	    int id = 99;
	    String name = "Steam";
	    ElementType type = ElementType.AIR;
	    double price = 19.99;
	    int quantity = 50;
		String imageURL = "url";

	    Product product = new Product(id, name, type, price + 2, quantity, imageURL);
		Product product2 = new Product(id, name, type, price, quantity, imageURL);

		// Invoke
		boolean actual = product.equals(product2);

		// Analyze
		assertFalse(actual);
	}

	@Test
	public void testDecrementStock(){
		// Setup
	    int id = 99;
	    String name = "Steam";
	    ElementType type = ElementType.AIR;
	    double price = 19.99;
	    int quantity = 50;
		String imageURL = "url";

	    Product product = new Product(id, name, type, price, quantity, imageURL);

		// Invoke
		Product actual = product.decrementStock();

		// Analyze
		assertEquals(quantity - 1, actual.getQuantity());
	}

}