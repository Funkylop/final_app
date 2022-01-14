package com.hramyko.finalapp.service;

import com.hramyko.finalapp.persistence.entity.Game;

import java.util.Optional;

public interface GameService {
    String findGames();
    String findGameById(int id);
    void saveGame(String jsonString);
    void updateGame(int id, String jsonString);
    void destroyGame(int id);

    Game getGameFromOptional(Optional<Game> game);
}
