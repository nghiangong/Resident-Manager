package com.vinhome.residentmanagement.dtos;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.Set;

@Getter
@Setter
public class UserPostDto {
    private Long id;
    private String name;
    private String phone;
    private String userName;
    private String password;
    private String email;
    private boolean acceptedStatus;
    private boolean gender;
    private String image;
    private Date date;
    private int ownId;
    private String idCard;
    private boolean createQrPermission;
    private Date deletedAt;
    private Long houseId;
    private Long gateId;
    private Set<Long> roleIds;
}
