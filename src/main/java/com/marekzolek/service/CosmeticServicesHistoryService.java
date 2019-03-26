package com.marekzolek.service;

import com.marekzolek.exception.CosmeticServiceNotFoundException;
import com.marekzolek.exception.CustomerNotFoundException;
import com.marekzolek.exception.HistoryNotFoundException;
import com.marekzolek.model.CosmeticService;
import com.marekzolek.model.CosmeticServiceCategory;
import com.marekzolek.model.CosmeticServicesHistory;
import com.marekzolek.model.Customer;

import java.util.List;

public interface CosmeticServicesHistoryService {

    CosmeticServicesHistory add(CosmeticServicesHistory cosmeticServicesHistory);

    void delete(Long id) throws HistoryNotFoundException;

    CosmeticServicesHistory findOne(Long id);

    List<CosmeticServicesHistory> findAll();

    Integer sumOfPricesAllServicesOfGivenCustomerTest(Long customerId) throws CustomerNotFoundException;

    Integer countEachServicesOfGivenCustomer(Long customerId) throws CustomerNotFoundException;

    CosmeticService theMostPopularService() throws CosmeticServiceNotFoundException;

    CosmeticService leastPopularService() throws CosmeticServiceNotFoundException;
}
