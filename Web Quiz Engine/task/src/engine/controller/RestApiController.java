package engine.controller;

import engine.model.completion.Completion;
import engine.model.completion.CompletionService;
import engine.model.quiz.Quiz;
import engine.model.quiz.QuizService;
import engine.model.user.User;
import engine.model.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.*;

@RestController
public class RestApiController {

    private final QuizService quizService;
    private final UserService userService;
    private final CompletionService completionService;

    @Autowired
    public RestApiController(QuizService quizService, UserService userService, CompletionService completionService) {
        this.quizService = quizService;
        this.userService = userService;
        this.completionService = completionService;
    }

    private static final String success = """
            {"success":true,"feedback":"Congratulations, you're right!"}""";

    private static final String failure = """
            {"success":false,"feedback":"Wrong answer! Please, try again."}""";

    @PostMapping("/api/register")
    public void registerUser(@Valid @RequestBody User user) {
        if (userService.findUserByEmail(user.getEmail()).isPresent()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
        user.setPassword(userService.getPasswordEncoder().encode(user.getPassword()));
        userService.save(user);
    }

    @PostMapping("/api/quizzes")
    public ResponseEntity<Quiz> createQuiz(@Valid @RequestBody Quiz quiz,
                                           @AuthenticationPrincipal UserDetails userDetails) {
        quiz.setUser(userService.findUserByEmail(userDetails.getUsername()).get());
        quizService.save(quiz);

        return ResponseEntity.ok(quiz);
    }

    @GetMapping("/api/quizzes/{id}")
    public ResponseEntity<Quiz> returnQuizById(@PathVariable Long id) {
        Optional<Quiz> quiz = quizService.findById(id);

        if (quiz.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Quiz not found");
        }

        return ResponseEntity.ok(quiz.get());
    }

    @GetMapping("/api/quizzes")
    public ResponseEntity<Page<Quiz>> returnAllQuizzes(@RequestParam(defaultValue = "1") Integer page) {
        return ResponseEntity.ok(quizService.getAll(page));
    }

    @PostMapping("/api/quizzes/{id}/solve")
    public ResponseEntity<String> checkAnswer(@PathVariable Long id,
                                              @RequestBody Map<String, Set<Integer>> answerMap,
                                              @AuthenticationPrincipal UserDetails userDetails) {
        Optional<Quiz> quiz = quizService.findById(id);

        if (quiz.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Quiz not found");
        }

        Completion completion = new Completion();

        if (Objects.equals(answerMap.get("answer"), quiz.get().getAnswer())) {
            completion.setQuiz(quiz.get());
            completion.setUser(userService.findUserByEmail(userDetails.getUsername()).get());
            completion.setCompletedAt(LocalDateTime.now());
            completionService.save(completion);

            return ResponseEntity.ok(success);
        } else {
            return ResponseEntity.ok(failure);
        }
    }

    @GetMapping("/api/quizzes/completed")
    public ResponseEntity<Page<Completion>> returnAllCompletions(@AuthenticationPrincipal UserDetails userDetails,
                                                                 @RequestParam(defaultValue = "1") Integer page) {
        return ResponseEntity.ok(
                completionService.getAll(userService.findUserByEmail(userDetails.getUsername()).get(), page));
    }

    @DeleteMapping("/api/quizzes/{id}")
    public void deleteQuiz(@PathVariable Long id, @AuthenticationPrincipal UserDetails userDetails) {
        Optional<Quiz> quiz = quizService.findById(id);
        Optional<User> user = userService.findUserByEmail(userDetails.getUsername());

        if (quiz.isEmpty() || user.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }

        if (!quiz.get().getUser().equals(user.get())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }

        quizService.deleteById(id);
        throw new ResponseStatusException(HttpStatus.NO_CONTENT);
    }

}
