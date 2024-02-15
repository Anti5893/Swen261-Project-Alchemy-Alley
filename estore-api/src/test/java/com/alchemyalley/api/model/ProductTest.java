package com.alchemyalley.api.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

/**
 * Test the Product class.
 *
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

}