package com.hramyko.finalapp.service;

import com.hramyko.finalapp.persistence.entity.Post;
import org.springframework.transaction.annotation.Transactional;

public interface PostService {
    String findUserPosts(int idUser);
    String findAllPosts();
    void createPost(String jsonString);
    void deletePost(int id, int idUser);
    void deletePost(int id);
    void updatePost(int id, String jsonString, int idUser);
    void updatePost(int id, String jsonString);

    @Transactional
    Post getPostFromOptional(int id);
}
