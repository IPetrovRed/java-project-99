package hexlet.code.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<hexlet.code.model.User, Long> {
    Optional<hexlet.code.model.User> findByEmail(String email);
}
