package com.hramyko.finalapp.service.impl;

import com.hramyko.finalapp.persistence.entity.ConfirmationToken;
import com.hramyko.finalapp.persistence.entity.TokenType;
import com.hramyko.finalapp.persistence.entity.User;
import com.hramyko.finalapp.persistence.repository.ConfirmationTokenRepository;
import com.hramyko.finalapp.service.ConfirmationTokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ConfirmationTokenServiceImpl implements ConfirmationTokenService {

    private final ConfirmationTokenRepository confirmationTokenRepository;

    @Autowired
    ConfirmationTokenServiceImpl(ConfirmationTokenRepository confirmationTokenRepository) {
        this.confirmationTokenRepository = confirmationTokenRepository;
    }

    @Transactional
    @Override
    public ConfirmationToken findByConfirmationToken(String confirmationToken) {
        return confirmationTokenRepository.findConfirmationTokenByConfirmationToken(confirmationToken);
    }

    @Transactional
    @Override
    public void deleteConfirmationToken(User user, TokenType type) {
        confirmationTokenRepository.deleteConfirmationTokenByUserIdAndTokenType(user.getId(), type);
    }

    @Override
    public void createConfirmationToken(ConfirmationToken confirmationToken) {
        confirmationTokenRepository.save(confirmationToken);
    }
}
