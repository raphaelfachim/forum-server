package fachim.raphael.spring.learn.mongo.services.user;

import fachim.raphael.spring.learn.mongo.domain.User;
import fachim.raphael.spring.learn.mongo.dto.shared.exception.GenericServerError;
import fachim.raphael.spring.learn.mongo.dto.user.Login;
import fachim.raphael.spring.learn.mongo.dto.user.UserInput;
import fachim.raphael.spring.learn.mongo.dto.user.UserOutput;
import fachim.raphael.spring.learn.mongo.infra.mapper.UserMapper;
import fachim.raphael.spring.learn.mongo.infra.messages.error.ErrorMessage;
import fachim.raphael.spring.learn.mongo.infra.repository.relational.IUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class UserService {

    @Autowired
    private IUserRepository repository;

    public UserOutput create(UserInput userInput) {
        User newUser = repository.save(UserMapper.userInputToUser(userInput));
        return UserMapper.userToUserOutput(newUser);
    }

    public List<UserOutput> all(){
        return UserMapper.usersToUserOutputList(repository.findAll());
    }

    public ResponseEntity<?> login(Login login) {
        Optional<User> _user;
        if (login.username().contains("@")){
            _user = repository.findByEmailAndPassword(login.username(), login.password());
        } else {
            _user = repository.findByUserAndPassword(login.username(), login.password());
        }

        if(_user.isEmpty()) {
            return ResponseEntity.badRequest().body(new GenericServerError(ErrorMessage.ERR4));
        }

        User user = _user.get();

        return ResponseEntity.ok(UserMapper.userToUserOutput(user));
    }
}
