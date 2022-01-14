package com.hramyko.finalapp.web.controllers;

import com.hramyko.finalapp.service.GameObjectService;
import com.hramyko.finalapp.service.UserService;
import com.hramyko.finalapp.service.parser.JsonParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/objects")
public class GameObjectController {

    private final GameObjectService gameObjectService;
    private final UserService userService;

    @Autowired
    public GameObjectController(GameObjectService gameObjectService, UserService userService) {
        this.gameObjectService = gameObjectService;
        this.userService = userService;
    }

    @GetMapping()
    @PreAuthorize("hasAnyAuthority('user.read', 'user.write', 'user.delete')")
    public String index(@RequestParam @Nullable Integer[] games) {
        return gameObjectService.findAllGameObjectsOfGame(games).toString();
    }

    @GetMapping("/my")
    @PreAuthorize("hasAnyAuthority('user.write', 'user.delete')")
    public String showTraderObjects() {
        return gameObjectService.findAllUserGameObjects(userService.currentUser().getId()).toString();
    }

    @PostMapping()
    @PreAuthorize("hasAnyAuthority('user.write', 'user.delete')")
    public String create(@RequestBody String jsonString) {
        gameObjectService.saveGameObject(jsonString);
        return "Game object has been created successfully";
    }

    @PatchMapping("{id}")
    @PreAuthorize("hasAnyAuthority('user.write', 'user.delete')")
    public String update(@RequestBody String jsonString, @PathVariable int id) {
        gameObjectService.updateGameObject(id, jsonString);
        return "Game object has been updated successfully";
    }

    @PatchMapping("{id}/status")
    @PreAuthorize("hasAnyAuthority('user.write', 'user.delete')")
    public String updateGameObjectStatus(@RequestBody String jsonString, @PathVariable int id) {
        String status = JsonParser.getInfoFromJson(jsonString, "status");
        gameObjectService.updateGameObjectStatus(id, status);
        return "Game object status has been updated successfully";
    }

    @DeleteMapping("{id}")
    @PreAuthorize("hasAuthority('user.write')")
    public String delete(@PathVariable int id) {
        gameObjectService.destroyGameObject(id, userService.currentUser().getId());
        return "Game object has been deleted successfully";
    }

    @DeleteMapping("admin/{idObject}/user/{idUser}")
    @PreAuthorize("hasAuthority('user.delete')")
    public String delete(@PathVariable("idObject") int idObject, @PathVariable("idUser") int idUser) {
        gameObjectService.destroyGameObject(idObject, idUser);
        return "Game object has been deleted successfully";
    }
}