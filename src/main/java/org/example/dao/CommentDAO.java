package org.example.dao;

import org.example.entity.Comment;

import java.util.List;

public interface CommentDAO {

    Comment save(Comment comment);

    List<Comment> findByRecipeId(Long recipeId);

    void delete(Comment comment);
}

