package com.marekzolek.service;

import com.marekzolek.exception.CosmeticServiceNotFoundException;
import com.marekzolek.exception.CustomerNotFoundException;
import com.marekzolek.model.CosmeticService;
import com.marekzolek.model.CosmeticServicesHistory;
import com.marekzolek.model.Customer;
import com.marekzolek.repository.CosmeticServicesHistoryRepository;
import com.marekzolek.repository.CustomerRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class CosmeticServicesHistoryServiceImplTest {

    @InjectMocks
    private CosmeticServicesHistoryServiceImpl cosmeticServicesHistoryService;

    @Mock
    private CosmeticServicesHistoryRepository cosmeticServicesHistoryRepository;

    @Mock
    private CustomerRepository customerRepository;

    @Test
    public void add() {

        CosmeticServicesHistory cosmeticServicesHistory = new CosmeticServicesHistory();

        when(cosmeticServicesHistoryRepository.save(cosmeticServicesHistory)).thenReturn(cosmeticServicesHistory);

        assertEquals(cosmeticServicesHistory, cosmeticServicesHistoryService.add(cosmeticServicesHistory));
    }

    @Test
    public void delete() {

        cosmeticServicesHistoryRepository.deleteById(1L);
        Mockito.verify(cosmeticServicesHistoryRepository, times(1)).deleteById(1L);
    }

    @Test
    public void findOne() {

        CosmeticServicesHistory cosmeticServicesHistory = new CosmeticServicesHistory();

        when(cosmeticServicesHistoryRepository.findById(1L)).thenReturn(java.util.Optional.of(cosmeticServicesHistory));

        assertEquals(cosmeticServicesHistory, cosmeticServicesHistoryService.findOne(1L));
    }

    @Test
    public void findAll() {

        CosmeticServicesHistory cosmeticServicesHistory1 = new CosmeticServicesHistory();
        CosmeticServicesHistory cosmeticServicesHistory2 = new CosmeticServicesHistory();

        List<CosmeticServicesHistory> cosmeticServicesHistories = new ArrayList<>();
        cosmeticServicesHistories.add(cosmeticServicesHistory1);
        cosmeticServicesHistories.add(cosmeticServicesHistory2);

        when(cosmeticServicesHistoryRepository.findAll()).thenReturn(cosmeticServicesHistories);

        assertEquals(cosmeticServicesHistories, cosmeticServicesHistoryService.findAll());
    }

    @Test
    public void sumOfPricesAllServicesOfGivenCustomerTest() throws CustomerNotFoundException {

        Customer customer = new Customer();

        when(customerRepository.findById(1L)).thenReturn(Optional.of(customer));
        when(cosmeticServicesHistoryRepository.findTotalPriceOfServicesOfGivenCustomer(1L)).thenReturn(200);

        assertEquals(Optional.of(200).get(), cosmeticServicesHistoryService.sumOfPricesAllServicesOfGivenCustomerTest(1L));
    }

    @Test
    public void countEachServicesOfGivenCustomer() throws CustomerNotFoundException {

        Customer customer = new Customer();

        when(customerRepository.findById(1L)).thenReturn(Optional.of(customer));
        when(cosmeticServicesHistoryRepository.countEachServicesOfGivenCustomer(1L)).thenReturn(3);

        assertEquals(Optional.of(3).get(), cosmeticServicesHistoryService.countEachServicesOfGivenCustomer(1L));

    }

    @Test
    public void theMostPopularService() throws CosmeticServiceNotFoundException {

        CosmeticService cosmeticService1 = new CosmeticService();
        CosmeticService cosmeticService2 = new CosmeticService();

        CosmeticServicesHistory cosmeticServicesHistory1 = new CosmeticServicesHistory(cosmeticService1, null, null);
        CosmeticServicesHistory cosmeticServicesHistory2 = new CosmeticServicesHistory(cosmeticService1, null, null);
        CosmeticServicesHistory cosmeticServicesHistory3 = new CosmeticServicesHistory(cosmeticService2, null, null);

        List<CosmeticServicesHistory> cosmeticServicesHistories = new ArrayList<>();
        cosmeticServicesHistories.add(cosmeticServicesHistory1);
        cosmeticServicesHistories.add(cosmeticServicesHistory2);
        cosmeticServicesHistories.add(cosmeticServicesHistory3);

        when(cosmeticServicesHistoryRepository.findAll()).thenReturn(cosmeticServicesHistories);

        assertEquals(cosmeticService1, cosmeticServicesHistoryService.theMostPopularService());
    }

    @Test
    public void leastPopularService() throws CosmeticServiceNotFoundException {

        CosmeticService cosmeticService1 = new CosmeticService();
        CosmeticService cosmeticService2 = new CosmeticService();

        CosmeticServicesHistory cosmeticServicesHistory1 = new CosmeticServicesHistory(cosmeticService1, null, null);
        CosmeticServicesHistory cosmeticServicesHistory2 = new CosmeticServicesHistory(cosmeticService1, null, null);
        CosmeticServicesHistory cosmeticServicesHistory3 = new CosmeticServicesHistory(cosmeticService2, null, null);

        List<CosmeticServicesHistory> cosmeticServicesHistories = new ArrayList<>();
        cosmeticServicesHistories.add(cosmeticServicesHistory1);
        cosmeticServicesHistories.add(cosmeticServicesHistory2);
        cosmeticServicesHistories.add(cosmeticServicesHistory3);

        when(cosmeticServicesHistoryRepository.findAll()).thenReturn(cosmeticServicesHistories);

        assertEquals(cosmeticService2, cosmeticServicesHistoryService.theMostPopularService());
    }
}