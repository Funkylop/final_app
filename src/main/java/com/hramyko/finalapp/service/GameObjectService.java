package com.hramyko.finalapp.service;


import com.hramyko.finalapp.persistence.entity.GameObject;

import java.util.List;

public interface GameObjectService {
    List<GameObject> findAllGameObjects();
    List<GameObject> findAllUserGameObjects(int id);
    List<GameObject> findAllGameObjectsOfGame(Integer[] gameIds);
    void saveGameObject(String jsonString);
    void updateGameObject(int id, String string);
    void updateGameObjectStatus(int id,String status);
    void destroyGameObject(int id, int idUser);
    boolean isOwner(int userId, GameObject gameObject);
}
