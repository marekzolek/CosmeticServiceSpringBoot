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

    CosmeticService cosmeticService1;
    CosmeticService cosmeticService2;
    CosmeticServiceCategory cosmeticServiceCategory1;
    List<CosmeticService> cosmeticServices = new ArrayList<>();

    @InjectMocks
    private CosmeticServiceController cosmeticServiceController;

    @Mock
    private CosmeticServiceService cosmeticServiceService;

    private MockMvc mockMvc;

    @Before
    public void init() {

        mockMvc = MockMvcBuilders.standaloneSetup(cosmeticServiceController).build();

        cosmeticServiceCategory1 = new CosmeticServiceCategory.CosmeticServiceCategoryBuilder()
                .id(1L)
                .name("AA")
                .cosmeticServices(null)
                .build();

        cosmeticService1 = new CosmeticService.CosmeticServiceBuilder()
                .name("AA")
                .price(250)
                .type("M")
                .category(cosmeticServiceCategory1)
                .build();
        cosmeticService2 = new CosmeticService.CosmeticServiceBuilder()
                .name("BB")
                .price(200)
                .type("F")
                .category(null)
                .build();

        cosmeticServices.add(cosmeticService1);
        cosmeticServices.add(cosmeticService2);

    }

    @Test
    public void add() {


        when(cosmeticServiceService.add(cosmeticService1)).thenReturn(cosmeticService1);

        assertEquals(cosmeticService1, cosmeticServiceController.add(cosmeticService1));
    }

    @Test
    public void delete() {

        cosmeticServiceService.delete(1L);
        verify(cosmeticServiceService, times(1)).delete(1L);
    }

    @Test
    public void findOne() throws Exception {

        when(cosmeticServiceService.findOne(1L)).thenReturn(cosmeticService1);

        mockMvc.perform(MockMvcRequestBuilders.get("/cosmeticService/1"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("AA"));

        assertEquals(cosmeticService1, cosmeticServiceController.findOne(1L));
    }

    @Test
    public void findAllCosmeticServices() throws Exception {

        when(cosmeticServiceService.findAll()).thenReturn(cosmeticServices);

        mockMvc.perform(MockMvcRequestBuilders.get("/cosmeticService/all"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].name").value("AA"));

        assertEquals(cosmeticServices, cosmeticServiceController.findAllCosmeticServices());
    }

    @Test
    public void findCosmeticServicesWithCategoryId() throws Exception {


        when(cosmeticServiceService.findCosmeticServicesWithCategoryId(1L)).thenReturn(cosmeticServices);

        mockMvc.perform(MockMvcRequestBuilders.get("/cosmeticService/withCategoryId/1"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].name").value("AA"));

        assertEquals(cosmeticServices, cosmeticServiceController.findCosmeticServicesWithCategoryId(1L));
    }

    @Test
    public void findAllByType() throws Exception {

        when(cosmeticServiceService.findAllByType("M")).thenReturn(cosmeticServices);

        mockMvc.perform(MockMvcRequestBuilders.get("/cosmeticService/type?type=M"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].name").value("AA"));

        assertEquals(cosmeticServices, cosmeticServiceController.findAllByType("M"));
    }

    @Test
    public void findTheMostExpensiveService() throws Exception {

        when(cosmeticServiceService.findTheMostExpensiveService()).thenReturn(cosmeticService1);

        mockMvc.perform(MockMvcRequestBuilders.get("/cosmeticService/theMostExpensiveService"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("AA"));

        assertEquals(cosmeticService1, cosmeticServiceController.findTheMostExpensiveService());
    }

    @Test
    public void findTheCheapestService() throws Exception {

        when(cosmeticServiceService.findCheapestService()).thenReturn(cosmeticService1);

        mockMvc.perform(MockMvcRequestBuilders.get("/cosmeticService/theCheapestService"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("AA"));

        assertEquals(cosmeticService1, cosmeticServiceController.findTheCheapestService());
    }

    @Test
    public void countServicesByType() throws Exception {

        when(cosmeticServiceService.countServicesByType("M")).thenReturn(1);

        mockMvc.perform(MockMvcRequestBuilders.get("/cosmeticService/numberOfServicesByType?type=M"))
                .andExpect(MockMvcResultMatchers.status().isOk());

        assertEquals(Optional.of(1).get(), cosmeticServiceController.countServicesByType("M"));
    }
}