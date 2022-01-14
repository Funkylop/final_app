package com.hramyko.finalapp.service;

public interface CommentService {
    String findAllCommentsOfUser(int idUser);
    String findAllCommentsOfPost(int idPost);
    String findComment(int id);
    void saveComment(String jsonString);
    void updateComment(int id, String jsonString);
    void approveComment(int id);
    void destroyComment(int id);
    String findAllUnapprovedComments();
}
