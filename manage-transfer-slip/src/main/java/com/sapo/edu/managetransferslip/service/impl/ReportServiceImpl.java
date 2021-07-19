package com.sapo.edu.managetransferslip.service.impl;

import com.sapo.edu.managetransferslip.dao.IReportDao;
import com.sapo.edu.managetransferslip.model.dto.*;
import com.sapo.edu.managetransferslip.model.dto.product.ProductsDTO;
import com.sapo.edu.managetransferslip.model.dto.statistic.*;
import com.sapo.edu.managetransferslip.repository.InventoriesRepository;
import com.sapo.edu.managetransferslip.repository.ProductRepository;
import com.sapo.edu.managetransferslip.repository.UserRepository;
import com.sapo.edu.managetransferslip.service.IReportService;
import lombok.SneakyThrows;

import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ReportServiceImpl implements IReportService {

    private IReportDao reportDao;

    private ProductRepository productRepository;

    private InventoriesRepository inventoriesRepository;

    private UserRepository userRepository;

    public ReportServiceImpl(IReportDao reportDao, ProductRepository productRepository, InventoriesRepository inventoriesRepository, UserRepository userRepository) {
        this.reportDao = reportDao;
        this.productRepository = productRepository;
        this.inventoriesRepository = inventoriesRepository;
        this.userRepository = userRepository;
    }

    @SneakyThrows
    @Override
    public List<StatisticProductTableDto> getSatisticProduct(StatisticProductFormDto statisticProductForm) {
        try {
            List<StatisticProductTableDto> list = new ArrayList<>();
            if (statisticProductForm == null) {
                list = reportDao.getSatisticProduct(null, null);
                return list;
            }

            list = reportDao.getSatisticProduct(statisticProductForm.getDateStart(), statisticProductForm.getDateEnd());

            if (statisticProductForm.getProduct() != null) {

                List<ProductsDTO> listProduct = new ArrayList<>();
                for (int item : statisticProductForm.getProduct()) {
                    ProductsDTO productsDTO = new ProductsDTO(productRepository.getById(item));
                    listProduct.add(productsDTO);
                }
                if (listProduct != null) {
                    list = list.stream().filter(item -> listProduct.stream().anyMatch(p -> p.getCode().equals(item.getCode())))
                            .collect(Collectors.toList());
                } else {
                    throw new Exception("Not find product");
                }

            }
            if (statisticProductForm.getInventory() != 0) {
                InventoriesDTO inventoriesDTO = new InventoriesDTO(inventoriesRepository.getById(statisticProductForm.getInventory()));
                if (inventoriesDTO != null) {
                    list = list.stream().filter(a -> a.getInventory().equals(inventoriesDTO.getName())).collect(Collectors.toList());
                } else {
                    throw new Exception(inventoriesDTO.getName() + " is not exist");
                }
            }

            return list;
        } catch (Exception ex) {
            System.out.println("Error reportServiceImpl: " + ex.getMessage());
            throw new Exception(ex.getMessage());
        }
    }

    @Override
    public List<StatisticTransferTableDto> getStatisticTransfer(StatisticTransferFormDto statisticTransferFormDto) {

        try {
            List<StatisticTransferTableDto> list = reportDao.getStatisticTranser(statisticTransferFormDto.getDateStart(), statisticTransferFormDto.getDateEnd());

            if (statisticTransferFormDto.getInventoryInput() != 0) {
                InventoriesDTO inventoriesDTO = new InventoriesDTO(inventoriesRepository.getById(statisticTransferFormDto.getInventoryInput()));
                if (inventoriesDTO != null) {
                    list = list.stream().filter(a -> a.getInventoryInput().equals(inventoriesDTO.getName())).collect(Collectors.toList());
                } else {
                    throw new Exception(inventoriesDTO.getName() + " is not exist11111");
                }
            }

            if (statisticTransferFormDto.getInventoryOutput() != 0) {
                InventoriesDTO inventoriesDTO = new InventoriesDTO(inventoriesRepository.getById(statisticTransferFormDto.getInventoryOutput()));
                if (inventoriesDTO != null) {
                    list = list.stream().filter(a -> a.getInventoryOutput().equals(inventoriesDTO.getName())).collect(Collectors.toList());
                } else {
                    throw new Exception(inventoriesDTO.getName() + " is not exist");
                }
            }

            if (statisticTransferFormDto.getUser() != 0) {
                UsersDTO usersDTO = new UsersDTO(userRepository.getById(statisticTransferFormDto.getUser()));
                if (usersDTO != null) {
                    list = list.stream().filter(a -> a.getUsername().equals(usersDTO.getUsername())).collect(Collectors.toList());
                } else {
                    throw new Exception(usersDTO.getCode() + " is not exist");
                }
            }
            return list;
        } catch (Exception ex) {
            System.out.println("Error reportServiceImpl: " + ex);
//            throw new Exception(ex.getMessage());
            return null;
        }
    }

    @Override
    public List<StatisticDateTableDto> getStatisticDate(StatisticDateFormDto statisticDateFormDto) {

        List<StatisticDateTableDto> list = reportDao.getStatisticDate(statisticDateFormDto.getDateStart(), statisticDateFormDto.getDateEnd(), statisticDateFormDto.getInventory(), statisticDateFormDto.getUser());
        statisticDateFormDto.setDateEnd(statisticDateFormDto.getDateEnd() == null ? new Date(System.currentTimeMillis()) : statisticDateFormDto.getDateEnd());

        if (list.size() == 0) return list;

        //Sort
        String sort = statisticDateFormDto.getSort().getColumn() == null ? "" : statisticDateFormDto.getSort().getColumn();
        boolean asc = statisticDateFormDto.getSort().isAsc();
        switch (sort) {
            case "createDate": {
                if (asc) {
                    Collections.sort(list, (o1, o2) -> o1.getCreateDate().compareTo(o2.getCreateDate()));
                } else {
                    Collections.sort(list, (o1, o2) -> o2.getCreateDate().compareTo(o1.getCreateDate()));
                };
                break;
            }
            case "transferImport": {
                if (asc) {
                    Collections.sort(list, (o1, o2) -> o1.getTransferImport() - (o2.getTransferImport()));
                } else {
                    Collections.sort(list, (o1, o2) -> o2.getTransferImport() - (o1.getTransferImport()));
                };
                break;
            }
            case "transferExport": {
                if (asc) {
                    Collections.sort(list, (o1, o2) -> o1.getTransferExport() - (o2.getTransferExport()));
                } else {
                    Collections.sort(list, (o1, o2) -> o2.getTransferExport() - (o1.getTransferExport()));
                };
                break;
            }
            case "productImport": {
                if (asc) {
                    Collections.sort(list, (o1, o2) -> o1.getProductImport() - (o2.getProductImport()));
                } else {
                    Collections.sort(list, (o1, o2) -> o2.getProductImport() - (o1.getProductImport()));
                };
                break;
            }
            case "productExport": {
                if (asc) {
                    Collections.sort(list, (o1, o2) -> o1.getProductExport() - (o2.getProductExport()));
                } else {
                    Collections.sort(list, (o1, o2) -> o2.getProductExport() - (o1.getProductExport()));
                };
                break;
            }
            case "proTotalImport": {
                if (asc) {
                    Collections.sort(list, (o1, o2) -> (int) Math.ceil(o1.getProTotalImport() - (o2.getProTotalImport())));
                } else {
                    Collections.sort(list, (o1, o2) ->  (int) Math.ceil(o2.getProTotalImport() - (o1.getProTotalImport())));
                };
                break;
            }
            case "proTotalExport": {
                if (asc) {
                    Collections.sort(list, (o1, o2) -> (int) Math.ceil(o1.getProductExport() - (o2.getProductExport())));
                } else {
                    Collections.sort(list, (o1, o2) ->  (int) Math.ceil(o2.getProductExport() - (o1.getProductExport())));
                };
                break;
            }
            default:{
                Collections.sort(list, (o1, o2) -> o2.getCreateDate().compareTo(o1.getCreateDate()));
                break;
            }

        }

        statisticDateFormDto.getPage().setCount(getCountPage(list));
        int page = statisticDateFormDto.getPage().getCurrentPage() == 0 ? 0 : statisticDateFormDto.getPage().getCurrentPage() - 1;
        int limit = statisticDateFormDto.getPage().getLimit();
        int start = limit * page, end = limit * (page + 1) <= getCountPage(list) ? limit * (page + 1) : getCountPage(list);

        list = list.subList(start, end);

        return list;

    }

    @Override
    public List<StatisticInventoryTableDto> getSatisticInventory(StatisticInventoryFormDto statisticInventoryFormDto) {
        List<StatisticInventoryTableDto> list = reportDao.getSatisticInventory(statisticInventoryFormDto.getDate(), statisticInventoryFormDto.getInventory(), statisticInventoryFormDto.getUser());
        if (list.size() == 0) return list;

        String sort = statisticInventoryFormDto.getSort().getColumn() == null ? "" : statisticInventoryFormDto.getSort().getColumn();
        boolean asc = statisticInventoryFormDto.getSort().isAsc();

        switch (sort) {
            case "inventory": {
                if (asc) {
                    Collections.sort(list, ((o1, o2) -> o1.getInventory().compareTo(o2.getInventory())));
                } else {
                    Collections.sort(list, ((o1, o2) -> o2.getInventory().compareTo(o1.getInventory())));
                }
                break;
            }
            case "transferInNum": {
                if (asc) {
                    Collections.sort(list, ((o1, o2) -> o1.getTransferInNum() - o2.getTransferInNum()));
                } else {
                    Collections.sort(list, ((o1, o2) -> o2.getTransferInNum() - o1.getTransferInNum()));
                }
                break;
            }
            case "transferOutNum": {
                if (asc) {
                    Collections.sort(list, ((o1, o2) -> o1.getTransferOutNum() - o2.getTransferOutNum()));
                } else {
                    Collections.sort(list, ((o1, o2) -> o2.getTransferOutNum() - o1.getTransferOutNum()));
                }
                break;
            }
            case "productOutput": {
                if (asc) {
                    Collections.sort(list, ((o1, o2) -> o1.getProductOutput() - o2.getProductOutput()));
                } else {
                    Collections.sort(list, ((o1, o2) -> o2.getProductOutput() - o1.getProductOutput()));
                }
                break;
            }
            case "productInput": {
                if (asc) {
                    Collections.sort(list, ((o1, o2) -> o1.getProductInput() - o2.getProductInput()));
                } else {
                    Collections.sort(list, ((o1, o2) -> o2.getProductInput() - o1.getProductInput()));
                }
                break;
            }
            case "productInNum": {
                if (asc) {
                    Collections.sort(list, ((o1, o2) -> o1.getProductInNum() - o2.getProductInNum()));
                } else {
                    Collections.sort(list, ((o1, o2) -> o2.getProductInNum() - o1.getProductInNum()));
                }
            }
            case "productOutNum": {
                if (asc) {
                    Collections.sort(list, ((o1, o2) -> o1.getProductOutNum() - o2.getProductOutNum()));
                } else {
                    Collections.sort(list, ((o1, o2) -> o2.getProductOutNum() - o1.getProductOutNum()));
                }
                break;
            }
            case "productInTotal": {
                if (asc) {
                    Collections.sort(list, ((o1, o2) -> o1.getProductInTotal() - o2.getProductInTotal()));
                } else {
                    Collections.sort(list, ((o1, o2) -> o2.getProductInTotal() - o1.getProductInTotal()));
                }
                break;
            }
            case "productOutTotal": {
                if (asc) {
                    Collections.sort(list, ((o1, o2) -> o1.getProductOutTotal() - o2.getProductOutTotal()));
                } else {
                    Collections.sort(list, ((o1, o2) -> o2.getProductOutTotal() - o1.getProductOutTotal()));
                }
                break;
            }
            default:{
                Collections.sort(list, ((o1, o2) -> o1.getInventory().compareTo(o2.getInventory())));
                break;
            }
        }

        statisticInventoryFormDto.getPage().setCount(getCountPage(list));

        int page = statisticInventoryFormDto.getPage().getCurrentPage() == 0 ? 0 : statisticInventoryFormDto.getPage().getCurrentPage() - 1;
        int limit = statisticInventoryFormDto.getPage().getLimit();
        int start = limit * page, end = limit * (page + 1) <= getCountPage(list) ? limit * (page + 1) : getCountPage(list);

        list = list.subList(start, end);

        return list;
    }

    @Override
    public <T> int getCountPage(List<T> t) {
        return (int) t.stream().count();
    }

}
