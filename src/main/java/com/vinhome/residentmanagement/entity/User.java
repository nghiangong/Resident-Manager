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
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private String phone;
    @Column(nullable = false, unique = true, name = "user_name")
    private String userName;
    @Column(nullable = false)
    private String password;
    private String email;
    @Column(nullable = false, name = "accepted_status", columnDefinition = "boolean default false")
    private boolean acceptedStatus;
    @Column(nullable = false)
    private boolean gender;
    @Column(name = "public_key")
    private String publicKey;
    @Column(name = "private_key")
    private String privateKey;
    private String image;
    @Temporal(TemporalType.DATE)
    private Date date;
    @Column(name = "own_id")
    private Long ownId;
    @Column(name = "id_card")
    private String idCard;
    @Column(name = "create_qr_permission")
    private boolean createQrPermission;
    @Temporal(TemporalType.DATE)
    @Column(name = "deleted_at")
    private Date deletedAt;
    @JsonIgnore
    @ManyToMany
    @JoinTable(
            name = "user_role",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles = new HashSet<>();
    @ManyToOne
    @JoinColumn(name = "house_id")
    private House house;
    @ManyToOne
    @JoinColumn(name = "gate_id")
    private Gate gate;
    @JsonIgnore
    @OneToMany(mappedBy = "qrCreator")
    private Set<History> histories = new HashSet<>();
    public void removeRole(Role role){
        this.roles.remove(role);
        role.getUsers().remove(this);
    }
    public void addRole(Role role){
        this.roles.add(role);
        role.getUsers().add(this);
    }
    public void clearRoles() {
        this.roles.clear();
    }
}
