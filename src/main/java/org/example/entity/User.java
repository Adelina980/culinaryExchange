package org.example.entity;


import javax.persistence.*;
import java.sql.Blob;
import java.util.Date;
import java.util.List;

@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column
    private Blob avatar;

    @Column
    private String confirmationToken;

//    @Column
//    private String preferenceName;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Recipe> createdRecipes;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Recipe> favoriteRecipes;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Comment> comments;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ChallengeEntry> challengeEntries;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Feedback> feedbacks;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "createdAt", nullable = false, updatable = false)
    @org.hibernate.annotations.CreationTimestamp
    private String createdAt;


    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public String getConfirmationToken() {
        return confirmationToken;
    }
    public void setConfirmationToken(String confirmationToken){
        this.confirmationToken = confirmationToken;
    }
    public Blob getAvatar() {
        return avatar;
    }
    public void setAvatar(Blob avatar) {
        this.avatar = avatar;
    }

    public List<Recipe> getCreatedRecipes() {
        return createdRecipes;
    }
    public void setCreatedRecipes(List<Recipe> createdRecipes) {
        this.createdRecipes = createdRecipes;
    }
    public String getCreatedAt(){
        return createdAt;
    }
    public void setCreatedAt(String createdAt){
        this.createdAt = createdAt;
    }

    public List<Recipe> getFavoriteRecipes() {
        return favoriteRecipes;
    }

    public void setFavoriteRecipes(List<Recipe> favoriteRecipes) {
        this.favoriteRecipes = favoriteRecipes;
    }
    //    public String getPreferenceName() {
//        return preferenceName;
//    }
//    public void setPreferenceName(String preferenceName) {
//        this.preferenceName = preferenceName;
//    }

}

