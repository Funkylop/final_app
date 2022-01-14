package com.hramyko.finalapp.persistence.repository;

import com.hramyko.finalapp.persistence.entity.ConfirmationToken;
import com.hramyko.finalapp.persistence.entity.TokenType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ConfirmationTokenRepository extends JpaRepository<ConfirmationToken, Integer> {
    ConfirmationToken findConfirmationTokenByConfirmationToken(String token);
    void deleteConfirmationTokenByUserIdAndTokenType(int userId, TokenType tokenType);
}
