package com.utcn.universityapp.service;

import com.utcn.universityapp.domain.Attempt;
import com.utcn.universityapp.repository.AttemptRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;

@Service
@Transactional
public class AttemptService {

    private AttemptRepository attemptRepository;

    public void save(Attempt attempt) {
        attemptRepository.save(attempt);
    }

    public int countAttempts(String username, LocalDateTime afterTime) {
        return attemptRepository
                .countAllByUsernameLikeAndAttemptTimeGreaterThanEqual(username, afterTime);
    }

    @Autowired
    public void setAttemptRepository(AttemptRepository attemptRepository) {
        this.attemptRepository = attemptRepository;
    }
}
