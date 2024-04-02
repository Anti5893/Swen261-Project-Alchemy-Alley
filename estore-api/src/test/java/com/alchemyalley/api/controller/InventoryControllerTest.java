package com.alchemyalley.api.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.IOException;

import com.alchemyalley.api.persistence.ProductDAO;
import com.alchemyalley.api.model.ElementType;
import com.alchemyalley.api.model.Product;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

/**
 * Tests the InventoryController class.
 * @author Group 2
 */
@Tag("Controller-tier")
public class InventoryControllerTest {

    private ProductDAO mockProductDAO;
    private InventoryController inventoryController;

    /**
     * Before each test, creates a new {@link InventoryController} object
     * and injects a mock {@link ProductDAO} object
     */
    @BeforeEach
    public void setupInventoryController() {
        this.mockProductDAO = mock(ProductDAO.class);
        this.inventoryController = new InventoryController(this.mockProductDAO);
    }

    @Test
    public void testGetProduct() {
        // Setup
        Product product = new Product(99, "Steam", ElementType.AIR, 19.99, 50, "fake/url");
        when(this.mockProductDAO.getProduct(product.getId())).thenReturn(product);

        // Invoke
        ResponseEntity<Product> response = this.inventoryController.getProduct(product.getId());

        // Analyze
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(product, response.getBody());
    }

    @Test
    public void testGetProductNotFound() {
        // Setup
        int productID = 99;
        when(this.mockProductDAO.getProduct(productID)).thenReturn(null);

        // Invoke
        ResponseEntity<Product> response = this.inventoryController.getProduct(productID);

        // Analyze
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    public void testSearchProducts() {
        // Setup
        String searchString = "fire";
        Product[] products = new Product[3];
        products[0] = new Product(0, "Fire", ElementType.AIR, 1.5, 50,"fake/url");
        products[1] = new Product(6, "Fire Bolt", ElementType.AIR, 1.5, 50,"fake/url");
        products[2] = new Product(13, "Fire Rain", ElementType.AIR, 1.5, 50,"fake/url");
        when(this.mockProductDAO.findProducts(searchString)).thenReturn(products);

        // Invoke
        ResponseEntity<Product[]> response = this.inventoryController.searchProducts(searchString);

        // Analyze
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(products, response.getBody());
    }

    @Test
    public void testCreateProduct() throws IOException {
        // Setup
        Product product = new Product(99, "Steam", ElementType.AIR, 19.99, 50,"fake/url");
        when(this.mockProductDAO.createProduct(product)).thenReturn(product);

        // Invoke
        ResponseEntity<Product> response = this.inventoryController.createProduct(product);

        // Analyze
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(product, response.getBody());
    }

    @Test
    public void testCreateProductFailed() throws IOException {
        // Setup
        Product product = new Product(99, "Steam", ElementType.AIR, 19.99, 50,"fake/url");
        when(this.mockProductDAO.createProduct(product)).thenReturn(null);

        // Invoke
        ResponseEntity<Product> response = this.inventoryController.createProduct(product);

        // Analyze
        assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
    }

    @Test
    public void testCreateProductHandleException() throws IOException {
        // Setup
        Product product = new Product(99, "Steam", ElementType.AIR, 19.99, 50,"fake/url");
        doThrow(new IOException()).when(this.mockProductDAO).createProduct(product);

        // Invoke
        ResponseEntity<Product> response = this.inventoryController.createProduct(product);

        // Analyze
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }

    @Test
    public void testUpdateProduct() throws IOException {
        // Setup
        Product product = new Product(99, "Steam", ElementType.AIR, 19.99, 50,"fake/url");
        when(this.mockProductDAO.updateProduct(product)).thenReturn(product);

        // Invoke
        ResponseEntity<Product> response = this.inventoryController.updateProduct(product);

        // Analyze
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(product, response.getBody());
    }

    @Test
    public void testUpdateProductFailed() throws IOException {
        // Setup
        Product product = new Product(99, "Steam", ElementType.AIR, 19.99, 50,"fake/url");
        when(this.mockProductDAO.updateProduct(product)).thenReturn(null);

        // Invoke
        ResponseEntity<Product> response = this.inventoryController.updateProduct(product);

        // Analyze
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    public void testUpdateProductHandleException() throws IOException {
        // Setup
        Product product = new Product(99, "Steam", ElementType.AIR, 19.99, 50,"fake/url" );
        doThrow(new IOException()).when(this.mockProductDAO).updateProduct(product);

        // Invoke
        ResponseEntity<Product> response = this.inventoryController.updateProduct(product);

        // Analyze
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }

    @Test
    public void testDeleteProduct() throws IOException {
        // Setup
        int productId = 99;
        when(this.mockProductDAO.deleteProduct(productId)).thenReturn(true);

        // Invoke
        ResponseEntity<Product> response = this.inventoryController.deleteProduct(productId);

        // Analyze
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    public void testDeleteProductNotFound() throws IOException {
        // Setup
        int productId = 99;
        when(this.mockProductDAO.deleteProduct(productId)).thenReturn(false);

        // Invoke
        ResponseEntity<Product> response = this.inventoryController.deleteProduct(productId);

        // Analyze
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    public void testDeleteProductHandleException() throws IOException {
        // Setup
        int productId = 99;
        doThrow(new IOException()).when(this.mockProductDAO).deleteProduct(productId);

        // Invoke
        ResponseEntity<Product> response = this.inventoryController.deleteProduct(productId);

        // Analyze
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }

}
