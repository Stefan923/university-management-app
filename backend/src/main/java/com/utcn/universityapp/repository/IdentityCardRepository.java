package com.utcn.universityapp.repository;

import com.utcn.universityapp.domain.IdentityCard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.web.bind.annotation.CrossOrigin;

@CrossOrigin
@RepositoryRestResource(collectionResourceRel = "identity_cards", path = "identity_cards")
public interface IdentityCardRepository extends JpaRepository<IdentityCard, Long> {
}
