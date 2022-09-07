package engine.model.completion;

import engine.model.user.User;
import engine.respository.CompletionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class CompletionService {

    private final CompletionRepository completionRepository;

    @Autowired
    public CompletionService(CompletionRepository completionRepository) {
        this.completionRepository = completionRepository;
    }

    public void save(Completion completion) {
        completionRepository.save(completion);
    }

    public Page<Completion> getAll(User user, Integer page) {
        Pageable pageable = PageRequest.of(page, 10);

        return completionRepository.findByUserOrderByCompletedAtDesc(user, pageable);
    }

}
