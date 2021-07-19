package com.sapo.edu.managetransferslip.model.dto.statistic;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class StatisticInventoryTableDto {
    String inventory;
    int transferInNum, transferOutNum, productOutput, productInput, productInNum, productOutNum, productInTotal, productOutTotal;
}
