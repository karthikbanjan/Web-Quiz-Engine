package engine.respository;

import engine.model.quiz.Quiz;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface QuizRepository extends PagingAndSortingRepository<Quiz, Long> {

    Page<Quiz> findByOrderByIdAsc(Pageable pageable);

}