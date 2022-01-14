package com.hramyko.finalapp.persistence.entity;

import javax.persistence.*;
import java.util.Date;
import java.util.Objects;

@Entity
@Table(name = "game_objects")
public class GameObject {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private Trader trader;

    @OneToOne(mappedBy = "gameObject")
    private Post post;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "game_id")
    private Game game;

    private String title;
    private String text;
    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private GameObjectStatus status = GameObjectStatus.AVAILABLE;
    @Column(name = "created_at")
    private Date createdAt = new Date();
    @Column(name = "updated_at")
    private Date updatedAt = new Date();

    public GameObject() {
    }

    public GameObject(String title, String text) {
        this.title = title;
        this.text = text;
    }

    public GameObject(String title, String text, GameObjectStatus status) {
        this.title = title;
        this.text = text;
        this.status = status;
    }

    public GameObject(String title,
                      String text, GameObjectStatus status, Date createdAt,
                      Date updatedAt) {
        this.title = title;
        this.text = text;
        this.status = status;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    public int getId() {
        return id;
    }

    public Trader getUser() {
        return trader;
    }

    public void setTrader(Trader trader) {
        this.trader = trader;
    }

    public Game getGame() {
        return game;
    }

    public void setGame(Game game) {
        this.game = game;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Trader getTrader() {
        return trader;
    }

    public Post getPost() {
        return post;
    }

    public void setPost(Post post) {
        this.post = post;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public GameObjectStatus getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = GameObjectStatus.valueOf(status.toUpperCase());
    }

    public void setStatus(GameObjectStatus status) {
        this.status = status;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt() {
        this.updatedAt = new Date();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GameObject that = (GameObject) o;
        return id == that.id && Objects.equals(trader, that.trader) && Objects.equals(game, that.game) && Objects.equals(title, that.title) && Objects.equals(text, that.text) && status == that.status && Objects.equals(createdAt, that.createdAt) && Objects.equals(updatedAt, that.updatedAt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, text, status, createdAt, updatedAt);
    }

    @Override
    public String toString() {
        return "GameObject{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", text='" + text + '\'' +
                ", status=" + status +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                '}';
    }
}
