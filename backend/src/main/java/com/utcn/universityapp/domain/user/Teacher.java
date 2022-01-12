package com.utcn.universityapp.domain.user;

import com.utcn.universityapp.domain.subject.StudentSubject;
import com.utcn.universityapp.domain.subject.Subject;
import lombok.Data;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;

import javax.persistence.*;
import java.util.Set;

@Data
@Entity
@Table(name = "teacher")
@EnableAutoConfiguration
public class Teacher {

    @Id
    @Column(name = "userId", nullable = false)
    private long userId;

    @OneToOne
    @JoinColumn(name = "userId")
    private User user;

    @OneToMany(mappedBy = "teacher")
    private Set<StudentSubject> studentSubjects;

}
