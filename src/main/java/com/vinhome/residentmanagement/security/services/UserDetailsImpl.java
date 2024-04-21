package com.vinhome.residentmanagement.security.services;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.vinhome.residentmanagement.dtos.GateDto;
import com.vinhome.residentmanagement.dtos.HouseDto;
import com.vinhome.residentmanagement.entity.User;
import com.vinhome.residentmanagement.mappers.MapStructMapper;
import com.vinhome.residentmanagement.mappers.MapStructMapperImpl;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Getter
public class UserDetailsImpl implements UserDetails {
    private static final long serialVersionUID = 1L;

    private Long id;

    private String name;
    private String phone;

    private String username;

    private String email;

    @JsonIgnore
    private String password;

    private boolean acceptedStatus;

    private boolean gender;
    private String image;
    private Date date;
    private Long ownId;
    private String idCard;
    private boolean createQrPermission;
    private HouseDto house;
    private GateDto gate;
    private Date deletedAt;

    private Collection<? extends GrantedAuthority> authorities;

    public UserDetailsImpl(Long id, String name, String phone, String username, String email, String password,
                           boolean acceptedStatus, boolean gender, String image, Date date, Long ownId, String idCard, boolean createQrPermission, HouseDto house, GateDto gate, Date deletedAt, Collection<? extends GrantedAuthority> authorities) {
        this.id = id;
        this.name = name;
        this.phone = phone;
        this.username = username;
        this.email = email;
        this.password = password;
        this.acceptedStatus = acceptedStatus;
        this.gender = gender;
        this.image = image;
        this.date = date;
        this.ownId = ownId;
        this.idCard = idCard;
        this.createQrPermission = createQrPermission;
        this.house = house;
        this.gate = gate;
        this.deletedAt = deletedAt;
        this.authorities = authorities;
    }

    public static UserDetailsImpl build(User user) {
        MapStructMapper mapStructMapper = new MapStructMapperImpl();
        List<GrantedAuthority> authorities = user.getRoles().stream()
                .map(role -> new SimpleGrantedAuthority(role.getName().name()))
                .collect(Collectors.toList());

        return new UserDetailsImpl(
                user.getId(),
                user.getName(), user.getPhone(), user.getUsername(),
                user.getEmail(),
                user.getPassword(),
                user.isAcceptedStatus(),
                user.isGender(),
                user.getImage(),
                user.getDate(),
                user.getOwnId(),
                user.getIdCard(),
                user.isCreateQrPermission(),
                mapStructMapper.houseToHouseDto(user.getHouse()),
                mapStructMapper.gateToGateDto(user.getGate()),
                user.getDeletedAt(), authorities);
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    public Long getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

//    public boolean isAcceptedStatus() {
//        return acceptedStatus;
//    }
//
//    public boolean isGender() {
//        return gender;
//    }
//
//    public boolean isCreateQrPermission() {
//        return createQrPermission;
//    }
//
//    public Date getDate() {
//        return date;
//    }
//
//    public GateDto getGate() {
//        return gate;
//    }
//
//    public HouseDto getHouse() {
//        return house;
//    }
//
//    public Long getOwnId() {
//        return ownId;
//    }
//
//    public String getIdCard() {
//        return idCard;
//    }
//
//    public String getImage() {
//        return image;
//    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        UserDetailsImpl user = (UserDetailsImpl) o;
        return Objects.equals(id, user.id);
    }
}
