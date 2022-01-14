package com.hramyko.finalapp.web.controllers;

import com.hramyko.finalapp.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/comments")
public class CommentController {
    private final CommentService commentService;

    @Autowired
    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @GetMapping("{id}")
    @PreAuthorize("hasAnyAuthority('user.read', 'user.write', 'user.delete')")
    public String showComment(@PathVariable("id") int id) {
        return commentService.findComment(id);
    }

    @GetMapping("post/{id}")
    @PreAuthorize("hasAnyAuthority('user.read', 'user.write', 'user.delete')")
    public String showAllCommentsOfPost(@PathVariable("id") int idPost) {
        return commentService.findAllCommentsOfPost(idPost);
    }

    @GetMapping("unapproved")
    @PreAuthorize("hasAuthority('user.delete')")
    public String showAllUnapprovedComments() {
        return commentService.findAllUnapprovedComments();
    }

    @GetMapping("user/{id}")
    @PreAuthorize("hasAnyAuthority('user.read', 'user.write', 'user.delete')")
    public String showAllCommentsOfUser(@PathVariable("id") int idUser) {
        return commentService.findAllCommentsOfUser(idUser);
    }

    @PostMapping
    @PreAuthorize("hasAnyAuthority('user.read', 'user.delete')")
    public String create(@RequestBody String jsonString) {
        commentService.saveComment(jsonString);
        return "Comment added successfully";
    }

    @PatchMapping("{id}")
    @PreAuthorize("hasAnyAuthority('user.read', 'user.delete')")
    public String update(@PathVariable("id") int id, @RequestBody String jsonString) {
        commentService.updateComment(id, jsonString);
        return "Comment has been updated successfully";
    }

    @DeleteMapping("{id}")
    @PreAuthorize("hasAnyAuthority('user.read')")
    public String delete(@PathVariable("id") int id) {
        commentService.destroyComment(id);
        return "Comment has been deleted successfully";
    }

    @PatchMapping("approve/{id}")
    @PreAuthorize("hasAnyAuthority('user.delete')")
    public String approveComment(@PathVariable("id") int id) {
        commentService.approveComment(id);
        return "Comment has been approved successfully";
    }
}