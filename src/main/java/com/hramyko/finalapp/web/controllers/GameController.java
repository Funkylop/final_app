package com.hramyko.finalapp.web.controllers;

import com.hramyko.finalapp.service.GameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/games")
public class GameController {

    private final GameService gameService;

    @Autowired
    public GameController(GameService gameService) {
        this.gameService = gameService;
    }

    @GetMapping
    @PreAuthorize("hasAnyAuthority('user.read', 'user.write', 'user.delete')")
    public String index() {
        return gameService.findGames();
    }

    @GetMapping("{id}")
    @PreAuthorize("hasAnyAuthority('user.read', 'user.write', 'user.delete')")
    public String show(@PathVariable("id") int id) {
        return gameService.findGameById(id);
    }

    @PostMapping
    @PreAuthorize("hasAnyAuthority('user.delete')")
    public String create(@RequestBody String jsonString) {
        gameService.saveGame(jsonString);
        return "Game has been created successfully";
    }

    @PatchMapping("{id}")
    @PreAuthorize("hasAuthority('user.delete')")
    public String update(@PathVariable("id") int id, @RequestBody String jsonString) {
        gameService.updateGame(id, jsonString);
        return "Game has been updated successfully";
    }

    @DeleteMapping("{id}")
    @PreAuthorize("hasAuthority('user.delete')")
    public String delete(@PathVariable("id") int id) {
        gameService.destroyGame(id);
        return "Game has been deleted successfully";
    }
}