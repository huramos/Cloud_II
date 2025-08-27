package cl.duoc.dsy2207.microservicebff.controller;

import cl.duoc.dsy2207.microservicebff.model.User;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/users")
public class UserController {
    private final Map<Long, User> userStore = new HashMap<>(); // Almacén temporal

    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody User user) {
        if (user.getId() == null) {
            return ResponseEntity.badRequest().build();
        }
        userStore.put(user.getId(), user);
        return ResponseEntity.ok(user);
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getUser(@PathVariable Long id) {
        User user = userStore.get(id);
        return user != null ? ResponseEntity.ok(user) : ResponseEntity.notFound().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<User> updateUser(@PathVariable Long id, @RequestBody User user) {
        if (userStore.containsKey(id)) {
            user.setId(id);
            userStore.put(id, user);
            return ResponseEntity.ok(user);
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        return userStore.remove(id) != null ? ResponseEntity.ok().build() : ResponseEntity.notFound().build();
    }
}