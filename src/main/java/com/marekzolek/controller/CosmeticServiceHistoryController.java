package com.marekzolek.controller;


import com.marekzolek.exception.CosmeticServiceNotFoundException;
import com.marekzolek.exception.CustomerNotFoundException;
import com.marekzolek.exception.HistoryNotFoundException;
import com.marekzolek.model.CosmeticService;
import com.marekzolek.model.CosmeticServicesHistory;
import com.marekzolek.service.CosmeticServicesHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/cosmeticServicesHistory")
public class CosmeticServiceHistoryController {

    @Autowired
    private CosmeticServicesHistoryService cosmeticServicesHistoryService;

    @PostMapping("/add")
    public CosmeticServicesHistory add(@RequestBody final CosmeticServicesHistory cosmeticServicesHistory) {
        return cosmeticServicesHistoryService.add(cosmeticServicesHistory);
    }

    @DeleteMapping("/delete")
    public void delete(@RequestParam final Long id) throws HistoryNotFoundException {
        cosmeticServicesHistoryService.delete(id);
    }

    @GetMapping("/{id}")
    public CosmeticServicesHistory findOne(@PathVariable final Long id) {
        return cosmeticServicesHistoryService.findOne(id);
    }

    @GetMapping("/all")
    public List<CosmeticServicesHistory> findAll() {
        return cosmeticServicesHistoryService.findAll();
    }

    @GetMapping("/sum")
    public Integer sum(@RequestParam final Long customerId) throws CustomerNotFoundException {
        return cosmeticServicesHistoryService.sumOfPricesAllServicesOfGivenCustomerTest(customerId);
    }

    @GetMapping("/numberOfServiceOfCustomer")
    public Integer countEachServicesOfGivenCustomer(@RequestParam final Long customerId) throws CustomerNotFoundException {
        return cosmeticServicesHistoryService.countEachServicesOfGivenCustomer(customerId);
    }

    @GetMapping("/theMostPopularService")
    public CosmeticService theMostPopularService() throws CosmeticServiceNotFoundException {
        return cosmeticServicesHistoryService.theMostPopularService();
    }

    @GetMapping("/leastPopularService")
    public CosmeticService leastPopularService() throws CosmeticServiceNotFoundException {
        return cosmeticServicesHistoryService.leastPopularService();
    }
}
