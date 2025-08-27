package cl.duoc.dsy2207.usuarios.service;

import cl.duoc.dsy2207.usuarios.model.User;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class UserService {
    private final Map<Long, User> userStore = new HashMap<>();

    public User createUser(User user) {
        if (user.getId() == null) throw new IllegalArgumentException("ID cannot be null");
        userStore.put(user.getId(), user);
        return user;
    }

    public User getUser(Long id) {
        return userStore.get(id);
    }

    public User updateUser(Long id, User user) {
        if (userStore.containsKey(id)) {
            user.setId(id);
            userStore.put(id, user);
            return user;
        }
        return null;
    }

    public boolean deleteUser(Long id) {
        return userStore.remove(id) != null;
    }
}