package com.alchemyalley.persistence;

import com.alchemyalley.model.Recipe;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * An implementation of {@link CraftingDAO} that uses JSON files.
 * @author Group 2
 */
@Component
public class CraftingFileDAO implements CraftingDAO {

	Map<Integer[], Recipe> recipes;
	private final ObjectMapper objectMapper;
	private final String fileName;

	/**
	 * Creates an instance of this DAO over a JSON file.
	 *
	 * @param fileName      The file to load.
	 * @param objectMapper  The object mapper.
	 * @throws IOException  If there is an error reading from disk
	 */
	public CraftingFileDAO(@Value("${crafting.file}") String fileName, ObjectMapper objectMapper) throws IOException {
		this.fileName = fileName;
		this.objectMapper = objectMapper;
		load();
	}

	/**
	 * Loads the recipes from file.
	 *
	 * @throws IOException  If there is an error reading from disk
	 */
	private void load() throws IOException {
		this.recipes = new HashMap<>();

		Recipe[] recipes = this.objectMapper.readValue(new File(this.fileName), Recipe[].class);

		for(Recipe recipe : recipes) {
			this.recipes.put(recipe.getIds(), recipe);
		}
	}

	/**
	 * Searches for a recipe by its product id inputs.
	 *
	 * @param inputs    The product ids of the recipe's input
	 * @return          The recipe if found, if not, null
	 */
	@Override
	public Recipe getRecipe(Integer[] inputs) {
		for(Map.Entry<Integer[], Recipe> entry : this.recipes.entrySet()) {
			if(Arrays.equals(entry.getKey(), inputs)) {
				return entry.getValue();
			}
		}

		return null;
	}

	/**
	 * Retrieves all recipes loaded from disk.
	 *
	 * @return  All {@link Recipe} objects that have been loaded
	 */
	@Override
	public Recipe[] getAllRecipes() {
		return this.recipes.values().toArray(new Recipe[0]);
	}

}
