package engine.respository;

import engine.model.completion.Completion;
import engine.model.user.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CompletionRepository extends PagingAndSortingRepository<Completion, Long> {

    Page<Completion> findByUserOrderByCompletedAtDesc(User user, Pageable pageable);

}