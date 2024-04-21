package com.vinhome.residentmanagement.response;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.vinhome.residentmanagement.dtos.GateDto;
import com.vinhome.residentmanagement.dtos.HouseDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class JwtResponse {
    private String token;
    private Long id;
    private String name;
    private String phone;
    private String username;
    private String email;
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
    List<String> roles;
}
