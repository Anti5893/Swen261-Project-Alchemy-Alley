package com.alchemyalley.api.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.alchemyalley.api.persistence.ProductDAO;
import com.alchemyalley.api.model.Product;

/**
 * Controller responsible for requests beginning with /inventory
 * 
 * @author Group 2
 */
@RestController
@RequestMapping("inventory")
public class InventoryController {

	private static final Logger LOG = Logger.getLogger(InventoryController.class.getName());
	private final ProductDAO productDAO;

	public InventoryController(ProductDAO productDAO) {
		this.productDAO = productDAO;
	}

	/**
	 * Retrieves a product with the given id.
	 * 
	 * @param id    The ID of the product to retrieve
	 * @return      The response that might contain a {@link Product}
	 */
	@GetMapping("/products/{id}")
	public ResponseEntity<Product> getProduct(@PathVariable int id) {
		LOG.info("GET /inventory/products/" + id);

		Product product = this.productDAO.getProduct(id);
		return product != null ? new ResponseEntity<>(product, HttpStatus.OK)
				: new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}

	/**
	 * Retrieves all stored products.
	 * 
	 * @return ResponseEntity<Product[]>    The response that contains all products
	 */
	@GetMapping("/products/")
	public ResponseEntity<Product[]> getProducts() {
		LOG.info("GET /inventory/products");

		Product[] products = this.productDAO.getProducts();
		return new ResponseEntity<>(products, HttpStatus.OK);
	}

	/**
	 * Retrieves all products whose name partially matches the input.
	 * 
	 * @param name                          The search query
	 * @return ResponseEntity<Product[]>    The response that may contain number of products
	 */
	@GetMapping("/products")
	public ResponseEntity<Product[]> searchProducts(@RequestParam String name) {
		LOG.info("GET /inventory/products?name=" + name);

		Product[] products = this.productDAO.findProducts(name);
		if (products.length > 0) {
			return new ResponseEntity<>(products, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	/**
	 * Creates a new product and stores it.
	 * 
	 * @param product                   The new product to create
	 * @return ResponseEntity<Product>  THe response that may contain the newly created product
	 */
	@PostMapping("/products")
	public ResponseEntity<Product> createProduct(@RequestBody Product product) {
		LOG.info("POST /inventory/products " + product);

		try {
			Product created = this.productDAO.createProduct(product);

			return created == null ? new ResponseEntity<>(HttpStatus.CONFLICT)
					: new ResponseEntity<>(created, HttpStatus.CREATED);
		} catch (IOException e) {
			LOG.log(Level.SEVERE, e.getLocalizedMessage());
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	/**
	 * Updates an existing product's stored data.
	 * 
	 * @param product                   The {@link Product} to update, specifically, its name
	 * @return ResponseEntity<Product>  The response that may contain the updated product
	 */
	@PutMapping("/products")
	public ResponseEntity<Product> updateProduct(@RequestBody Product product) {
		LOG.info("PUT /inventory/products " + product);

		try {
			Product updated = this.productDAO.updateProduct(product);

			return updated != null ? new ResponseEntity<>(updated, HttpStatus.OK)
					: new ResponseEntity<>(HttpStatus.NOT_FOUND);
		} catch (IOException e) {
			LOG.log(Level.SEVERE, e.getLocalizedMessage());
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	/**
	 * Deletes a product in the inventory by its ID.
	 * 
	 * @param id                        The ID of the product to delete
	 * @return ResponseEntity<Product>  The response that may contain the deleted product
	 */
	@DeleteMapping("/products/{id}")
	public ResponseEntity<Product> deleteProduct(@PathVariable int id) {
		LOG.info("DELETE /inventory/products/" + id);

		try {
			boolean existed = this.productDAO.deleteProduct(id);
			return existed ? new ResponseEntity<>(HttpStatus.OK) : new ResponseEntity<>(HttpStatus.NOT_FOUND);
		} catch (IOException e) {
			LOG.log(Level.SEVERE, e.getLocalizedMessage());
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

}
