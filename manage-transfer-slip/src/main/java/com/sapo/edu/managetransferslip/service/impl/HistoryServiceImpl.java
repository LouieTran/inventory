package com.sapo.edu.managetransferslip.service.impl;

import com.sapo.edu.managetransferslip.model.dto.HistoryDTO;
import com.sapo.edu.managetransferslip.model.dto.InventoriesDTO;
import com.sapo.edu.managetransferslip.model.dto.TransferDTO;
import com.sapo.edu.managetransferslip.model.entity.InventoriesEntity;
import com.sapo.edu.managetransferslip.model.entity.TransferEntity;
import com.sapo.edu.managetransferslip.repository.InventoryRepository;
import com.sapo.edu.managetransferslip.repository.TransferRepository;
import com.sapo.edu.managetransferslip.repository.UserRepository;
import com.sapo.edu.managetransferslip.service.HistoryServices;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class HistoryServiceImpl implements HistoryServices {
    private TransferRepository transferRepository;
    private InventoryRepository inventoryRepository;
    private UserRepository userRepository;

    public HistoryServiceImpl(TransferRepository transferRepository, InventoryRepository inventoryRepository, UserRepository userRepository) {
        this.transferRepository = transferRepository;
        this.inventoryRepository = inventoryRepository;
        this.userRepository = userRepository;
    }

    @Override
    public List<HistoryDTO> getData(int id, int page, int limit) {
        int start = (page - 1) *limit;
        List<TransferEntity> transferEntities = transferRepository.findHistoryByInventoryId(id,start,limit);
        if (transferEntities.isEmpty())
        {
            return null;
        }
        List<HistoryDTO> historyDTOS = new ArrayList<>();
        for (TransferEntity transferEntity :
                transferEntities) {
            InventoriesEntity inventoriesEntityImport = inventoryRepository.findById(transferEntity.getInventoryInput().getId()).get();
            InventoriesEntity inventoriesEntityExport = inventoryRepository.findById(transferEntity.getInventoryOutput().getId()).get();
            if ( (inventoriesEntityExport != null) && (inventoriesEntityImport != null))
            {
                historyDTOS.add(new HistoryDTO(new InventoriesDTO(inventoriesEntityImport),new InventoriesDTO(inventoriesEntityExport),new TransferDTO(transferEntity)));
            }
        }
        return historyDTOS;
     }

    @Override
    public long sizeHistory(int id) {
        long size = 0;
        try{
            size = transferRepository.sizeHistory(id);

        }catch (Exception e){
            return 0;
        }
        return  size;
    }

    @Override
    public List<HistoryDTO> findData(String key, int status, int page, int limit) {
        //tim tren kho chuyen trc
        //lay het thong itn
        //tim tren kho con lai
        //lay thong itn doi sanh tra data
        return null;
    }

}
