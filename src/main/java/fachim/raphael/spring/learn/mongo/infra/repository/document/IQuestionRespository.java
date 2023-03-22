package fachim.raphael.spring.learn.mongo.infra.repository.document;

import fachim.raphael.spring.learn.mongo.domain.Question;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface IQuestionRespository extends MongoRepository<Question, String> {

    Optional<List<Question>> findAllByUserId(Long userId);
}
