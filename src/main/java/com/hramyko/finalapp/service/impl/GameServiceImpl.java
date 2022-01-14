package com.hramyko.finalapp.service.impl;

import com.hramyko.finalapp.persistence.entity.Game;
import com.hramyko.finalapp.persistence.repository.GameRepository;
import com.hramyko.finalapp.service.GameService;
import com.hramyko.finalapp.service.parser.JsonParser;
import com.hramyko.finalapp.service.validator.GameValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class GameServiceImpl implements GameService {

    private final GameRepository gameRepository;
    private final GameValidator gameValidator;

    @Autowired
    GameServiceImpl(GameRepository gameRepository, GameValidator gameValidator) {
        this.gameRepository = gameRepository;
        this.gameValidator = gameValidator;
    }

    @Transactional
    @Override
    public String findGames() {
        return gameRepository.findAll().toString();
    }

    @Transactional
    @Override
    public String findGameById(int id) {
        Optional<Game> optionalGame = gameRepository.findById(id);
        return getGameFromOptional(optionalGame).toString();
    }

    @Transactional
    @Override
    public void saveGame(String jsonString) {
        Game game = (Game) JsonParser.getObjectFromJson(jsonString, Game.class.getName());
        if (game != null) {
            gameValidator.validateTitle(game.getTitle());
            gameRepository.save(game);
        } else throw new RuntimeException("Error of saving");
    }

    @Transactional
    @Override
    public void updateGame(int id, String jsonString) {
        Optional<Game> optionalGame = gameRepository.findById(id);
        Game game = getGameFromOptional(optionalGame);
        Game newGame = (Game) JsonParser.getObjectFromJson(jsonString, Game.class.getName());
        if(newGame != null) {
            gameValidator.validateTitle(newGame.getTitle());
            game.setTitle(newGame.getTitle());
            gameRepository.save(game);
        } else throw new RuntimeException("Error of updating");
    }

    @Transactional
    @Override
    public void destroyGame(int id) {
        gameRepository.deleteById(id);
    }

    @Transactional
    @Override
    public Game getGameFromOptional(Optional<Game> game) {
        if (game.isPresent()) {
            return game.get();
        } else throw new RuntimeException("No such game");
    }
}
