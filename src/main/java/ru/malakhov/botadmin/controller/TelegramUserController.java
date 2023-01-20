package ru.malakhov.botadmin.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.malakhov.botadmin.entity.TelegramUser;
import ru.malakhov.botadmin.service.TelegramUserService;

import java.util.List;

@RestController
@AllArgsConstructor
public class TelegramUserController {
    private final TelegramUserService userService;

    @GetMapping("/user/{id}")
    public ResponseEntity<TelegramUser> findById(@PathVariable long id) {
        var user = userService.findById(id);
        return user.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @RequestMapping(value = "/user", method = RequestMethod.GET)
    public ResponseEntity<TelegramUser> findByUserName(@RequestParam(value = "userName") String userName) {
        var user = userService.findByUsername(userName);
        return user.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/users")
    public ResponseEntity<List<TelegramUser>> findAll() {
        List<TelegramUser> users = userService.findAll();

        return (users == null || users.isEmpty()) ?
                ResponseEntity.notFound().build() :
                ResponseEntity.ok(users);
    }
}