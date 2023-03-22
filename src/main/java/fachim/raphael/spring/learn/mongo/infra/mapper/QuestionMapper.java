package fachim.raphael.spring.learn.mongo.infra.mapper;

import fachim.raphael.spring.learn.mongo.domain.Question;
import fachim.raphael.spring.learn.mongo.dto.question.QuestionInput;
import fachim.raphael.spring.learn.mongo.dto.question.QuestionOutput;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

public class QuestionMapper {

    public static Question questionInputToQuestion(QuestionInput questionInput, String username) {
        Question question = new Question(questionInput.userId(), username, questionInput.title(), questionInput.content());

        if(!questionInput.additionalContent().isEmpty()) {
            question.setAdditionalContent(questionInput.additionalContent().get());
        }
        return question;
    }

    public static QuestionOutput questionToQuestionOutput(Question question, String username){

        Optional<Map<String, String>> _additionalContent = Optional.of(question.getAdditionalContent());

        return new QuestionOutput(
            question.getId(),
            question.getUserId(),
            username,
            question.getTitle(),
            question.getContent(),
            question.getCreatedAt().toString(),
            question.getLastEdited() != null ? question.getLastEdited().toString() : null,
            _additionalContent,
            question.getVotesUp().size(),
            question.getVotesDown().size()
        );
    }
}
