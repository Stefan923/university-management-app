package com.utcn.universityapp.domain.subject;

import com.utcn.universityapp.domain.user.Student;
import com.utcn.universityapp.domain.user.Teacher;
import lombok.Data;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;

import javax.persistence.*;
import java.util.Set;

@Data
@Entity
@Table(name = "subject")
@EnableAutoConfiguration
public class Subject {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private long id;

    @Column(name = "name", length = 64, nullable = false)
    private String name;

    @Column(name = "credits", nullable = false)
    private int credits;

    @Column(name = "year", nullable = false)
    private int year;

    @Column(name = "semester", nullable = false)
    private int semester;

    @OneToMany(mappedBy = "subject")
    private Set<StudentSubject> studentSubjects;

}
