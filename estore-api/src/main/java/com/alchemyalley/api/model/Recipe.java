package com.alchemyalley.api.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Arrays;

/**
 * A recipe that involves multiple products.
 * @author Group 2
 */
public class Recipe {

	static final String STRING_FORMAT = "Recipe [inputs=%s, result=%d]";

	@JsonProperty("inputs") private Integer[] inputs;
	@JsonProperty("result") private int result;

	/**
	 * Creates a {@code Recipe} with its given attributes.
	 *
	 * @param inputs  The product ids required as input
	 * @param result  The resulting product id
	 */
	public Recipe(@JsonProperty("inputs") Integer[] inputs, @JsonProperty("result") int result) {
		this.inputs = inputs;
		this.result = result;
	}
	/**
	 * Gets the first product id for this recipe.
	 *
	 * @return  The first product id
	 */
	public int getFirstInput() {
		return this.inputs[0];
	}

	/**
	 * Gets the second product id for this recipe.
	 *
	 * @return  The second product id
	 */
	public int getSecondInput() {
		return this.inputs[1];
	}

	/**
	 * Gets all products ids required as input for this recipe.
	 *
	 * @return  All product ids for this recipe
	 */
	public Integer[] getIds() {
		return this.inputs;
	}

	/**
	 * Gets the resulting product id of this recipe.
	 *
	 * @return  The resulting product id
	 */
	public int getResult() {
		return this.result;
	}

	/**
	 * The string representation of a recipe.
	 *
	 * @return  The string representation, including fields
	 */
	@Override
	public String toString() {
		return String.format(STRING_FORMAT, Arrays.toString(this.inputs), this.result);
	}

}
