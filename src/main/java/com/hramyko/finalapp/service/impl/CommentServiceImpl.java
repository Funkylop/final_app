package com.hramyko.finalapp.service.impl;

import com.hramyko.finalapp.persistence.entity.Comment;
import com.hramyko.finalapp.persistence.repository.CommentRepository;
import com.hramyko.finalapp.service.CommentService;
import com.hramyko.finalapp.service.PostService;
import com.hramyko.finalapp.service.UserService;
import com.hramyko.finalapp.service.parser.JsonParser;
import com.hramyko.finalapp.service.validator.CommentValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;
    private final CommentValidator commentValidator;
    private final UserService userService;
    private final PostService postService;

    @Autowired
    CommentServiceImpl(CommentRepository commentRepository, CommentValidator commentValidator,
                       UserService userService, PostService postService) {
        this.commentRepository = commentRepository;
        this.commentValidator = commentValidator;
        this.userService = userService;
        this.postService = postService;
    }

    @Transactional
    @Override
    public String findAllCommentsOfUser(int idUser) {
        return commentRepository.findCommentsByAuthorAndApprovedTrue(userService.getUserFromOptional(idUser)).toString();
    }

    @Transactional
    @Override
    public String findAllCommentsOfPost(int idPost) {
        return commentRepository.findCommentsByPostAndApprovedTrue(postService.getPostFromOptional(idPost)).toString();
    }

    @Transactional
    @Override
    public String findComment(int id) {
        Optional<Comment> optionalComment = commentRepository.findById(id);
        if (optionalComment.isPresent()) {
            return optionalComment.get().toString();
        } else throw new RuntimeException("Comment with such id doesn't exist");
    }

    @Transactional
    @Override
    public void saveComment(String jsonString) {
        Comment comment = (Comment) JsonParser.getObjectFromJson(jsonString, Comment.class.getName());
        if (comment != null) {
            commentValidator.validateMark(comment.getMark());
            commentValidator.validateMessage(comment.getMessage());
            comment.setAuthor(userService.currentUser());
            commentRepository.save(comment);
        } else throw new RuntimeException("Error of saving comment");
    }

    @Transactional
    @Override
    public void updateComment(int id, String jsonString) {
        Comment newComment = (Comment) JsonParser.getObjectFromJson(jsonString, Comment.class.getName());
        if (newComment == null) throw new RuntimeException("Error of updating comment");
        Optional<Comment> optionalComment = commentRepository.findById(id);
        Comment comment;
        if (optionalComment.isPresent()) {
            comment = optionalComment.get();
        } else throw new RuntimeException("Comment with such id doesn't exist");
        if (newComment.getMessage() != null) {
            commentValidator.validateMessage(newComment.getMessage());
            comment.setMessage(newComment.getMessage());
        }
        if (newComment.getMark() != 0) {
            commentValidator.validateMark(newComment.getMark());
            comment.setMark(newComment.getMark());
        }
        commentRepository.save(comment);
    }

    @Transactional
    @Override
    public void approveComment(int id) {
        Optional<Comment> comment = commentRepository.findById(id);
        if (comment.isPresent()) {
            comment.get().setApproved(true);
            commentRepository.save(comment.get());
        } else throw new RuntimeException("Comment with such id doesn't exist");
    }

    @Transactional
    @Override
    public void destroyComment(int id) {
        commentRepository.deleteById(id);
    }

    @Transactional
    @Override
    public String findAllUnapprovedComments() {
        return commentRepository.findCommentsByApprovedFalse().toString();
    }
}
