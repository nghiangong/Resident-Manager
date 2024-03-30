package com.vinhome.residentmanagement.dtos;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class GateDto {
    private Long id;
    private String name;
    private String address;
    private Date deletedAt;
}
