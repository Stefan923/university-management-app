package com.utcn.universityapp.domain.subject;

import com.utcn.universityapp.domain.user.Student;
import com.utcn.universityapp.domain.user.Teacher;
import lombok.Data;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;

import javax.persistence.*;
import java.util.Set;

@Data
@Entity
@Table(name = "studentSubject")
@EnableAutoConfiguration
public class StudentSubject {

    @EmbeddedId
    private StudentSubjectKey id;

    @ManyToOne
    @MapsId("studentId")
    @JoinColumn(name = "student_id", nullable = false)
    private Student student;

    @ManyToOne
    @MapsId("subjectId")
    @JoinColumn(name = "subject_id", nullable = false)
    private Subject subject;

    @ManyToOne
    @JoinColumn(name = "teacherId", nullable = false)
    private Teacher teacher;

}
