package com.sapo.edu.managetransferslip.dao;

import com.sapo.edu.managetransferslip.model.dto.statistic.StatisticDateTableDto;
import com.sapo.edu.managetransferslip.model.dto.statistic.StatisticInventoryTableDto;
import com.sapo.edu.managetransferslip.model.dto.statistic.StatisticProductTableDto;
import com.sapo.edu.managetransferslip.model.dto.statistic.StatisticTransferTableDto;

import java.sql.Date;
import java.util.List;


public interface IReportDao {
    List<StatisticProductTableDto> getSatisticProduct(Date start, Date end);

    List<StatisticTransferTableDto> getStatisticTranser(Date start, Date end);

    List<StatisticDateTableDto> getStatisticDate(Date start, Date end, int inventory, int user);

    List<StatisticInventoryTableDto> getSatisticInventory(Date date, int inventory, int user);


    
}
