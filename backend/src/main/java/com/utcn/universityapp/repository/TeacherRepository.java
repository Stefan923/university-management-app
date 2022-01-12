package com.utcn.universityapp.repository;

import com.utcn.universityapp.domain.user.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestParam;

@CrossOrigin
@RepositoryRestResource(collectionResourceRel = "teachers", path = "teachers")
public interface TeacherRepository extends JpaRepository<Teacher, Long> {

    <S extends Teacher> S save(@RequestParam("userId") long userId);

}
