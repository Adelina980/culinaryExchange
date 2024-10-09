package org.example.entity;

import javax.persistence.*;

@Entity
public class Feedback {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(columnDefinition = "TEXT")
    private String content;

    @Lob
    private byte[] image;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    public String getContent() {
        return content;
    }
    public void setContent(String content) {
        this.content = content;
    }
}

