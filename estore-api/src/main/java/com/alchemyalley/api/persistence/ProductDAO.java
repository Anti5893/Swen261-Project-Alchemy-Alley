package com.alchemyalley.api.persistence;

import java.io.IOException;
import com.alchemyalley.api.model.Product;

public interface ProductDAO {

    Product[] getProducts() throws IOException;

    Product[] findProducts(String containsText) throws IOException;

    Product getProduct(int id) throws IOException;

    Product createProduct(Product product) throws IOException;

    Product updateProduct(Product product) throws IOException;

    boolean deleteProduct(int id) throws IOException;

}
