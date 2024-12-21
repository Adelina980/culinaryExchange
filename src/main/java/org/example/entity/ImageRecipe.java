package org.example.entity;


import lombok.Getter;
import lombok.Setter;


import javax.persistence.*;

@Getter
@Setter
@Entity
public class ImageRecipe {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String filePath;

    @ManyToOne
    @JoinColumn(name = "recipe_id", nullable = false)
    private Recipe recipe;

}

