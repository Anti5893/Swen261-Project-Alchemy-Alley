package com.alchemyalley.api.persistence;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.alchemyalley.api.model.Product;

/**
 * An implementation of {@link ProductDAO} that uses JSON.
 * 
 * @author Group 2
 */
@Component
public class ProductFileDAO implements ProductDAO {

    private final Map<Integer, Product> products;
    private static int nextId;
    private final String fileName;
    private final ObjectMapper objectMapper;

    /**
     * Creates an instance of this DAO over a JSON file.
     *
     * @param fileName     The file to load.
     * @param objectMapper The object mapper.
     * @throws IOException If there is an error reading from disk
     */
    public ProductFileDAO(@Value("${products.file}") String fileName, ObjectMapper objectMapper) throws IOException {
        this.products = new TreeMap<>();
        this.fileName = fileName;
        this.objectMapper = objectMapper;
        load();
    }

    /**
     * Returns the next available ID.
     * 
     * @return int for the next available ID
     */
    private synchronized static int nextId() {
        int id = nextId;
        ++nextId;
        return id;
    }

    /**
     * Returns an array of the products
     * 
     * @return Product[]
     */
    private Product[] getProductsArray() {
        return getProductsArray(null);
    }

    /**
     * Returns an array of the products containing the text
     * 
     * @param containsText
     * @return
     */
    private Product[] getProductsArray(String containsText) {
        ArrayList<Product> productArrayList = new ArrayList<>();

        for (Product product : this.products.values()) {
            if (containsText == null || product.getName().toLowerCase().contains(containsText.toLowerCase())) {
                productArrayList.add(product);
            }
        }

        Product[] productArray = new Product[productArrayList.size()];
        productArrayList.toArray(productArray);
        return productArray;
    }

    /**
     * Loads the JSON file on disk and stores it in a map.
     *
     * @throws IOException If there is an error reading from disk
     */
    private void load() throws IOException {
        nextId = 0;

        Product[] productArray = this.objectMapper.readValue(new File(this.fileName), Product[].class);
        for (Product product : productArray) {
            this.products.put(product.getId(), product);
            if (product.getId() > nextId)
                nextId = product.getId();
        }

        ++nextId;
    }

    /**
     * Saves the map to disk as a JSON file.
     *
     * @throws IOException If there is an error saving to disk
     */
    private void save() throws IOException {
        Product[] productArray = getProductsArray();
        this.objectMapper.writeValue(new File(this.fileName), productArray);
    }

    /**
     * Returns an array of the products
     * 
     * @return Product[]
     */
    @Override
    public Product[] getProducts() {
        synchronized (this.products) {
            return getProductsArray();
        }
    }

    /**
     * Returns an array of the products containing the text
     * 
     * @param containsText
     * @return Product[]
     */
    @Override
    public Product[] findProducts(String containsText) {
        synchronized (this.products) {
            return getProductsArray(containsText);
        }
    }

    /**
     * Returns the product with the given ID
     * 
     * @param id
     * @return Product
     */
    @Override
    public Product getProduct(int id) {
        synchronized (this.products) {
            return this.products.getOrDefault(id, null);
        }
    }

    /**
     * Creates a product with the given attributes, returns the created product
     * 
     * @param product
     * @return Product
     * @throws IOException
     */
    @Override
    public Product createProduct(Product product) throws IOException {
        synchronized (this.products) {
            for (Product existingProduct : this.products.values()) {
                if (existingProduct.getName().equalsIgnoreCase(product.getName())) {
                    return null;
                }
            }
            Product newProduct = new Product(nextId(), product.getName(), product.getType(), product.getPrice(),
                    product.getQuantity(), product.getURL());
            this.products.put(newProduct.getId(), newProduct);
            save();
            return newProduct;
        }
    }

    /**
     * Updates the product with the given attributes, returns the updated product
     * 
     * @param product
     * @return Product
     * @throws IOException
     */
    @Override
    public Product updateProduct(Product product) throws IOException {
        synchronized (this.products) {
            if (!this.products.containsKey(product.getId()))
                return null;

            this.products.put(product.getId(), product);
            save();
            return product;
        }
    }

    /**
     * Deletes the product with the given ID, returns true if successful, false
     * otherwise
     * 
     * @param id
     * @return boolean
     * @throws IOException
     */
    @Override
    public boolean deleteProduct(int id) throws IOException {
        synchronized (this.products) {
            if (this.products.containsKey(id)) {
                this.products.remove(id);
                save();
                return true;
            } else {
                return false;
            }
        }
    }

}
