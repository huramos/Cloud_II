package cl.duoc.dsy2207.microservicebff.controller;

import cl.duoc.dsy2207.microservicebff.model.User;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpEntity;

@RestController
@RequestMapping("/users")
public class UserController {
    private final RestTemplate restTemplate;
    private final String usuariosUrl = "http://usuarios:8081/api/users";
    private final String functionsUrl = "http://localhost:7071/api/";

    public UserController(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody User user) {
        return restTemplate.postForEntity(usuariosUrl, user, User.class);
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getUser(@PathVariable Long id) {
        return restTemplate.getForEntity(functionsUrl + "ReadDeleteUser?id=" + id, User.class);
    }

    @PutMapping("/{id}")
    public ResponseEntity<User> updateUser(@PathVariable Long id, @RequestBody User user) {
        return restTemplate.exchange(functionsUrl + "CreateUpdateUser?id=" + id, HttpMethod.PUT, new HttpEntity<>(user), User.class);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        restTemplate.delete(functionsUrl + "ReadDeleteUser?id=" + id);
        return ResponseEntity.ok().build();
    }
}