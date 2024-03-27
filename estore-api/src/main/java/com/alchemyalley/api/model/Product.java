package com.alchemyalley.api.model;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Represents a Product entity.
 * @author Group 2
 */
public class Product {

    static final String STRING_FORMAT = "Product [id=%d, name=%s, type=%s, price=%f, quantity=%d]";

    @JsonProperty("id") private final int id;
    @JsonProperty("name") private final String name;
    @JsonProperty("type") private final ElementType type;
    @JsonProperty("price") private double price;
    @JsonProperty("quantity") private int quantity;

    /**
     * Creates a {@code Product} with its given attributes.
     *
     * @param id        The id of the product
     * @param name      The name of the product
     * @param type      The {@link ElementType} of the product
     * @param price     The price of the product
     * @param quantity  The quantity of the product
     */
    public Product(@JsonProperty("id") int id, @JsonProperty("name") String name,
                   @JsonProperty("type") ElementType type, @JsonProperty("price") double price,
                   @JsonProperty("quantity") int quantity) throws IllegalArgumentException {
        if(id < 0) throw new IllegalArgumentException("Product ID cannot be < 0");
        this.id = id;
        this.name = name;
        this.type = type;
        this.price = price;
        this.quantity = quantity;
    }

    /**
     * Gets the id of this product.
     *
     * @return  The id of the product
     */
    public int getId() {
        return this.id;
    }

    /**
     * Gets the name of this product
     *
     * @return  The name of the product
     */
    public String getName() {
        return this.name;
    }

    /**
     * Gets the type of this product
     *
     * @return  The {@link ElementType} of this product
     */
    public ElementType getType() {
        return this.type;
    }

    /**
     * Gets the price of this product
     *
     * @return  The price of this product, as a double
     */
    public double getPrice() {
        return this.price;
    }

    /**
     * Gets the quantity of this product.
     *
     * @return  The quantity of this product, as an integer
     */
    public int getQuantity() {
        return this.quantity;
    }

    /**
     * Decrements the quantity of the product by 1.
     */
    public void decrementStock() {
        this.quantity--;
    }

    /**
     * The string representation of a product.
     *
     * @return  Its string representation, including fields
     */
    @Override
    public String toString() {
        return String.format(STRING_FORMAT, this.id, this.name, this.type, this.price, this.quantity);
    }

}