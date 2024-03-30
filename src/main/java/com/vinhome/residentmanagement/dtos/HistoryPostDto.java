package com.vinhome.residentmanagement.dtos;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Date;

@Getter
@Setter
public class HistoryPostDto {
    private Long id;
    private String name;
    private String gender;
    private LocalDateTime date;
    private boolean isResident;
    private Date deletedAt;
    private Long gateId;
    private Long qrCreatorId;
    private boolean readStatus;
}
