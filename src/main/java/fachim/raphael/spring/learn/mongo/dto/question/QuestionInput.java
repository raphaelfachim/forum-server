package fachim.raphael.spring.learn.mongo.dto.question;

import java.util.Map;
import java.util.Optional;

public record QuestionInput(Long userId, String title, String content, Optional<Map<String, String>> additionalContent) {
}
