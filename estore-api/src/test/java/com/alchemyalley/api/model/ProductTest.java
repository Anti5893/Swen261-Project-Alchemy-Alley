package com.alchemyalley.api.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.beans.Transient;

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
        Product product = new Product(id, name, type, price, quantity);

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

		// Invoke & Analyze
		assertThrows(IllegalArgumentException.class,
				() -> new Product(id, name, type, price, quantity),
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

	    Product product = new Product(id, name, type, price, quantity);
        String expected = String.format(Product.STRING_FORMAT, id, name, type, price, quantity);

        // Invoke
        String actual = product.toString();

        // Analyze
        assertEquals(expected, actual);
    }
	@Test
	public void testEqualsEqual(){
		// Setup
	    int id = 99;
	    String name = "Steam";
	    ElementType type = ElementType.AIR;
	    double price = 19.99;
	    int quantity = 50;

	    Product product = new Product(id, name, type, price, quantity);
		Product product2 = new Product(id, name, type, price, quantity);

		// Invoke
		boolean actual = product.equals(product2);

		//Analyze
		assertEquals(true, actual);
	}
	@Test
	public void testEqualsNotEqual(){
		// Setup
	    int id = 99;
	    String name = "Steam";
	    ElementType type = ElementType.AIR;
	    double price = 19.99;
	    int quantity = 50;

	    Product product = new Product(id, name, type, price+2, quantity);
		Product product2 = new Product(id, name, type, price, quantity);

		// Invoke
		boolean actual = product.equals(product2);

		//Analyze
		assertEquals(false, actual);
	}
	@Test
	public void testDecrementStock(){
		// Setup
	    int id = 99;
	    String name = "Steam";
	    ElementType type = ElementType.AIR;
	    double price = 19.99;
	    int quantity = 50;

	    Product product = new Product(id, name, type, price, quantity);

		// Invoke
		Product actual = product.decrementStock();

		//Analyze
		assertEquals(quantity-1, actual.getQuantity());
	}
}