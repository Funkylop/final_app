package com.hramyko.finalapp.persistence.entity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "posts")
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;
    double price;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_game_object")
    GameObject gameObject;

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL, orphanRemoval = true)
    List<Comment> comments;

    public Post() {
    }

    public Post(GameObject gameObject, double price) {
        this.gameObject = gameObject;
        this.price = price;
        this.comments = new ArrayList<>();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public GameObject getGameObject() {
        return gameObject;
    }

    public void setGameObject(GameObject gameObject) {
        this.gameObject = gameObject;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void addComment(Comment comment) {
        this.comments.add(comment);
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Post post = (Post) o;
        return id == post.id && Double.compare(post.price, price) == 0 && Objects.equals(gameObject, post.gameObject) && Objects.equals(comments, post.comments);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, price, gameObject, comments);
    }

    @Override
    public String toString() {
        return "Post{" +
                "id=" + id +
                ", price=" + price +
                ", gameObject=" + gameObject +
                ", comments=" + comments +
                '}';
    }
}
