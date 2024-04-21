package com.vinhome.residentmanagement.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
public class OverviewStatistic {
    private int totalResidents;
    private int totalGateKeepers;
    private Long visitorPerMonth;
}
