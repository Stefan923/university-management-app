package com.utcn.universityapp.domain;

import lombok.Data;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "attempts")
@RequiredArgsConstructor
public class Attempt {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private long id;

    @Column(name = "username", nullable = false)
    private String username;

    @Column(name = "attempt_time", nullable = false, unique = true)
    private LocalDateTime attemptTime;

    public Attempt(String username, LocalDateTime attemptTime) {
        this.username = username;
        this.attemptTime = attemptTime;
    }

}
