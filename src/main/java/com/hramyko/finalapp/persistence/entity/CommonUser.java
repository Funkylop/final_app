package com.hramyko.finalapp.persistence.entity;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "common_users")
public class CommonUser extends User {

    @OneToMany(mappedBy = "author")
    private List<Comment> comments;

    public CommonUser() {
    }

    public CommonUser(User user) {
        super(user.getEmail(), user.getPassword(), user.getFirstName(), user.getLastName(),
                user.getCreatedAt(), user.getRole(), user.getStatus());
    }

    public CommonUser(String email, String password, String firstName,
                  String lastName, Date createdAt, Role role, Status status, List<Comment> comments) {
        super(email, password, firstName, lastName, createdAt, role, status);
        this.comments = comments;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        CommonUser that = (CommonUser) o;
        return Objects.equals(comments, that.comments);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), comments);
    }

    @Override
    public String toString() {
        return "CommonUser{" +
                "id=" + this.getId() +
                ", email='" + this.getEmail() + '\'' +
                ", firstName='" + this.getFirstName() + '\'' +
                ", lastName='" + this.getLastName() + '\'' +
                ", comments=" + comments +
                ", createdAt=" + this.getCreatedAt() +
                ", role=" + this.getRole() +
                '}';
    }
}
