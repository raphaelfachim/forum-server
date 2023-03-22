package fachim.raphael.spring.learn.mongo.dto.question;

import java.util.Map;
import java.util.Optional;

public record QuestionOutput(
        String id,
        Long userId,
        String username,
        String title,
        String content,
        String createdAt,
        String lastEdited,
        Optional<Map<String, String>> additionalContent,
        Integer votesUp,
        Integer votesDown) {
}
