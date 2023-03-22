package fachim.raphael.spring.learn.mongo.services.question;

import fachim.raphael.spring.learn.mongo.domain.Question;
import fachim.raphael.spring.learn.mongo.dto.question.QuestionInput;
import fachim.raphael.spring.learn.mongo.dto.question.QuestionOutput;
import fachim.raphael.spring.learn.mongo.dto.question.Vote;
import fachim.raphael.spring.learn.mongo.dto.shared.exception.GenericServerError;
import fachim.raphael.spring.learn.mongo.infra.mapper.QuestionMapper;
import fachim.raphael.spring.learn.mongo.infra.messages.error.ErrorMessage;
import fachim.raphael.spring.learn.mongo.infra.repository.document.IQuestionRespository;
import fachim.raphael.spring.learn.mongo.infra.repository.relational.IUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class QuestionService {

    @Autowired
    private IQuestionRespository questionRespository;

    @Autowired
    private IUserRepository userRepository;

    public QuestionOutput create(QuestionInput questionInput) {
        Question question = questionRespository.save(
                QuestionMapper.questionInputToQuestion(questionInput)
        );

        return QuestionMapper.questionToQuestionOutput(question);
    }

    public ResponseEntity<?> allByUserId(Long userId) {
        Optional<List<Question>> _questions =
                questionRespository.findAllByUserId(userId);
        if(_questions.isEmpty() || _questions.get().isEmpty()) return ResponseEntity.status(204).build();

        return ResponseEntity
                .ok(QuestionMapper.questionsToQuestionOutputList(_questions.get()));
    }

    public ResponseEntity<?> voteUp(Vote vote){
        Optional<Question> _question = questionRespository.findById(vote.questionId());

        if(_question.isEmpty()) return ResponseEntity.status(204).body(
                new GenericServerError(ErrorMessage.ERR1)
        );

        Question question = _question.get();

        if(question.getVotesUp().contains(vote.userId())) {
            return ResponseEntity.status(204).body(new GenericServerError(ErrorMessage.ERR2));
        } else {
            if(question.getVotesDown().contains(vote.userId())){
                int index = question.getVotesDown().indexOf(vote.userId());
                question.getVotesDown().remove(index);
            }
            question.getVotesUp().add(vote.userId());
        }

        questionRespository.save(question);

        return ResponseEntity.ok(QuestionMapper.questionToQuestionOutput(question));
    }

    public ResponseEntity<?> voteDown(Vote vote){
        Optional<Question> _question = questionRespository.findById(vote.questionId());

        if(_question.isEmpty()) return ResponseEntity.status(204).body(
                new GenericServerError(ErrorMessage.ERR1)
        );

        Question question = _question.get();

        if(question.getVotesDown().contains(vote.userId())) {
            return ResponseEntity.status(204).body(new GenericServerError(ErrorMessage.ERR2));
        } else {
            if(question.getVotesUp().contains(vote.userId())){
                int index = question.getVotesUp().indexOf(vote.userId());
                question.getVotesUp().remove(index);
            }
            question.getVotesDown().add(vote.userId());
        }

        questionRespository.save(question);

        return ResponseEntity.ok(QuestionMapper.questionToQuestionOutput(question));
    }

}
