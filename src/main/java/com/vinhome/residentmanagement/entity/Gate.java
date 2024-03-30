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
@Table(name = "gates")
public class Gate {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private String address;
    @Temporal(TemporalType.DATE)
    @Column(name = "deleted_at")
    private Date deletedAt;
    @JsonIgnore
    @OneToMany(mappedBy="gate")
    private Set<User> users = new HashSet<>();
    @JsonIgnore
    @OneToMany(mappedBy="gate")
    private Set<History> histories = new HashSet<>();
}
