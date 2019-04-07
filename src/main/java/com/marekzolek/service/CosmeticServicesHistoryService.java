package com.marekzolek.service;

import com.marekzolek.exception.CosmeticServiceNotFoundException;
import com.marekzolek.exception.CustomerNotFoundException;
import com.marekzolek.exception.HistoryNotFoundException;
import com.marekzolek.model.CosmeticService;
import com.marekzolek.model.CosmeticServicesHistory;

import java.util.List;

public interface CosmeticServicesHistoryService {

    CosmeticServicesHistory add(final CosmeticServicesHistory cosmeticServicesHistory);

    void delete(final Long id) throws HistoryNotFoundException;

    CosmeticServicesHistory findOne(final Long id);

    List<CosmeticServicesHistory> findAll();

    Integer sumOfPricesAllServicesOfGivenCustomerTest(final Long customerId) throws CustomerNotFoundException;

    Integer countEachServicesOfGivenCustomer(final Long customerId) throws CustomerNotFoundException;

    CosmeticService theMostPopularService() throws CosmeticServiceNotFoundException;

    CosmeticService leastPopularService() throws CosmeticServiceNotFoundException;
}
