package com.sapo.edu.managetransferslip.service;

import com.sapo.edu.managetransferslip.model.dto.HistoryDTO;
import com.sapo.edu.managetransferslip.model.dto.InputTransferDTO;
import com.sapo.edu.managetransferslip.model.dto.Message;
import com.sapo.edu.managetransferslip.model.dto.TransferDTO;
import com.sapo.edu.managetransferslip.model.entity.TransferEntity;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;

import java.sql.Date;
import java.util.List;

public interface TransferService {

    List<TransferDTO> findAllTransfer(Integer page, Integer limit);

    ResponseEntity<?> createTransfer(InputTransferDTO inputTransferDTO);

    ResponseEntity<?> updateTransfer(InputTransferDTO inputTransferDTO, Integer id);

    ResponseEntity<?> getDetail(Integer id);

    ResponseEntity<?> shipping(Integer id);


    ResponseEntity<?> receive(Integer id);


    Message deleteTransfer(Integer id);

    List<TransferDTO> search(String keyword , Integer page, Integer limit);

    List<TransferDTO> findByDate(Date dateMin, Date dateMax, Integer page, Integer limit);

    List<TransferDTO> findHistory(int id,int pages,int limit);

    List<TransferDTO> findByInventoryInputId(int id,int page,int limit);

    List<TransferDTO> findByInventoryOutputId(int id,int page,int limit);

    List<TransferDTO> findingByKeyForHistory(String key);

    long sizeHistory(int id);

    long sizeExport(int id);

    long sizeImport(int id);

    List<HistoryDTO> paginationFindingBetween(String from, String to, int offset, int limit, int id);

    long sizeFindingBetween(String from,String to,int id);

    long sizeSearch(String key,int id);

    List<HistoryDTO> searchByKeyAndId(int page,int limit,String key,int id);

}
