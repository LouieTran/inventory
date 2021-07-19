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
public class StatisticDateFormDto {
    private Date dateStart = null;
    private Date dateEnd = null;
    private int inventory;
    private int user;
    PaginationDto page = new PaginationDto();
    SortDto sort = new SortDto();
}
