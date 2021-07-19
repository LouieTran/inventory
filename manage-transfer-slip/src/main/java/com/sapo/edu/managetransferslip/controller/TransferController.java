package com.sapo.edu.managetransferslip.controller;

import com.sapo.edu.managetransferslip.model.dto.HistoryDTO;
import com.sapo.edu.managetransferslip.model.dto.InputTransferDTO;
import com.sapo.edu.managetransferslip.model.dto.Message;
import com.sapo.edu.managetransferslip.model.dto.TransferDTO;
import com.sapo.edu.managetransferslip.service.TransferDetailService;
import com.sapo.edu.managetransferslip.service.TransferService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
import java.util.List;

@RestController
@RequestMapping("/transfers")
@CrossOrigin("http://localhost:3006")
public class TransferController {
    private final TransferService transferService;
    private final TransferDetailService transferDetailService;

    public TransferController(TransferService transferService, TransferDetailService transferDetailService){
        this.transferService = transferService;
        this.transferDetailService = transferDetailService;
    }

    @GetMapping
    @PreAuthorize("hasRole('ROLE_COORDINATOR')or hasRole('ROLE_MANAGER')")
    public List<TransferDTO> getAllTransfer(@RequestParam(value = "page", required = false, defaultValue = "1") Integer page,
                                            @RequestParam(value = "limit",required = false) Integer limit,
                                            @RequestParam(value = "key" , required = false) String key,
                                            @RequestParam(value = "dateMin",required = false, defaultValue = "2000-01-1") Date dateMin,
                                            @RequestParam(value = "dateMax",required = false, defaultValue = "2050-01-1") Date dateMax) {

        if(key == "" || key == null){
            if(dateMin != null && dateMax != null){
                return transferService.findByDate(dateMin,dateMax,page-1,limit);
            }
            else if(dateMin == null && dateMax == null) {
                return transferService.findAllTransfer(page - 1, limit);
            }
            else
                return transferService.findAllTransfer(page - 1, limit);

        }
        else {
            return transferService.search(key,page-1,limit);

        }
    }


    @PostMapping
    @PreAuthorize("hasRole('ROLE_COORDINATOR') or hasRole('ROLE_MANAGER')")
    public ResponseEntity<?> createTransfer(@RequestBody InputTransferDTO inputTransferDTO){
        return transferService.createTransfer(inputTransferDTO);
    }

    @PutMapping("/shipping/{id}")
    @PreAuthorize("hasRole('ROLE_INVENTORIER')")
    public ResponseEntity<?> shipping(@PathVariable int id){
        return transferService.shipping(id);
    }
    @PutMapping("/receive/{id}")
    @PreAuthorize("hasRole('ROLE_INVENTORIER')")
    public ResponseEntity<?> receive(@PathVariable int id){
        return transferService.receive(id);
    }


//    @PutMapping("/{id}")
//    public ResponseEntity<?> updateTransfer(@RequestBody InputTransferDTO inputTransferDTO, @PathVariable int id){
//        return transferService.updateTransfer(inputTransferDTO,id);
//
//    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_INVENTORIER') or hasRole('ROLE_MANAGER') or hasRole('ROLE_COORDINATOR')")
    public ResponseEntity<?> getDetailTransfer(@PathVariable int id){
        return transferService.getDetail(id);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_COORDINATOR')")
    public Message deleteTransfer(@PathVariable int id){
        return transferService.deleteTransfer(id);
    }

//    @GetMapping("/date")
//    public List<TransferDTO> search(@RequestParam(value = "page" ,required = false,defaultValue = "1") Integer page,
//                                    @RequestParam(value = "limit" ,required = false) Integer limit,
//                                    @RequestParam(value = "dateMin") Date dateMin,
//                                    @RequestParam(value = "dateMax") Date dateMax) {
//        return transferService.findByDate(dateMin,dateMax,page-1,limit);
//    }

    @GetMapping("/search/size")
    @PreAuthorize("hasAnyRole('ROLE_INVENTORIER','ROLE_MANAGER','ROLE_COORDINATOR')")
    public long sizeSearch(@RequestParam String key,@RequestParam int id){
        return transferService.sizeSearch(key,id);
    }
    
//    @DeleteMapping("/detail/{id}")
//    public Message deleteDetailTransfer(@PathVariable int id){
//        return transferDetailService.deleteTransferDetail(id);
//    }

    //hungnq api custom transfer
    //done
    @GetMapping("/history")
    @PreAuthorize("hasAnyRole('ROLE_INVENTORIER','ROLE_MANAGER','ROLE_COORDINATOR')")
    public List<TransferDTO> history(@RequestParam int id,@RequestParam int page,@RequestParam int limit){
        return  transferService.findHistory(id,page,limit);
    }

    //hungnq api custom transfer
    //done
    @GetMapping("/export")
    @PreAuthorize("hasAnyRole('ROLE_INVENTORIER','ROLE_MANAGER','ROLE_COORDINATOR')")
    public List<TransferDTO> findExport(@RequestParam int id,@RequestParam int page,@RequestParam int limit){
        return  transferService.findByInventoryOutputId(id,page,limit);
    }
//
//    //hungnq api custom transfer
    @GetMapping("/import")
    @PreAuthorize("hasAnyRole('ROLE_INVENTORIER','ROLE_MANAGER','ROLE_COORDINATOR')")
    public List<TransferDTO> findImport(@RequestParam int id,@RequestParam int page,@RequestParam int limit){
        return  transferService.findByInventoryInputId(id,page,limit);
    }

    //hungnq size history
    @GetMapping("/history/size")
    @PreAuthorize("hasAnyRole('ROLE_INVENTORIER','ROLE_MANAGER','ROLE_COORDINATOR')")
    public long sizeHistory(@RequestParam int id){
        return transferService.sizeHistory(id);
    }

    @GetMapping("/export/size")
    @PreAuthorize("hasAnyRole('ROLE_INVENTORIER','ROLE_MANAGER','ROLE_COORDINATOR')")
    public long sizeExport(@RequestParam int id){
        return transferService.sizeExport(id);
    }

    @GetMapping("/import/size")
    @PreAuthorize("hasAnyRole('ROLE_INVENTORIER','ROLE_MANAGER','ROLE_COORDINATOR')")
    public long sizeImport(@RequestParam int id){
        return transferService.sizeImport(id);
    }

    @GetMapping("/history/finding")
    @PreAuthorize("hasAnyRole('ROLE_INVENTORIER','ROLE_MANAGER','ROLE_COORDINATOR')")
    public List<HistoryDTO> paginationFindingBetween(
            @RequestParam String from,@RequestParam String to,@RequestParam int page,@RequestParam int limit,@RequestParam int id
    ){
        return transferService.paginationFindingBetween(from,to,page,limit,id);
    }

    @GetMapping("/history/finding/size")
    @PreAuthorize("hasAnyRole('ROLE_INVENTORIER','ROLE_MANAGER','ROLE_COORDINATOR')")
    public long size(
            @RequestParam String from,@RequestParam String to,@RequestParam int id
    ){
        return transferService.sizeFindingBetween(from,to,id);
    }

    @GetMapping("/history/search-by-key")
    @PreAuthorize("hasAnyRole('ROLE_INVENTORIER','ROLE_MANAGER','ROLE_COORDINATOR')")
    public List<HistoryDTO> searchByKeyAndId(@RequestParam  int page,@RequestParam  int limit,@RequestParam  String key,@RequestParam int id){
        return transferService.searchByKeyAndId(page,limit,key,id);
    }

    @GetMapping("/history/search-by-key/size")
    @PreAuthorize("hasAnyRole('ROLE_INVENTORIER','ROLE_MANAGER','ROLE_COORDINATOR')")
    public long sizeHistoryFinding(@RequestParam  String key,@RequestParam int id){
        return transferService.sizeSearch(key,id);
    }
}
