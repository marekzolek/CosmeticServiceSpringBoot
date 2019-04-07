package com.marekzolek.controller;

import com.marekzolek.exception.HistoryNotFoundException;
import com.marekzolek.model.CosmeticService;
import com.marekzolek.model.CosmeticServicesHistory;
import com.marekzolek.repository.CosmeticServicesHistoryRepository;
import com.marekzolek.service.CosmeticServicesHistoryService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class CosmeticServiceHistoryControllerTest {

    CosmeticServicesHistory cosmeticServicesHistory1;
    CosmeticServicesHistory cosmeticServicesHistory2;
    CosmeticServicesHistory cosmeticServicesHistory3;
    CosmeticService cosmeticService1;
    CosmeticService cosmeticService2;

    List<CosmeticServicesHistory> cosmeticServicesHistories = new ArrayList<>();

    @InjectMocks
    private CosmeticServiceHistoryController cosmeticServiceHistoryController;

    @Mock
    private CosmeticServicesHistoryService cosmeticServicesHistoryService;

    private MockMvc mockMvc;

    @Autowired
    private CosmeticServicesHistoryRepository cosmeticServicesHistoryRepository;

    @Before
    public void init() {

        mockMvc = MockMvcBuilders.standaloneSetup(cosmeticServiceHistoryController).build();

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

        when(cosmeticServicesHistoryService.add(cosmeticServicesHistory1)).thenReturn(cosmeticServicesHistory1);

        assertEquals(cosmeticServicesHistory1, cosmeticServiceHistoryController.add(cosmeticServicesHistory1));
    }

    @Test
    public void delete() throws HistoryNotFoundException {

        cosmeticServicesHistoryService.delete(1L);
        verify(cosmeticServicesHistoryService, times(1)).delete(1L);
    }

    @Test
    public void findOne() throws Exception {

        when(cosmeticServicesHistoryService.findOne(1L)).thenReturn(cosmeticServicesHistory1);

        mockMvc.perform(MockMvcRequestBuilders.get("/cosmeticServicesHistory/1")).
                andExpect(MockMvcResultMatchers.status().isOk()).
                andExpect(MockMvcResultMatchers.jsonPath("$.date").value("2017-03-04"));

        assertEquals(cosmeticServicesHistory1, cosmeticServiceHistoryController.findOne(1L));
    }

    @Test
    public void findAll() throws Exception {

        when(cosmeticServicesHistoryService.findAll()).thenReturn(cosmeticServicesHistories);

        mockMvc.perform(MockMvcRequestBuilders.get("/cosmeticServicesHistory/all")).
                andExpect(MockMvcResultMatchers.status().isOk()).
                andExpect(MockMvcResultMatchers.jsonPath("$[1].date").value("2017-02-04"));

        assertEquals(cosmeticServicesHistories, cosmeticServiceHistoryController.findAll());

    }

    @Test
    public void sum() throws Exception {

        when(cosmeticServicesHistoryService.sumOfPricesAllServicesOfGivenCustomerTest(1L)).thenReturn(300);

        mockMvc.perform(MockMvcRequestBuilders.get("/cosmeticServicesHistory/sum?customerId=1")).
                andExpect(MockMvcResultMatchers.status().isOk());

        assertEquals(Optional.of(300).get(), cosmeticServiceHistoryController.sum(1L));
    }

    @Test
    public void countEachServicesOfGivenCustomer() throws Exception {

        when(cosmeticServicesHistoryService.countEachServicesOfGivenCustomer(1L)).thenReturn(5);

        mockMvc.perform(MockMvcRequestBuilders.get("/cosmeticServicesHistory/numberOfServiceOfCustomer?customerId=1"))
                .andExpect(MockMvcResultMatchers.status().isOk());

        assertEquals(Optional.of(5).get(), cosmeticServiceHistoryController.countEachServicesOfGivenCustomer(1L));
    }

    @Test
    public void theMostPopularService() throws Exception {

        when(cosmeticServicesHistoryService.theMostPopularService()).thenReturn(cosmeticService1);

        mockMvc.perform(MockMvcRequestBuilders.get("/cosmeticServicesHistory/theMostPopularService"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.price").value(250));

        assertEquals(cosmeticService1, cosmeticServiceHistoryController.theMostPopularService());
    }

    @Test
    public void leastPopularService() throws Exception {

        when(cosmeticServicesHistoryService.leastPopularService()).thenReturn(cosmeticService2);

        mockMvc.perform(MockMvcRequestBuilders.get("/cosmeticServicesHistory/leastPopularService"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("BB"));

        assertEquals(cosmeticService2, cosmeticServiceHistoryController.leastPopularService());
    }
}