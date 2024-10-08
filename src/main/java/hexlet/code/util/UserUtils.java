package hexlet.code.util;

import hexlet.code.model.User;
import hexlet.code.repositories.UserRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class UserUtils {
    private final UserRepository userRepository;

    public UserUtils(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User getCurrentUser() {
        var authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            return null;
        }
        var email = authentication.getName();
        return userRepository.findByEmail(email).get();
    }

    public boolean checkUserPermission(Long id) {
        var userEmail = userRepository.findById(id).get().getEmail();
        var authentication = SecurityContextHolder.getContext().getAuthentication();
        return userEmail.equals(authentication.getName());
    }
}
