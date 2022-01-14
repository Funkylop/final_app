package com.hramyko.finalapp.web.controllers;

import com.hramyko.finalapp.dto.AuthenticationRequestDto;
import com.hramyko.finalapp.persistence.entity.CommonUser;
import com.hramyko.finalapp.persistence.entity.ConfirmationToken;
import com.hramyko.finalapp.persistence.entity.Status;
import com.hramyko.finalapp.persistence.entity.User;
import com.hramyko.finalapp.service.CommonUserService;
import com.hramyko.finalapp.service.ConfirmationTokenService;
import com.hramyko.finalapp.service.UserService;
import com.hramyko.finalapp.service.impl.EmailSenderServiceImpl;
import com.hramyko.finalapp.service.parser.JsonParser;
import com.hramyko.finalapp.service.security.jwt.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;


@RestController
@RequestMapping("api")
public class AuthController {

    private final CommonUserService commonUserService;
    private final UserService userService;
    private final ConfirmationTokenService confirmationTokenService;
    private final EmailSenderServiceImpl emailSenderServiceImpl;
    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;
    @Autowired
    public AuthController(CommonUserService commonUserService, UserService userService,
                          ConfirmationTokenService confirmationTokenService, EmailSenderServiceImpl emailSenderServiceImpl,
                          AuthenticationManager authenticationManager, JwtTokenProvider jwtTokenProvider) {
        this.userService = userService;
        this.commonUserService = commonUserService;
        this.confirmationTokenService = confirmationTokenService;
        this.emailSenderServiceImpl = emailSenderServiceImpl;
        this.authenticationManager = authenticationManager;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @PostMapping(value = "/registration")
    public String saveUser(@RequestBody String jsonString) {
        CommonUser user = (CommonUser) JsonParser.getObjectFromJson(jsonString, CommonUser.class.getName());
        CommonUser existingUser;
        if (user != null) {
            try {
                existingUser = (CommonUser) userService.findUserByEmail(user.getEmail());
            } catch (EmptyResultDataAccessException e) {
                existingUser = null;
            }
            if (existingUser == null) {
                user.setStatus(Status.valueOf("BANNED"));
                commonUserService.save(user);
                emailSenderServiceImpl.registrationConfirmationMessage(user);
            } else if (existingUser.getStatus().equals(Status.BANNED)) {
                emailSenderServiceImpl.registrationConfirmationMessage(user);
            } else {
                return "Such user already exists";
            }
            return "To register successfully redirect to url, that we have send to your email";
        } else {
            return "Error";
        }
    }

    @PostMapping(value = "login")
    public ResponseEntity login(@RequestBody AuthenticationRequestDto requestDto) {
        try {
            String username = requestDto.getUsername();
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, requestDto.getPassword()));
            User user = userService.findUserByEmail(username);

            if(user == null) {
                throw new UsernameNotFoundException("User with email: " + username + " not found");
            }
            String token = jwtTokenProvider.createToken(username, user.getRole());

            Map<Object, Object> response = new HashMap<>();
            response.put("username", username);
            response.put("token", token);

            return ResponseEntity.ok(response);
        } catch (AuthenticationException e) {
            throw new BadCredentialsException("Invalid username or password");
        }
    }

    @PostMapping("/auth/forgot_password")
    public String forgotPassword(@RequestBody String json) {
        String email = JsonParser.getInfoFromJson(json, "email");
        User user = userService.findUserByEmail(email);
        emailSenderServiceImpl.resetPasswordMessage(user);
        return "Please check your email and reset your password!";
    }

    @PatchMapping("/auth/reset/{token}")
    public String reset(@PathVariable("token") String token, @RequestBody String json) {
        ConfirmationToken confirmationToken = confirmationTokenService.findByConfirmationToken(token);
        String password = JsonParser.getInfoFromJson(json, "password");
        if (confirmationToken != null) {
            User user = userService.getUserFromOptional(confirmationToken.getUserId());
            user.setPassword(password);
            confirmationTokenService.deleteConfirmationToken(user, confirmationToken.getTokenType());
            userService.updateUserPassword(user.getId(), user);
        }
        return "You changed your password successfully";
    }

    @PostMapping("/registration/confirm/{token}")
    public void confirmAccount(@PathVariable("token") String token, HttpServletResponse response) {
        ConfirmationToken confirmationToken = confirmationTokenService.findByConfirmationToken(token);
        if (confirmationToken != null) {
            User user = userService.getUserFromOptional(confirmationToken.getUserId());
            confirmationTokenService.deleteConfirmationToken(user, confirmationToken.getTokenType());
            userService.updateUserStatus(user.getEmail(), Status.ACTIVE.toString());
        }
        try {
            response.sendRedirect("/users/my_account");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @GetMapping("my/logout")
    @PreAuthorize("hasAnyAuthority('user.read', 'user.write', 'user.delete')")
    public String logout() {
        SecurityContextHolder.clearContext();
        return "You successfully logged out";
    }
}