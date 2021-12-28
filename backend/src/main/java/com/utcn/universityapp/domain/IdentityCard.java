package com.utcn.universityapp.domain;

import com.utcn.universityapp.domain.user.User;
import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "identityCard")
public class IdentityCard {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "userId", nullable = false)
    private long id;

    @Column(name = "cnp", length = 13, nullable = false)
    private String cnp;

    @Column(name = "series", length = 2, nullable = false)
    private String series;

    @Column(name = "number", length = 6, nullable = false)
    private String number;

    @OneToOne(mappedBy = "identityCard", cascade = CascadeType.ALL)
    @PrimaryKeyJoinColumn
    private Address address;

    @OneToOne
    @MapsId
    @JoinColumn(name = "userId")
    private User user;

}
