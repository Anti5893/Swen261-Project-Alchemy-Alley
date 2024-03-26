package com.alchemyalley.api.model;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Represents the category for a specific {@link Product}.
 * @author Group 2
 */
public enum ElementType {

    @JsonProperty("EARTH") EARTH,
    @JsonProperty("FIRE") FIRE,
    @JsonProperty("WATER") WATER,
    @JsonProperty("AIR") AIR,
    @JsonProperty("ENERGY") ENERGY

}
