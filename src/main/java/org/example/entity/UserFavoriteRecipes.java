package org.example.entity;

import javax.persistence.*;

@Entity
public class UserFavoriteRecipes {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "recipe_id", nullable = false)
    private Recipe recipe;

    public void setId(Long id) {
        this.id = id;
    }
    public Long getId() {
        return id;
    }
    public void setUser(User user) {
        this.user = user;
    }
    public User getUser() {
        return user;
    }
    public void setRecipe(Recipe recipe) {
        this.recipe = recipe;
    }
    public Recipe getRecipe() {
        return recipe;
    }
}
