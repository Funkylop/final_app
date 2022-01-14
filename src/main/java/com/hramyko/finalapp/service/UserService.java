package com.hramyko.finalapp.service;

import com.hramyko.finalapp.persistence.entity.User;
import org.springframework.transaction.annotation.Transactional;

public interface UserService {
    String findAll();
    String findUserById(int id);
    User saveUser(User user);
    String updateUser(User user);
    void destroyUser(int id);

    @Transactional
    String getCurrentUser();

    User findUserByEmail(String email);

    String findAllTraders();

    String updateUserStatus(String email, String status);
    String updateUserRole(int id, String jsonString);
    User currentUser();
    String updateUserPassword(int idUser, User user);

    @Transactional
    User getUserFromOptional(int id);
}
