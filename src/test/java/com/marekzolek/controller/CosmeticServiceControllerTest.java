package com.marekzolek.controller;

import com.marekzolek.model.CosmeticService;
import com.marekzolek.model.CosmeticServiceCategory;
import com.marekzolek.service.CosmeticServiceService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
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
public class CosmeticServiceControllerTest {

    @InjectMocks
    private CosmeticServiceController cosmeticServiceController;

    @Mock
    private CosmeticServiceService cosmeticServiceService;

    private MockMvc mockMvc;

    @Before
    public void init() {
        mockMvc = MockMvcBuilders.standaloneSetup(cosmeticServiceController).build();
    }

    @Test
    public void add() {

        CosmeticService cosmeticService = new CosmeticService();

        when(cosmeticServiceService.add(cosmeticService)).thenReturn(cosmeticService);

        assertEquals(cosmeticService, cosmeticServiceController.add(cosmeticService));
    }

    @Test
    public void delete() {

        cosmeticServiceService.delete(1L);
        verify(cosmeticServiceService, times(1)).delete(1L);
    }

    @Test
    public void findOne() throws Exception {

        CosmeticService cosmeticService = new CosmeticService("AA", 250, "M", null);

        when(cosmeticServiceService.findOne(1L)).thenReturn(cosmeticService);

        mockMvc.perform(MockMvcRequestBuilders.get("/cosmeticService/1"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath(".name").value("AA"));

        assertEquals(cosmeticService, cosmeticServiceController.findOne(1L));
    }

    @Test
    public void findAllCosmeticServices() throws Exception {

        CosmeticService cosmeticService1 = new CosmeticService("AA", 250, "M", null);
        CosmeticService cosmeticService2 = new CosmeticService("BB", 200, "F", null);

        List<CosmeticService> cosmeticServices = new ArrayList<>();
        cosmeticServices.add(cosmeticService1);
        cosmeticServices.add(cosmeticService2);

        when(cosmeticServiceService.findAll()).thenReturn(cosmeticServices);

        mockMvc.perform(MockMvcRequestBuilders.get("/cosmeticService/all"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].name").value("AA"));

        assertEquals(cosmeticServices, cosmeticServiceController.findAllCosmeticServices());
    }

    @Test
    public void findCosmeticServicesWithCategoryId() throws Exception {

        CosmeticServiceCategory cosmeticServiceCategory = new CosmeticServiceCategory(1L, null, null);

        CosmeticService cosmeticService1 = new CosmeticService("AA", 250, "M", cosmeticServiceCategory);
        CosmeticService cosmeticService2 = new CosmeticService("BB", 200, "F", cosmeticServiceCategory);

        List<CosmeticService> cosmeticServices = new ArrayList<>();
        cosmeticServices.add(cosmeticService1);
        cosmeticServices.add(cosmeticService2);

        when(cosmeticServiceService.findCosmeticServicesWithCategoryId(1L)).thenReturn(cosmeticServices);

        mockMvc.perform(MockMvcRequestBuilders.get("/cosmeticService/withCategoryId/1"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].name").value("AA"));

        assertEquals(cosmeticServices, cosmeticServiceController.findCosmeticServicesWithCategoryId(1L));
    }

    @Test
    public void findAllByType() throws Exception {

        CosmeticService cosmeticService1 = new CosmeticService("AA", 250, "M", null);
        CosmeticService cosmeticService2 = new CosmeticService("BB", 200, "F", null);

        List<CosmeticService> cosmeticServices = new ArrayList<>();
        cosmeticServices.add(cosmeticService1);

        when(cosmeticServiceService.findAllByType("M")).thenReturn(cosmeticServices);

        mockMvc.perform(MockMvcRequestBuilders.get("/cosmeticService/type?type=M"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].name").value("AA"));

        assertEquals(cosmeticServices, cosmeticServiceController.findAllByType("M"));
    }

    @Test
    public void findTheMostExpensiveService() throws Exception {

        CosmeticService cosmeticService = new CosmeticService("AA", 250, "M", null);

        List<CosmeticService> cosmeticServices = new ArrayList<>();
        cosmeticServices.add(cosmeticService);

        when(cosmeticServiceService.findTheMostExpensiveService()).thenReturn(cosmeticService);

        mockMvc.perform(MockMvcRequestBuilders.get("/cosmeticService/theMostExpensiveService"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath(".name").value("AA"));

        assertEquals(cosmeticService, cosmeticServiceController.findTheMostExpensiveService());
    }

    @Test
    public void findTheCheapestService() throws Exception {

        CosmeticService cosmeticService = new CosmeticService("AA", 250, "M", null);

        List<CosmeticService> cosmeticServices = new ArrayList<>();
        cosmeticServices.add(cosmeticService);

        when(cosmeticServiceService.findCheapestService()).thenReturn(cosmeticService);

        mockMvc.perform(MockMvcRequestBuilders.get("/cosmeticService/theCheapestService"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath(".name").value("AA"));

        assertEquals(cosmeticService, cosmeticServiceController.findTheCheapestService());
    }

    @Test
    public void countServicesByType() throws Exception {

        CosmeticService cosmeticService1 = new CosmeticService("AA", 250, "M", null);
        CosmeticService cosmeticService2 = new CosmeticService("BB", 200, "F", null);

        List<CosmeticService> cosmeticServices = new ArrayList<>();
        cosmeticServices.add(cosmeticService1);
        cosmeticServices.add(cosmeticService2);

        when(cosmeticServiceService.countServicesByType("M")).thenReturn(1);

        mockMvc.perform(MockMvcRequestBuilders.get("/cosmeticService/numberOfServicesByType?type=M"))
                .andExpect(MockMvcResultMatchers.status().isOk());

        assertEquals(Optional.of(1).get(), cosmeticServiceController.countServicesByType("M"));
    }
}