package com.utcn.universityapp.repository;

import com.utcn.universityapp.domain.subject.StudentSubject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.web.bind.annotation.CrossOrigin;

@CrossOrigin
@RepositoryRestResource(collectionResourceRel = "student_subjects", path = "student_subjects")
public interface StudentSubjectRepository extends JpaRepository<StudentSubject, Long> {
}
