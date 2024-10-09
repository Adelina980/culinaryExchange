package org.example.entity;

import javax.persistence.*;
import java.util.List;

@Entity
public class Recipe {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(nullable = false)
    private String category;

    @Column
    private Integer preparationTime;

    @Column
    private Integer servings;

    @Column(columnDefinition = "TEXT")
    private String ingredients;

    @Column(columnDefinition = "TEXT")
    private String instructions;

    @Lob
    private byte[] image;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "recipe", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Comment> comments;

    @OneToMany(mappedBy = "recipe", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Rating> ratings;


    public String getName(){
        return name;
    }
    public void setName(String name){
        this.name = name;
    }
    public String getDescription(){
        return description;
    }
    public void setDescription(String description){
        this.description = description;
    }
    public String getCategory(){
        return category;
    }
    public void setCategory(String category){
        this.category = category;
    }
    public Integer getPreparationTime(){
        return preparationTime;
    }
    public void setPreparationTime(Integer preparationTime){
        this.preparationTime = preparationTime;
    }
    public Integer getServings(){
        return servings;
    }
    public void setServings(Integer servings){
        this.servings = servings;
    }
    public String getIngredients(){
        return ingredients;
    }
    public void setIngredients(String ingredients){
        this.ingredients = ingredients;
    }
    public String getInstructions(){
        return instructions;
    }
    public void setInstructions(String instructions){
        this.instructions = instructions;
    }
}

