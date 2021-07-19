package com.sapo.edu.managetransferslip.model.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class HistoryDTO {
     //ma kho nhap
     //ten kho nhap
     private InventoriesDTO inventoriesDTOImport;
     //ma kho xuat
     //ten kho xuat
     private  InventoriesDTO inventoriesDTOExport;
     //so luong san pham trong kho thieu
     //chi tiet so luong san pham thuoc kho
     //ma
     private TransferDTO transferDTO;

     public HistoryDTO(InventoriesDTO inventoriesDTOImport, InventoriesDTO inventoriesDTOExport, TransferDTO transferDTO) {
          this.inventoriesDTOImport = inventoriesDTOImport;
          this.inventoriesDTOExport = inventoriesDTOExport;
          this.transferDTO = transferDTO;
     }

     @Override
     public String toString() {
          return "HistoryDTO{" +
                  "inventoriesDTOImport=" + inventoriesDTOImport +
                  ", inventoriesDTOExport=" + inventoriesDTOExport +
                  ", transferDTO=" + transferDTO +
                  '}';
     }
}
