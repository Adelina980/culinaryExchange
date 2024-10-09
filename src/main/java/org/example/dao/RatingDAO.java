package org.example.dao;

import org.example.entity.Rating;

public interface RatingDAO {

    Rating save(Rating rating);

    void delete(Rating rating);

    Double findAverageRatingForRecipe(Long recipeId);
}

