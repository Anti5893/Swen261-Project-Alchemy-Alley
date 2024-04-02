package com.alchemyalley.api.persistence;

import java.io.IOException;
import com.alchemyalley.api.model.Product;

/**
 * Interface to interact with product data.
 * 
 * @author Group 2
 */
public interface ProductDAO {

    /**
     * Retrieves all products.
     *
     * @return An array of all {@link Product} objects
     */
    Product[] getProducts();

    /**
     * Retrieves all products that contain
     * the given text in their name.
     *
     * @param containsText The partial name to filter by
     * @return An array of all {@link Product} objects that match
     */
    Product[] findProducts(String containsText);

    /**
     * Retrieves a product by its given ID.
     *
     * @param id The ID of the product to retrieve
     * @return The {@link Product} object,
     *         or {@code null} if non-existent
     */
    Product getProduct(int id);

    /**
     * Creates a new product and saves it.
     *
     * @param product The product to create
     * @return The created {@link Product} object
     * @throws IOException If there is an error saving
     */
    Product createProduct(Product product) throws IOException;

    /**
     * Updates an existing product and saves it.
     *
     * @param product The product and its modifications to update
     * @return The updated {@link Product} object,
     *         or {@code null} if non-existent
     * @throws IOException If there is an error saving
     */
    Product updateProduct(Product product) throws IOException;

    /**
     * Deletes an existing product and saves.
     *
     * @param id The ID of the product to delete
     * @return {@code true} if the deletion was successful, or
     *         {@code false} if the product did not exist or there
     *         was an error saving
     * @throws IOException If there is an error saving
     */
    boolean deleteProduct(int id) throws IOException;

}
