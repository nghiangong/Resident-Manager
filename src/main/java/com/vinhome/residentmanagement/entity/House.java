package com.vinhome.residentmanagement.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "houses")
public class House {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String name;
    @Lob
    private String note;
    @Column(nullable = false)
    private String address;
    @Column(name = "deleted_at")
    @Temporal(TemporalType.DATE)
    private Date deletedAt;
    @JsonIgnore
    @OneToMany(mappedBy="house")
    private Set<User> users = new HashSet<>();
}
