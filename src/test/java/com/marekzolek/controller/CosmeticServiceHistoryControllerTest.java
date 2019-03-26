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
    }

    @Test
    public void add() {

        CosmeticServicesHistory cosmeticServicesHistory = new CosmeticServicesHistory();

        when(cosmeticServicesHistoryService.add(cosmeticServicesHistory)).thenReturn(cosmeticServicesHistory);

        assertEquals(cosmeticServicesHistory, cosmeticServiceHistoryController.add(cosmeticServicesHistory));
    }

    @Test
    public void delete() throws HistoryNotFoundException {

        cosmeticServicesHistoryService.delete(1L);
        verify(cosmeticServicesHistoryService, times(1)).delete(1L);
    }

    @Test
    public void findOne() throws Exception {

        CosmeticServicesHistory cosmeticServicesHistory = new CosmeticServicesHistory(null, null, "2017-03-04");

        when(cosmeticServicesHistoryService.findOne(1L)).thenReturn(cosmeticServicesHistory);

        mockMvc.perform(MockMvcRequestBuilders.get("/cosmeticServicesHistory/1")).
                andExpect(MockMvcResultMatchers.status().isOk()).
                andExpect(MockMvcResultMatchers.jsonPath(".date").value("2017-03-04"));

        assertEquals(cosmeticServicesHistory, cosmeticServiceHistoryController.findOne(1L));
    }

    @Test
    public void findAll() throws Exception {

        CosmeticServicesHistory cosmeticServicesHistory1 = new CosmeticServicesHistory(null, null, "2017-03-04");
        CosmeticServicesHistory cosmeticServicesHistory2 = new CosmeticServicesHistory(null, null, "2017-02-04");

        List<CosmeticServicesHistory> cosmeticServicesHistories = new ArrayList<>();
        cosmeticServicesHistories.add(cosmeticServicesHistory1);
        cosmeticServicesHistories.add(cosmeticServicesHistory2);

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

        CosmeticService cosmeticService1 = new CosmeticService("Depilacja", 250, "F", null);
        CosmeticService cosmeticService2 = new CosmeticService();

        CosmeticServicesHistory cosmeticServicesHistory1 = new CosmeticServicesHistory(cosmeticService1, null, null);
        CosmeticServicesHistory cosmeticServicesHistory2 = new CosmeticServicesHistory(cosmeticService1, null, null);
        CosmeticServicesHistory cosmeticServicesHistory3 = new CosmeticServicesHistory(cosmeticService2, null, null);

        List<CosmeticServicesHistory> cosmeticServicesHistories = new ArrayList<>();
        cosmeticServicesHistories.add(cosmeticServicesHistory1);
        cosmeticServicesHistories.add(cosmeticServicesHistory2);
        cosmeticServicesHistories.add(cosmeticServicesHistory3);

        when(cosmeticServicesHistoryService.theMostPopularService()).thenReturn(cosmeticService1);

        mockMvc.perform(MockMvcRequestBuilders.get("/cosmeticServicesHistory/theMostPopularService"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath(".price").value(250));

        assertEquals(cosmeticService1, cosmeticServiceHistoryController.theMostPopularService());
    }

    @Test
    public void leastPopularService() throws Exception {

        CosmeticService cosmeticService1 = new CosmeticService("Depilacja", 250, "F", null);
        CosmeticService cosmeticService2 = new CosmeticService("Depilacja Męska", 200, "F", null);

        CosmeticServicesHistory cosmeticServicesHistory1 = new CosmeticServicesHistory(cosmeticService1, null, null);
        CosmeticServicesHistory cosmeticServicesHistory2 = new CosmeticServicesHistory(cosmeticService1, null, null);
        CosmeticServicesHistory cosmeticServicesHistory3 = new CosmeticServicesHistory(cosmeticService2, null, null);

        List<CosmeticServicesHistory> cosmeticServicesHistories = new ArrayList<>();
        cosmeticServicesHistories.add(cosmeticServicesHistory1);
        cosmeticServicesHistories.add(cosmeticServicesHistory2);
        cosmeticServicesHistories.add(cosmeticServicesHistory3);

        when(cosmeticServicesHistoryService.leastPopularService()).thenReturn(cosmeticService2);

        mockMvc.perform(MockMvcRequestBuilders.get("/cosmeticServicesHistory/leastPopularService"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath(".name").value("Depilacja Męska"));

        assertEquals(cosmeticService2, cosmeticServiceHistoryController.leastPopularService());
    }
}