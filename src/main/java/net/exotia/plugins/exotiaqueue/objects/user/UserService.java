package net.exotia.plugins.exotiaqueue.objects.user;

import org.bukkit.entity.Player;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class UserService {
    private final Set<User> users = new HashSet<>();

    public Set<User> getUsers() {
        return this.users;
    }

    public User getUser(Player player) {
        return this.users.stream().filter(user -> user.getUniqueId().equals(player.getUniqueId())).findFirst().orElse(null);
    }
    public void addUser(User user) {
        this.users.add(user);
    }
    public void removePlayer(UUID uuid) {
        this.users.removeIf(user -> user.getUniqueId() == uuid);
    }
    public void removePlayer(User user) {
        this.users.remove(user);
    }
    public boolean contains(Player player) {
        return this.users.stream().anyMatch(user -> user.getUniqueId() == player.getUniqueId());
    }
}
