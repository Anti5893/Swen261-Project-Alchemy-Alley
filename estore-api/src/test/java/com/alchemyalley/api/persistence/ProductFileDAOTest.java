package com.alchemyalley.api.persistence;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.File;
import java.io.IOException;

import com.alchemyalley.api.model.ElementType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.alchemyalley.api.model.Product;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

/**
 * Tests the ProductFileDAO class.
 * @author Group 2
 */
@Tag("Persistence-tier")
public class ProductFileDAOTest {

	private ProductFileDAO productFileDAO;
	private Product[] testProducts;
	private ObjectMapper mockObjectMapper;

	/**
	 * Before each test, we will create and inject a Mock Object Mapper to
	 * isolate the tests from the underlying file
	 * @throws IOException  If there is an error creating the mock DAO
	 */
	@BeforeEach
	public void setupProductFileDAO() throws IOException {
		mockObjectMapper = mock(ObjectMapper.class);
		testProducts = new Product[3];
		testProducts[0] = new Product(1, "Steam", ElementType.AIR, 10.99, 20);
		testProducts[1] = new Product(2, "Flint", ElementType.EARTH, 12.99, 15);
		testProducts[2] = new Product(3, "Ice", ElementType.WATER, 8.99, 35);

		when(mockObjectMapper
				.readValue(new File("doesnt_matter.txt"), Product[].class))
				.thenReturn(testProducts);
		productFileDAO = new ProductFileDAO("doesnt_matter.txt", mockObjectMapper);
	}

	@Test
	public void testGetProducts() {
		// Invoke
		Product[] products = productFileDAO.getProducts();

		// Analyze
		assertEquals(products.length, testProducts.length);
		for(int i = 0; i < testProducts.length; ++i)
			assertEquals(products[i], testProducts[i]);
	}

	@Test
	public void testFindProducts() {
		// Invoke
		Product[] products = productFileDAO.findProducts("i");

		// Analyze
		assertEquals(2, products.length);
		assertEquals(products[0], testProducts[1]);
		assertEquals(products[1], testProducts[2]);
	}

	@Test
	public void testGetProduct() {
		// Invoke
		Product product = productFileDAO.getProduct(2);

		// Analyze
		assertEquals(product, testProducts[1]);
	}

	@Test
	public void testDeleteProduct() {
		// Invoke
		boolean result = Assertions.assertDoesNotThrow(() -> productFileDAO.deleteProduct(3), "Unexpected exception thrown");

		// Analyze
		assertTrue(result);
		assertEquals(productFileDAO.getProducts().length, testProducts.length - 1);
	}

	@Test
	public void testCreateProduct() {
		// Setup
		Product product = new Product(99, "Magma", ElementType.FIRE, 14.99, 4);

		// Invoke
		Product result = Assertions.assertDoesNotThrow(() -> productFileDAO.createProduct(product), "Unexpected exception thrown");

		// Analyze
		assertNotNull(result);
		assertEquals(product.getName(), result.getName());
	}

	@Test
	public void testUpdateProduct() {
		// Setup
		Product product = new Product(1, "Steam", ElementType.AIR, 10.99, 25);

		// Invoke
		Product result = Assertions.assertDoesNotThrow(() -> productFileDAO.updateProduct(product), "Unexpected exception thrown");

		// Analyze
		assertNotNull(result);
		Product actual = productFileDAO.getProduct(product.getId());
		assertEquals(actual, product);
	}

	@Test
	public void testSaveException() throws IOException{
		doThrow(new IOException())
				.when(mockObjectMapper)
				.writeValue(any(File.class), any(Product[].class));

		Product product = new Product(99, "Magma", ElementType.FIRE, 14.99, 4);

		assertThrows(IOException.class, () -> productFileDAO.createProduct(product), "IOException not thrown");
	}

	@Test
	public void testGetProductNotFound() {
		// Invoke
		Product product = productFileDAO.getProduct(4);

		// Analyze
		assertNull(product);
	}

	@Test
	public void testDeleteProductNotFound() {
		// Invoke
		boolean result = Assertions.assertDoesNotThrow(() -> productFileDAO.deleteProduct(4), "Unexpected exception thrown");

		// Analyze
		assertFalse(result);
		assertEquals(productFileDAO.getProducts().length, testProducts.length);
	}

	@Test
	public void testUpdateProductNotFound() {
		// Setup
		Product product = new Product(99, "Magma", ElementType.FIRE, 14.99, 4);

		// Invoke
		Product result = Assertions.assertDoesNotThrow(() -> productFileDAO.updateProduct(product), "Unexpected exception thrown");

		// Analyze
		assertNull(result);
	}

	@Test
	public void testConstructorException() throws IOException {
		// Setup
		ObjectMapper mockObjectMapper = mock(ObjectMapper.class);

		doThrow(new IOException())
				.when(mockObjectMapper)
				.readValue(new File("doesnt_matter.txt"), Product[].class);

		// Invoke & Analyze
		assertThrows(IOException.class, () -> new ProductFileDAO("doesnt_matter.txt", mockObjectMapper), "IOException not thrown");
	}

}
