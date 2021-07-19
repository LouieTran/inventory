package com.sapo.edu.managetransferslip.controller;

import com.sapo.edu.managetransferslip.model.dto.HistoryDTO;
import com.sapo.edu.managetransferslip.model.dto.InventoryDetailReportDTO;
import com.sapo.edu.managetransferslip.service.HistoryServices;
import com.sapo.edu.managetransferslip.service.impl.HistoryServiceImpl;
import com.sapo.edu.managetransferslip.service.impl.InventoryDetailImpl;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@PreAuthorize("hasRole('ROLE_MANAGER')")
@RequestMapping("/admin/history")
public class HistoryController {

    private HistoryServices historyService;

    public HistoryController(HistoryServices historyService) {
        this.historyService = historyService;
    }

    @GetMapping()
    public List<HistoryDTO> getData(@RequestParam int id, @RequestParam int page, @RequestParam int limit){
        return historyService.getData(id,page,limit);
    }

    @GetMapping("/size")
    public long size(@RequestParam int id){
        return historyService.sizeHistory(id);
    }
}
