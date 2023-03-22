package fachim.raphael.spring.learn.mongo.infra.repository.relational;

import fachim.raphael.spring.learn.mongo.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface IUserRepository extends JpaRepository<User, Long> {

    Optional<User> findByUserAndPassword(String user, String password);

    Optional<User> findByEmailAndPassword(String email, String password);
}
