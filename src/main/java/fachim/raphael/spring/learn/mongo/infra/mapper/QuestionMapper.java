package fachim.raphael.spring.learn.mongo.infra.mapper;

import fachim.raphael.spring.learn.mongo.domain.Question;
import fachim.raphael.spring.learn.mongo.dto.question.QuestionInput;
import fachim.raphael.spring.learn.mongo.dto.question.QuestionOutput;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

public class QuestionMapper {

    public static Question questionInputToQuestion(QuestionInput questionInput) {
        Question question = new Question(questionInput.userId(), questionInput.title(), questionInput.content());

        if(!questionInput.additionalContent().isEmpty()) {
            question.setAdditionalContent(questionInput.additionalContent().get());
        }
        return question;
    }

    public static QuestionOutput questionToQuestionOutput(Question question){

        Optional<Map<String, String>> _additionalContent = Optional.of(question.getAdditionalContent());

        return new QuestionOutput(
            question.getId(),
            question.getUserId(),
            question.getTitle(),
            question.getContent(),
            _additionalContent,
            question.getVotesUp().size(),
            question.getVotesDown().size()
        );
    }

    public static List<QuestionOutput> questionsToQuestionOutputList(List<Question> questions){
        return questions.stream().map(QuestionMapper::questionToQuestionOutput).collect(Collectors.toList());
    }
}
