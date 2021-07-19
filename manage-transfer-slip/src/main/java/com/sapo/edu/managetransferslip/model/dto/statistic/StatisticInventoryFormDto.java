package com.sapo.edu.managetransferslip.model.dto.statistic;

import com.sapo.edu.managetransferslip.model.dto.PaginationDto;
import com.sapo.edu.managetransferslip.model.dto.SortDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class StatisticInventoryFormDto {
    PaginationDto page = new PaginationDto(0, 1, 3);
    SortDto sort = new SortDto();
    Date date;
    int inventory, user;
}
