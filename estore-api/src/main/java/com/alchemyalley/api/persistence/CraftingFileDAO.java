package com.alchemyalley.api.persistence;

import com.alchemyalley.api.model.Recipe;
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
	private final String fileName;
	private final ObjectMapper objectMapper;

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
	 * Loads the JSON file on disk and stores it in a map.
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

	@Override
	public Recipe getRecipe(Integer[] inputs) {
		for(Map.Entry<Integer[], Recipe> entry : this.recipes.entrySet()) {
			if(Arrays.equals(entry.getKey(), inputs)) {
				return entry.getValue();
			}
		}

		return null;
	}

	@Override
	public Recipe[] getAllRecipes() {
		return this.recipes.values().toArray(new Recipe[0]);
	}

}
