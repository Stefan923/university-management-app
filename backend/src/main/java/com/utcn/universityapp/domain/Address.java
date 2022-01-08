package com.utcn.universityapp.domain;

import lombok.Data;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;

import javax.persistence.*;

@Data
@Entity
@Table(name = "address")
@EnableAutoConfiguration
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "identityCardId", nullable = false)
    private long id;

    @Column(name = "country", length = 64, nullable = false)
    private String country;

    @Column(name = "state", length = 64, nullable = false)
    private String state;

    @Column(name = "city", length = 64, nullable = false)
    private String city;

    @Column(name = "street", length = 64, nullable = false)
    private String street;

    @Column(name = "streetNumber", nullable = false)
    private int streetNumber;

    @OneToOne
    @MapsId
    @JoinColumn(name = "identityCardId")
    private IdentityCard identityCard;

}
