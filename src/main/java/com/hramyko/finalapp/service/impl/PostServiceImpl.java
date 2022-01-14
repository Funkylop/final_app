package com.hramyko.finalapp.service.impl;

import com.hramyko.finalapp.persistence.entity.GameObject;
import com.hramyko.finalapp.persistence.entity.Post;
import com.hramyko.finalapp.persistence.repository.PostRepository;
import com.hramyko.finalapp.service.GameObjectService;
import com.hramyko.finalapp.service.PostService;
import com.hramyko.finalapp.service.parser.JsonParser;
import com.hramyko.finalapp.service.validator.PostValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;
    private final PostValidator postValidator;
    private final GameObjectService gameObjectService;

    @Autowired
    PostServiceImpl(PostRepository postRepository, PostValidator postValidator,
                    GameObjectService gameObjectService) {
        this.postRepository = postRepository;
        this.postValidator = postValidator;
        this.gameObjectService = gameObjectService;
    }

    @Transactional
    @Override
    public String findUserPosts(int idUser) {
        List<GameObject> gameObjects = gameObjectService.findAllUserGameObjects(idUser);
        return postRepository.findAllByGameObjectIn(gameObjects).toString();
    }

    @Transactional
    @Override
    public String findAllPosts() {
        return postRepository.findAll().toString();
    }

    @Transactional
    @Override
    public void createPost(String jsonString) {
        Post post = (Post) JsonParser.getObjectFromJson(jsonString, Post.class.getName());
        if (post != null) {
            postValidator.validate(post);
            postRepository.save(post);
        } else throw new RuntimeException("Error of saving post");
    }

    @Transactional
    @Override
    public void deletePost(int id, int idUser) {
        if (isOwner(idUser, id)) {
            postRepository.deleteById(id);
        }
    }

    @Transactional
    @Override
    public void updatePost(int id, String jsonString, int idUser) {
        if (isOwner(idUser, id)) {
            double price = Double.parseDouble(JsonParser.getInfoFromJson(jsonString, "price"));
            Post post = getPostFromOptional(id);
            post.setPrice(price);
            postRepository.save(post);
        }
    }

    @Transactional
    @Override
    public void deletePost(int id) {
        postRepository.deleteById(id);
    }

    @Transactional
    @Override
    public void updatePost(int id, String jsonString) {
        Post newPost = (Post) JsonParser.getObjectFromJson(jsonString, Post.class.getName());
        Post post = getPostFromOptional(id);
        if (newPost != null) {
            if (newPost.getPrice() != 0) {
                postValidator.validatePrice(newPost.getPrice());
                post.setPrice(newPost.getPrice());
                postRepository.save(post);
            }
        } else throw new RuntimeException("Error of updating post");
    }

    @Transactional
    @Override
    public Post getPostFromOptional(int id) {
        Optional<Post> optionalPost = postRepository.findById(id);
        if (optionalPost.isPresent()) {
            return optionalPost.get();
        } else throw new RuntimeException("Post with such id doesn't exist");
    }

    private boolean isOwner(int idUser, int id) {
        Post post = getPostFromOptional(id);
        if (post.getGameObject().getUser().getId() == idUser) {
            return true;
        } else throw new RuntimeException("You aren't owner of this post");
    }
}