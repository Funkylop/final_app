package com.hramyko.finalapp.service.impl;

import com.hramyko.finalapp.persistence.entity.CommonUser;
import com.hramyko.finalapp.persistence.repository.CommonUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CommonUserServiceImpl implements com.hramyko.finalapp.service.CommonUserService {

    private final CommonUserRepository commonUserRepository;


    @Autowired
    CommonUserServiceImpl(CommonUserRepository commonUserRepository) {
        this.commonUserRepository = commonUserRepository;
    }

    @Override
    public List<CommonUser> findAll() {
        return commonUserRepository.findAll();
    }

    @Transactional
    @Override
    public CommonUser save(CommonUser commonUser) {
        if ("BANNED".equals(commonUser.getStatus().toString())) {
            String encodedPassword = getEncodedPassword(commonUser.getPassword());
            commonUser.setPassword(encodedPassword);
        }
        return commonUserRepository.save(commonUser);
    }

    private String getEncodedPassword(String password)
    {
        return new BCryptPasswordEncoder(12).encode(password);
    }
}
