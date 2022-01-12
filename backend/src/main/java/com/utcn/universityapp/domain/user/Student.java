package com.utcn.universityapp.domain.user;

import com.utcn.universityapp.domain.subject.StudentSubject;
import lombok.Data;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;

import javax.persistence.*;
import java.util.Set;

@Data
@Entity
@Table(name = "student")
@EnableAutoConfiguration
public class Student {

    @Id
    @Column(name = "userId", nullable = false)
    private long userId;

    @Column(name = "year", nullable = false)
    private int year;

    @Column(name = "groupId", nullable = false)
    private int group;

    @OneToOne
    @JoinColumn(name = "userId")
    private User user;

    @OneToMany(mappedBy = "student")
    private Set<StudentSubject> studentSubjects;

}
