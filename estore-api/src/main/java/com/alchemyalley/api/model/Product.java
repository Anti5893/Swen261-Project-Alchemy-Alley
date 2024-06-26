package com.alchemyalley.api.model;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Represents a Product entity.
 * 
 * @author Group 2
 */
public class Product {

    static final String STRING_FORMAT = "Product [id=%d, name=%s, type=%s, price=%f, quantity=%d, imageURL=%s]";

    @JsonProperty("id")
    private final int id;
    @JsonProperty("name")
    private final String name;
    @JsonProperty("type")
    private final ElementType type;
    @JsonProperty("price")
    private final double price;
    @JsonProperty("quantity")
    private int quantity;
    @JsonProperty("imageURL")
    private final String imageURL;

    /**
     * Creates a {@code Product} with its given attributes.
     *
     * @param id        The id of the product
     * @param name      The name of the product
     * @param type      The {@link ElementType} of the product
     * @param price     The price of the product
     * @param quantity  The quantity of the product
     * @param imageURL  The URL for displaying the product.
     */
    public Product(@JsonProperty("id") int id, @JsonProperty("name") String name,
                   @JsonProperty("type") ElementType type, @JsonProperty("price") double price,
                   @JsonProperty("quantity") int quantity, @JsonProperty("imageURL") String imageURL) throws IllegalArgumentException {
        if(id < 0) throw new IllegalArgumentException("Product ID cannot be < 0");
        this.id = id;
        this.name = name;
        this.type = type;
        this.price = price;
        this.quantity = quantity;
        this.imageURL = imageURL;
    }

    /**
     * Gets the id of this product.
     *
     * @return The id of the product
     */
    public int getId() {
        return this.id;
    }

    /**
     * Gets the name of this product
     *
     * @return The name of the product
     */
    public String getName() {
        return this.name;
    }

    /**
     * Gets the type of this product
     *
     * @return The {@link ElementType} of this product
     */
    public ElementType getType() {
        return this.type;
    }

    /**
     * Gets the price of this product
     *
     * @return The price of this product, as a double
     */
    public double getPrice() {
        return this.price;
    }

    /**
     * Gets the quantity of this product.
     *
     * @return The quantity of this product, as an integer
     */
    public int getQuantity() {
        return this.quantity;
    }

    /**
     * Decrements the quantity of the product by 1.
     */
    public Product decrementStock() {
        this.quantity--;
        return this;
    }
    
    /**
     * Gets the image URL for this product.
     * @return  The imageURL for the product. 
     */
    public String getImageURL(){
        return this.imageURL;
    }

    /**
     * The string representation of a product.
     *
     * @return Its string representation, including fields
     */
    @Override
    public String toString() {
        return String.format(STRING_FORMAT, this.id, this.name, this.type, this.price, this.quantity, this.imageURL);
    }

    /**
     * Checks whether two instances of {@link Product} are the same.
     *
     * @param o The object to compare to
     * @return Whether the two instances are the same
     */
    @Override
    public boolean equals(Object o) {
        if (o instanceof Product product) {
            return id == product.id && Double.compare(product.price, price) == 0 && quantity == product.quantity &&
                    name.equals(product.name) && type == product.type && imageURL.equals(product.imageURL);
        }
        return false;
    }

}