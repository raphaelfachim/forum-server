package fachim.raphael.spring.learn.mongo.services.question;

import fachim.raphael.spring.learn.mongo.domain.Question;
import fachim.raphael.spring.learn.mongo.domain.User;
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

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class QuestionService {

    @Autowired
    private IQuestionRespository questionRespository;

    @Autowired
    private IUserRepository userRepository;

    public QuestionOutput create(QuestionInput questionInput) {
        Optional<User> _user = userRepository.findById(questionInput.userId());
        if(_user.isEmpty()) throw new RuntimeException(ErrorMessage.ERR3);

        Question question = questionRespository.save(
                QuestionMapper.questionInputToQuestion(questionInput, _user.get().getUser())
        );

        return QuestionMapper.questionToQuestionOutput(question, _user.get().getUser());
    }

    public ResponseEntity<?> allByUserId(Long userId) {
        Optional<User> _user = userRepository.findById(userId);
        if(_user.isEmpty()) return ResponseEntity.status(204).build();

        Optional<List<Question>> _questions =
                questionRespository.findAllByUserId(userId);
        if(_questions.isEmpty() || _questions.get().isEmpty()) return ResponseEntity.status(204).build();

        List<QuestionOutput> questions = new ArrayList<>();
        for(Question q : _questions.get()){
            questions.add(QuestionMapper.questionToQuestionOutput(q, _user.get().getUser()));
        }

        return ResponseEntity.ok(questions);
    }

    public ResponseEntity<?> voteUp(Vote vote){
        Optional<Question> _question = questionRespository.findById(vote.questionId());

        if(_question.isEmpty()) return ResponseEntity.status(204).body(
                new GenericServerError(ErrorMessage.ERR1)
        );

        Question question = _question.get();

        Optional<User> _user = userRepository.findById(question.getUserId());
        if(_user.isEmpty()) return ResponseEntity.status(204).body(
                new GenericServerError(ErrorMessage.ERR3)
        );

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

        return ResponseEntity.ok(QuestionMapper.questionToQuestionOutput(question, _user.get().getUser()));
    }

    public ResponseEntity<?> voteDown(Vote vote){
        Optional<Question> _question = questionRespository.findById(vote.questionId());

        if(_question.isEmpty()) return ResponseEntity.status(204).body(
                new GenericServerError(ErrorMessage.ERR1)
        );

        Question question = _question.get();

        Optional<User> _user = userRepository.findById(question.getUserId());
        if(_user.isEmpty()) return ResponseEntity.status(204).body(
                new GenericServerError(ErrorMessage.ERR3)
        );

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

        return ResponseEntity.ok(QuestionMapper.questionToQuestionOutput(question, _user.get().getUser()));
    }

    public ResponseEntity<?> allPaginated(Integer amount) {
        List<Question> questions = questionRespository.findAll();

        if(questions.isEmpty()) {
            return ResponseEntity.status(204).build();
        }

        List<QuestionOutput> outputList = new ArrayList<>();
        for(Question question : questions) {
            Optional<User> _user = userRepository.findById(question.getUserId());
            outputList.add(
              QuestionMapper.questionToQuestionOutput(question, _user.get().getUser())
            );
        }

        return ResponseEntity.ok(outputList);
    }

    public ResponseEntity<?> findById(String id) {
        Optional<Question> _question = questionRespository.findById(id);

        if(_question.isEmpty()) {
            return ResponseEntity.status(204).build();
        }

        Question question = _question.get();

        Optional<User> _user = userRepository.findById(question.getUserId());

        String username = !_user.isEmpty() ? _user.get().getUser() : question.getUsername();

        return ResponseEntity.ok(QuestionMapper.questionToQuestionOutput(question, username));
    }

}
