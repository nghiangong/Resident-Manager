package com.vinhome.residentmanagement.dtos;

import com.vinhome.residentmanagement.entity.Gate;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Date;

@Getter
@Setter
public class HistoryGetDto {
    private Long id;
    private String name;
    private String gender;
    private LocalDateTime date;
    private boolean isResident;
    private Date deletedAt;
    private GateDto gate;
    private UserGetDto qrCreator;
    private boolean readStatus;
}
