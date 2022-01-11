package com.utcn.universityapp.repository;

import com.utcn.universityapp.domain.user.User;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@CrossOrigin
@RepositoryRestResource(collectionResourceRel = "users", path = "users")
public interface UserRepository extends JpaRepository<User, Long> {

    @RestResource(path = "byName", rel = "findUsersByName")
    <S extends User> List<S> findAllByFirstNameContainingIgnoreCaseAndLastNameContainingIgnoreCase(
            @RequestParam("firstName") String firstName,
            @RequestParam("lastName")  String lastName,
            Pageable pageable);

    @RestResource(path = "byRole", rel = "findUserByRole")
    <S extends User> List<S> findAllByRoleId(
            @RequestParam("roleId") long roleId,
            Pageable pageable);

}
