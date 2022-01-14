package com.hramyko.finalapp.persistence.repository;

import com.hramyko.finalapp.persistence.entity.GameObject;
import com.hramyko.finalapp.persistence.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post, Integer> {
    List<Post> findAllByGameObjectIn(List<GameObject> gameObjects);
}
