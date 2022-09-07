package engine.model.quiz;

import engine.respository.QuizRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class QuizService {

    private final QuizRepository quizRepository;

    public QuizService(QuizRepository quizRepository) {
        this.quizRepository = quizRepository;
    }

    public void save(Quiz quiz) {
        quizRepository.save(quiz);
    }

    public Optional<Quiz> findById(Long id) {
        return quizRepository.findById(id);
    }

    public void deleteById(Long id) {
        quizRepository.deleteById(id);
    }

    public Page<Quiz> getAll(Integer page) {
        Pageable pageable = PageRequest.of(page, 10);
        return quizRepository.findByOrderByIdAsc(pageable);
    }

}
