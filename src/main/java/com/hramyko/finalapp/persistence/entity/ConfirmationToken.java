package com.hramyko.finalapp.persistence.entity;

import javax.persistence.*;
import java.util.Date;
import java.util.Objects;
import java.util.UUID;

@Entity(name = "tokens")
public class ConfirmationToken {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "confirmation_token")
    private String confirmationToken;
    @Column(name = "created_at")
    private Date createdAt;
    @Column(name = "token_type")
    @Enumerated(EnumType.STRING)
    private TokenType tokenType;
    @Column(name = "user_id")
    private int userId;

    public ConfirmationToken() {
    }

    public ConfirmationToken(int userId, TokenType tokenType) {
        this.confirmationToken = UUID.randomUUID().toString();
        this.userId = userId;
        this.tokenType = tokenType;
    }

    public ConfirmationToken(String confirmationToken, int userId, Date date, TokenType tokenType) {
        this.confirmationToken = confirmationToken;
        this.userId = userId;
        this.createdAt = date;
        this.tokenType = tokenType;
    }

    public String getConfirmationToken() {
        return confirmationToken;
    }

    public void setConfirmationToken(String confirmationToken) {
        this.confirmationToken = confirmationToken;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public TokenType getTokenType() {
        return tokenType;
    }

    public void setTokenType(TokenType tokenType) {
        this.tokenType = tokenType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ConfirmationToken that = (ConfirmationToken) o;
        return confirmationToken.equals(that.confirmationToken) && Objects.equals(createdAt, that.createdAt) && tokenType == that.tokenType;
    }

    @Override
    public int hashCode() {
        return Objects.hash(confirmationToken, createdAt, tokenType);
    }

    @Override
    public String toString() {
        return "ConfirmationToken{" +
                "confirmationToken='" + confirmationToken + '\'' +
                ", createdAt=" + createdAt +
                ", tokenType=" + tokenType +
                '}';
    }
}
