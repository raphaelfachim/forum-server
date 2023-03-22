package fachim.raphael.spring.learn.mongo.controller;

import fachim.raphael.spring.learn.mongo.dto.profile.ProfileInput;
import fachim.raphael.spring.learn.mongo.dto.question.QuestionInput;
import fachim.raphael.spring.learn.mongo.dto.question.Vote;
import fachim.raphael.spring.learn.mongo.dto.user.Login;
import fachim.raphael.spring.learn.mongo.dto.user.UserInput;
import fachim.raphael.spring.learn.mongo.services.profile.ProfileService;
import fachim.raphael.spring.learn.mongo.services.question.QuestionService;
import fachim.raphael.spring.learn.mongo.services.user.UserService;
import jakarta.websocket.server.PathParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController("api")
public class MainController {

    @Autowired
    private UserService userService;

    @Autowired
    private ProfileService profileService;

    @Autowired
    private QuestionService questionService;

    @PostMapping("user")
    public ResponseEntity<?> createUser(@RequestBody UserInput newUser){
        return ResponseEntity.ok().body(userService.create(newUser));
    }

    @GetMapping("user")
    public ResponseEntity<?> allUsers() {
        return ResponseEntity.ok().body(userService.all());
    }

    @PostMapping("profile")
    public ResponseEntity<?> createProfile(@RequestBody ProfileInput profileInput) {
        return ResponseEntity.ok(profileService.create(profileInput));
    }

    @PostMapping("question")
    public ResponseEntity<?> createQuestion(@RequestBody QuestionInput questionInput) {
        return ResponseEntity.ok(questionService.create(questionInput));
    }

    @GetMapping("user/{id}/questions")
    public ResponseEntity<?> getQuestionsByUser(@PathVariable("id") String userId){
        return questionService.allByUserId(Long.valueOf(userId));
    }

    @CrossOrigin(origins = "http://localhost:4200")
    @PutMapping("question/up")
    public ResponseEntity<?> voteUp(@RequestBody Vote vote){
        return questionService.voteUp(vote);
    }

    @CrossOrigin(origins = "http://localhost:4200")
    @PutMapping("question/down")
    public ResponseEntity<?> voteDown(@RequestBody Vote vote){
        return questionService.voteDown(vote);
    }

    @CrossOrigin(origins = "http://localhost:4200")
    @PostMapping("login")
    public ResponseEntity<?> login(@RequestBody Login login){
        return userService.login(login);
    }

    @GetMapping("question")
    public ResponseEntity<?> allQuestions() {
        return questionService.allPaginated(5);
    }

    @GetMapping("question/{id}")
    public ResponseEntity<?> findQuestion(@PathVariable("id") String id) {
        return questionService.findById(id);
    }
}
