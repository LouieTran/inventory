package com.sapo.edu.managetransferslip.model.dto.statistic;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Date;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class StatisticImport {
    private Date date;
    int transfer, product, proNumber;
}
