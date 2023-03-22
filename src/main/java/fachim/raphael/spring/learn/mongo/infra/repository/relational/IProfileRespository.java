package fachim.raphael.spring.learn.mongo.infra.repository.relational;

import fachim.raphael.spring.learn.mongo.domain.Profile;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IProfileRespository extends JpaRepository<Profile, Long> {
}
