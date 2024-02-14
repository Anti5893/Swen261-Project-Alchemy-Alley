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
 * @author Group 2
 */
@Component
public class ProductFileDAO implements ProductDAO {

    Map<Integer, Product> products;
    private static int nextId;
    private final ObjectMapper objectMapper;
    private final String fileName;

    public ProductFileDAO(@Value("${products.file}") String fileName, ObjectMapper objectMapper) throws IOException {
        this.fileName = fileName;
        this.objectMapper = objectMapper;
        load();
    }

    private synchronized static int nextId() {
        int id = nextId;
        ++nextId;
        return id;
    }

    private Product[] getProductsArray() {
        return getProductsArray(null);
    }

    private Product[] getProductsArray(String containsText) {
        ArrayList<Product> productArrayList = new ArrayList<>();

        for(Product product : this.products.values()) {
            if(containsText == null || product.getName().toLowerCase().contains(containsText.toLowerCase())) {
                productArrayList.add(product);
            }
        }

        Product[] productArray = new Product[productArrayList.size()];
        productArrayList.toArray(productArray);
        return productArray;
    }

    private boolean save() throws IOException {
        Product[] productArray = getProductsArray();
        this.objectMapper.writeValue(new File(this.fileName),productArray);
        return true;
    }

    private void load() throws IOException {
        this.products = new TreeMap<>();
        nextId = 0;

        Product[] productArray = this.objectMapper.readValue(new File(this.fileName), Product[].class);
        for(Product product : productArray) {
            this.products.put(product.getId(), product);
            if(product.getId() > nextId) nextId = product.getId();
        }

        ++nextId;
    }

    @Override
    public Product[] getProducts() {
        synchronized(this.products) {
            return getProductsArray();
        }
    }

    @Override
    public Product[] findProducts(String containsText) {
        synchronized(this.products) {
            return getProductsArray(containsText);
        }
    }

    /**
     ** {@inheritDoc}
     */
    @Override
    public Product getProduct(int id) {
        synchronized(this.products) {
            return this.products.getOrDefault(id, null);
        }
    }

    // @Override
    // public Product createProduct(Product product) throws IOException {
    //     synchronized(this.products){
    //         if (getProductsArray(product.getName()).length != 0)
    //         {
    //             return null;
    //         }
    //         else 
    //         {
    //             Product newProduct = new Product(nextId(), product.getName(),product.getType(), product.getPrice(), product.getQuantity());
    //             this.products.put(newProduct.getId(), newProduct);
    //             save();
    //             return newProduct;
    //         }
    //     }
    // }
    @Override
    public Product createProduct(Product product) throws IOException {
        synchronized(this.products) {
            for(Product existingProduct : this.products.values()) {
                if(existingProduct.getName().equalsIgnoreCase(product.getName())) {
                    return null;
                }
            }
            Product newProduct = new Product(nextId(), product.getName(), product.getType(), product.getPrice(), product.getQuantity());
            this.products.put(newProduct.getId(), newProduct);
            save();
            return newProduct;
        }
    }

    @Override
    public Product updateProduct(Product product) throws IOException {
        synchronized(this.products) {
            if(!this.products.containsKey(product.getId())) return null;

            this.products.put(product.getId(), product);
            save();
            return product;
        }
    }

    @Override
    public boolean deleteProduct(int id) throws IOException {
        synchronized(this.products) {
            if(this.products.containsKey(id)) {
                this.products.remove(id);
                return save();
            } else {
                return false;
            }
        }
    }

}
