package com.utcn.universityapp.repository;

import com.utcn.universityapp.domain.Attempt;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;

public interface AttemptRepository extends JpaRepository<Attempt, Long> {

    int countAllByUsernameLikeAndAttemptTimeGreaterThanEqual(String username, LocalDateTime attemptTime);

}
