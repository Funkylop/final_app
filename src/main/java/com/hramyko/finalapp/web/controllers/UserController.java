package com.hramyko.finalapp.web.controllers;

import com.hramyko.finalapp.persistence.entity.User;
import com.hramyko.finalapp.service.TraderService;
import com.hramyko.finalapp.service.UserService;
import com.hramyko.finalapp.service.parser.JsonParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RestController
@RequestMapping("api/users")
public class UserController {

    private final UserService userService;
    private final TraderService traderService;

    @Autowired
    public UserController(UserService userService, TraderService traderService) {
        this.userService = userService;
        this.traderService = traderService;
    }

    @GetMapping()
    @PreAuthorize("hasAnyAuthority('user.write', 'user.delete')")
    public String index() {
        return userService.findAll();
    }

    @GetMapping("rating")
    public String showTop() {
        return traderService.getTopTraders().toString();
    }

    @PreAuthorize("hasAnyAuthority('user.read', 'user.write', 'user.delete')")
    @GetMapping("my_account")
    public String show() {
        return userService.getCurrentUser();
    }

    @GetMapping("{id}")
    @PreAuthorize("hasAuthority('user.delete')")
    public String show(@PathVariable("id") int id) {
        return userService.findUserById(id).toString();
    }

    @GetMapping("/traders")
    @PreAuthorize("hasAnyAuthority('user.read', 'user.write', 'user.delete')")
    public String showAllTraders() {
        return userService.findAllTraders();
    }

    //
    @PatchMapping("my_account")
    @PreAuthorize("hasAnyAuthority('user.read', 'user.write', 'user.delete')")
    public String update(@RequestBody String jsonString) {
        User user = (User) JsonParser.getObjectFromJson(jsonString, User.class.getName());
        userService.updateUser(user);
        return "Updated successfully";
    }

    @PostMapping("update/{id}")
    @PreAuthorize("hasAnyAuthority('user.delete')")
    public String updateRole(@PathVariable("id") int id, @RequestBody String jsonString) {
        return userService.updateUserRole(id, jsonString);
    }

    @DeleteMapping("my_account")
    @PreAuthorize("hasAnyAuthority('user.read', 'user.delete', 'user.write')")
    public void delete(HttpServletResponse response) {
        userService.destroyUser(userService.currentUser().getId());
        try {
            response.sendRedirect("/my/logout");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @GetMapping("rating/{id}")
    @PreAuthorize("hasAnyAuthority('user.read', 'user.write', 'user.delete')")
    public String showTraderRating(@PathVariable("id") int id) {
        User user = userService.getUserFromOptional(id);
        double rating = traderService.showTraderRating(user.getId());
        return userService.findUserById(id) + "\n" + "Rating: " + rating;
    }
}