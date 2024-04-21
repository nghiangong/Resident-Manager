package com.vinhome.residentmanagement.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@AllArgsConstructor
@Setter
@NoArgsConstructor
public class DayStatistic {
    private String day;
    private Long totalResidents;
    private Long totalVisitors;
}
