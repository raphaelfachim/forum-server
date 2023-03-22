package fachim.raphael.spring.learn.mongo.infra.mapper;

import fachim.raphael.spring.learn.mongo.domain.User;
import fachim.raphael.spring.learn.mongo.dto.user.UserInput;
import fachim.raphael.spring.learn.mongo.dto.user.UserOutput;

import java.util.List;
import java.util.stream.Collectors;

public class UserMapper {

    public static UserOutput userToUserOutput(User user){
        return new UserOutput(user.getId(), user.getEmail(), user.getUser(), user.getActive());
    }

    public static User userInputToUser(UserInput userInput){
        return new User(userInput.email(), userInput.password());
    }

    public static List<UserOutput> usersToUserOutputList(List<User> users){
        return users.stream().map(UserMapper::userToUserOutput).collect(Collectors.toList());
    }
}
