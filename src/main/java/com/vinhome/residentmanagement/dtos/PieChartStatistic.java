package com.vinhome.residentmanagement.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PieChartStatistic {
    private int living;
    private Long leaved;
    private int unApproved;
}
