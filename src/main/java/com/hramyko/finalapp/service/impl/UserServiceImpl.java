package com.hramyko.finalapp.service.impl;

import com.hramyko.finalapp.persistence.entity.*;
import com.hramyko.finalapp.persistence.repository.AdminRepository;
import com.hramyko.finalapp.persistence.repository.CommonUserRepository;
import com.hramyko.finalapp.persistence.repository.UserRepository;
import com.hramyko.finalapp.service.TraderService;
import com.hramyko.finalapp.service.UserService;
import com.hramyko.finalapp.service.parser.JsonParser;
import com.hramyko.finalapp.service.validator.UserValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private final UserValidator userValidator;
    private final UserRepository userRepository;
    private final TraderService traderService;
    private final CommonUserRepository commonUserService;
    private final AdminRepository adminService;

    @Autowired
    public UserServiceImpl(UserValidator userValidator, TraderService traderService,
                           UserRepository userRepository, CommonUserRepository commonUserRepository,
                           AdminRepository adminRepository) {
        this.userValidator = userValidator;
        this.userRepository = userRepository;
        this.traderService = traderService;
        this.commonUserService = commonUserRepository;
        this.adminService = adminRepository;
    }

    @Override
    @Transactional
    public String findAll() {
        return userRepository.findAll().toString();
    }

    @Override
    @Transactional
    public String findAllTraders() {
        List<Trader> traders = traderService.findAll();
        return traders.get(0).getGameObjects().toString();
    }

    @Transactional
    @Override
    public String findUserById(int id) {
        userValidator.validateId(id);
        return getUserFromOptional(id).toString();
    }

    @Transactional
    @Override
    public User saveUser(User user) {
        userValidator.validate(user);
        String encodedPassword = getEncodedPassword(user.getPassword());
        user.setPassword(encodedPassword);
        user = traderService.save(new Trader(user));
        return user;
    }

    @Transactional
    @Override
    public String updateUserPassword(int idUser, User user) {
        userValidator.validateId(idUser);
        if (user.getPassword() != null) {
            userValidator.validatePassword(user.getPassword());
        }
        User targetUser = userRepository.findById(idUser).get();
        targetUser.setPassword(user.getPassword());
        return userRepository.save(targetUser).toString();
    }

    @Transactional
    @Override
    public String updateUser(User user) {
        User currentUser = currentUser();
        if (user.getPassword() != null) {
            userValidator.validatePassword(user.getPassword());
            currentUser.setPassword(user.getPassword());
        }
        if (user.getFirstName() != null) {
            userValidator.validateName(user.getFirstName());
            currentUser.setFirstName(user.getFirstName());
        }
        if (user.getLastName() != null) {
            userValidator.validateName(user.getLastName());
            currentUser.setLastName(user.getLastName());
        }
        return userRepository.save(currentUser).toString();
    }

    @Transactional
    @Override
    public void destroyUser(int id) {
        userValidator.validateId(id);
        userRepository.deleteById(id);
    }

    @Transactional
    @Override
    public String updateUserStatus(String email, String status) {
        status = status.toUpperCase();
        userValidator.validateEmail(email);
        userValidator.validateStatus(status);
        User user = userRepository.findUserByEmail(email);
        user.setStatus(Status.valueOf(status));
        return userRepository.save(user).toString();
    }

    @Transactional
    @Override
    public String updateUserRole(int id, String jsonString) {
        String role = JsonParser.getInfoFromJson(jsonString, "role");
        role = role.toUpperCase();
        userValidator.validateId(id);
        userValidator.validateRole(role);
        User user = userRepository.findById(id).get();
        userRepository.deleteById(id);
        user.setRole(role);
        if ("TRADER".equals(role)) {
            traderService.save(new Trader(user));
        } else if ("USER".equals(role)) {
            commonUserService.save(new CommonUser(user));
        } else if ("ADMIN".equals(role)) {
            adminService.save(new Admin(user));
        }
        return "Successfully";
    }

    @Transactional
    @Override
    public User currentUser() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username;
        if (principal instanceof UserDetails) {
            username = ((UserDetails) principal).getUsername();
        } else {
            username = principal.toString();
        }
        return userRepository.findUserByEmail(username);
    }

    @Transactional
    @Override
    public String getCurrentUser() {
        return currentUser().toString();
    }

    @Transactional
    @Override
    public User findUserByEmail(String email) {
        userValidator.validateEmail(email);
        return userRepository.findUserByEmail(email);
    }

    @Transactional
    @Override
    public User getUserFromOptional(int id) {
        Optional<User> userOptional = userRepository.findById(id);
        if (userOptional.isPresent()) {
            return userOptional.get();
        } else throw new RuntimeException("User with such id doesn't exist");
    }

    private String getEncodedPassword(String password)
    {
        return new BCryptPasswordEncoder(12).encode(password);
    }
}
