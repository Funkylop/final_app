package com.hramyko.finalapp.persistence.entity;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "traders")
public class Trader extends User{

    @OneToMany(mappedBy = "trader")
    private List<GameObject> gameObjects;


    public Trader() {
    }

    public Trader(User user) {
        super(user.getEmail(), user.getPassword(), user.getFirstName(), user.getLastName(),
                user.getCreatedAt(), user.getRole(), user.getStatus());
    }

    public Trader(String email, String password, String firstName,
                  String lastName, Date createdAt, Role role, Status status, List<GameObject> gameObjects) {
        super(email, password, firstName, lastName, createdAt, role, status);
    }

    public List<GameObject> getGameObjects() {
        return gameObjects;
    }

    public void setGameObjects(List<GameObject> gameObjects) {
        this.gameObjects = gameObjects;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Trader trader = (Trader) o;
        return Objects.equals(gameObjects, trader.gameObjects);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), gameObjects);
    }

    @Override
    public String toString() {
        return "Trader{" +
                "id=" + this.getId() +
                ", email='" + this.getEmail() + '\'' +
                ", firstName='" + this.getFirstName() + '\'' +
                ", lastName='" + this.getLastName() + '\'' +
                ", createdAt=" + this.getCreatedAt() +
                ", role=" + this.getRole() +
                ", game objects" + gameObjects +
                '}';
    }
}
