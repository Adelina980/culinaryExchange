package org.example.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
@Getter
@Setter
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
    private String steps;

    @Column(name = "createdAt", nullable = false, updatable = false)
    private String createdAt;

    @Column(nullable = true)
    private String coverImagePath;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "recipe", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Comment> comments;

    @OneToMany(mappedBy = "recipe", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Rating> ratings;

    @OneToMany(mappedBy = "recipe", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ImageRecipe> images = new ArrayList<>();

    public void addImage(ImageRecipe image) {
        images.add(image);
        image.setRecipe(this);
    }

    public void removeImage(ImageRecipe image) {
        images.remove(image);
        image.setRecipe(null);
    }

}

