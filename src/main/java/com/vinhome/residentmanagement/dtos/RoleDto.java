package com.vinhome.residentmanagement.dtos;

import com.vinhome.residentmanagement.entity.RoleEnum;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RoleDto {
    private Long id;
    private RoleEnum name;
}
