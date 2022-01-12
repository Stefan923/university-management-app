package com.utcn.universityapp.repository;

import com.utcn.universityapp.domain.subject.StudentSubject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@CrossOrigin
@RepositoryRestResource(collectionResourceRel = "student_subjects", path = "student_subjects")
public interface StudentSubjectRepository extends JpaRepository<StudentSubject, Long> {

    @RestResource(path = "byTeacherUsername", rel = "findStudentSubjectsByTeacherUsername")
    List<StudentSubject> findAllByTeacherUserAccountUsernameLike(
            @RequestParam("username") String username);

    @RestResource(path = "byStudentUsername", rel = "findStudentSubjectsByStudentUsername")
    List<StudentSubject> findAllByStudentUserAccountUsernameLike(
            @RequestParam("username") String username);

}
