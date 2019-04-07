package com.marekzolek.service;

import com.marekzolek.exception.CosmeticServiceNotFoundException;
import com.marekzolek.exception.CustomerNotFoundException;
import com.marekzolek.model.CosmeticService;
import com.marekzolek.model.CosmeticServicesHistory;
import com.marekzolek.model.Customer;
import com.marekzolek.repository.CosmeticServicesHistoryRepository;
import com.marekzolek.repository.CustomerRepository;
import org.junit.Before;
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

    private CosmeticServicesHistory cosmeticServicesHistory1;
    private CosmeticServicesHistory cosmeticServicesHistory2;
    private CosmeticServicesHistory cosmeticServicesHistory3;
    private CosmeticService cosmeticService1;
    private CosmeticService cosmeticService2;

    private List<CosmeticServicesHistory> cosmeticServicesHistories = new ArrayList<>();

    @InjectMocks
    private CosmeticServicesHistoryServiceImpl cosmeticServicesHistoryService;

    @Mock
    private CosmeticServicesHistoryRepository cosmeticServicesHistoryRepository;

    @Mock
    private CustomerRepository customerRepository;

    @Before
    public void init() {

        cosmeticService1 = new CosmeticService.CosmeticServiceBuilder()
                .name("AA")
                .price(250)
                .type("M")
                .category(null)
                .build();
        cosmeticService2 = new CosmeticService.CosmeticServiceBuilder()
                .name("BB")
                .price(200)
                .type("F")
                .category(null)
                .build();

        cosmeticServicesHistory1 = new CosmeticServicesHistory.CosmeticServicesHistoryBuilder()
                .cosmeticService(cosmeticService1)
                .date("2017-03-04")
                .build();

        cosmeticServicesHistory2 = new CosmeticServicesHistory.CosmeticServicesHistoryBuilder()
                .cosmeticService(cosmeticService1)
                .date("2017-02-04")
                .build();

        cosmeticServicesHistory3 = new CosmeticServicesHistory.CosmeticServicesHistoryBuilder()
                .cosmeticService(cosmeticService2)
                .date("2017-02-04")
                .build();


        cosmeticServicesHistories.add(cosmeticServicesHistory1);
        cosmeticServicesHistories.add(cosmeticServicesHistory2);
        cosmeticServicesHistories.add(cosmeticServicesHistory3);
    }

    @Test
    public void add() {

        when(cosmeticServicesHistoryRepository.save(cosmeticServicesHistory1)).thenReturn(cosmeticServicesHistory1);

        assertEquals(cosmeticServicesHistory1, cosmeticServicesHistoryService.add(cosmeticServicesHistory1));
    }

    @Test
    public void delete() {

        cosmeticServicesHistoryRepository.deleteById(1L);
        Mockito.verify(cosmeticServicesHistoryRepository, times(1)).deleteById(1L);
    }

    @Test
    public void findOne() {

        when(cosmeticServicesHistoryRepository.findById(1L)).thenReturn(java.util.Optional.of(cosmeticServicesHistory1));

        assertEquals(cosmeticServicesHistory1, cosmeticServicesHistoryService.findOne(1L));
    }

    @Test
    public void findAll() {

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

        when(cosmeticServicesHistoryRepository.findAll()).thenReturn(cosmeticServicesHistories);

        assertEquals(cosmeticService1, cosmeticServicesHistoryService.theMostPopularService());
    }

    @Test
    public void leastPopularService() throws CosmeticServiceNotFoundException {

        when(cosmeticServicesHistoryRepository.findAll()).thenReturn(cosmeticServicesHistories);

        assertEquals(cosmeticService2, cosmeticServicesHistoryService.leastPopularService());
    }
}