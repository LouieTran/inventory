package com.sapo.edu.managetransferslip.service;

import com.sapo.edu.managetransferslip.model.dto.HistoryDTO;

import java.util.List;

public interface HistoryServices {
    List<HistoryDTO> getData(int id, int page, int limit);

    long sizeHistory(int id);

    List<HistoryDTO> findData(String key,int status,int page,int limit);
}
