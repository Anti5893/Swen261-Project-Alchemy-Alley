package com.alchemyalley.persistence;

import com.alchemyalley.model.Recipe;

/**
 * Interface to interact with crafting data
 * @author Group 2
 */
public interface CraftingDAO {

	/**
	 * Retrieves a {@link Recipe} by its corresponding inputs.
	 *
	 * @param inputs    The product ids of the recipe's input
	 * @return          The {@link Recipe} its associated with
	 */
	Recipe getRecipe(Integer[] inputs);

	/**
	 * Retrieves all recipes.
	 *
	 * @return  An array of all {@link Recipe} objects
	 */
	Recipe[] getAllRecipes();

}
