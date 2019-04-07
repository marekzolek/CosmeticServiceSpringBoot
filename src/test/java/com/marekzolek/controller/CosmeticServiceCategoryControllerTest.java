package com.marekzolek.controller;

import com.marekzolek.model.CosmeticService;
import com.marekzolek.model.CosmeticServiceCategory;
import com.marekzolek.service.CosmeticServiceCategoryService;
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

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class CosmeticServiceCategoryControllerTest {

    CosmeticService cosmeticService1;
    CosmeticService cosmeticService2;
    CosmeticServiceCategory cosmeticServiceCategory1;
    CosmeticServiceCategory cosmeticServiceCategory2;


    List<CosmeticService> cosmeticServices1 = new ArrayList<>();

    List<CosmeticService> cosmeticServices2 = new ArrayList<>();

    List<CosmeticServiceCategory> cosmeticServiceCategories = new ArrayList<>();


    @InjectMocks
    private CosmeticServiceCategoryController cosmeticServiceCategoryController;

    @Mock
    private CosmeticServiceCategoryService cosmeticServiceCategoryService;

    private MockMvc mockMvc;

    @Before
    public void init() {

        mockMvc = MockMvcBuilders.standaloneSetup(cosmeticServiceCategoryController).build();

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

        cosmeticServices1.add(cosmeticService1);

        cosmeticServices2.add(cosmeticService1);
        cosmeticServices2.add(cosmeticService2);

        cosmeticServiceCategory1 = new CosmeticServiceCategory.CosmeticServiceCategoryBuilder()
                .id(1L)
                .name("AA")
                .cosmeticServices(cosmeticServices1)
                .build();
        cosmeticServiceCategory2 = new CosmeticServiceCategory.CosmeticServiceCategoryBuilder()
                .id(2L)
                .name("BB")
                .cosmeticServices(cosmeticServices2)
                .build();

        cosmeticServiceCategories.add(cosmeticServiceCategory1);
        cosmeticServiceCategories.add(cosmeticServiceCategory2);
    }


    @Test
    public void add() {

        when(cosmeticServiceCategoryService.add(cosmeticServiceCategory1)).thenReturn(cosmeticServiceCategory1);

        assertEquals(cosmeticServiceCategory1, cosmeticServiceCategoryController.add(cosmeticServiceCategory1));
    }

    @Test
    public void delete() {

        cosmeticServiceCategoryService.delete(1L);
        verify(cosmeticServiceCategoryService, times(1)).delete(1L);
    }

    @Test
    public void findOne() throws Exception {

        when(cosmeticServiceCategoryService.findOne(1L)).thenReturn(cosmeticServiceCategory1);

        mockMvc.perform(MockMvcRequestBuilders.get("/cosmeticServiceCategory/1"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath(".name").value("AA"));

        assertEquals(cosmeticServiceCategory1, cosmeticServiceCategoryController.findOne(1L));
    }

    @Test
    public void findAll() throws Exception {

        when(cosmeticServiceCategoryService.findAll()).thenReturn(cosmeticServiceCategories);

        mockMvc.perform(MockMvcRequestBuilders.get("/cosmeticServiceCategory/all"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].name").value("AA"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].name").value("BB"));

        assertEquals(cosmeticServiceCategories, cosmeticServiceCategoryController.findAll());
    }

    @Test
    public void namesOfCategoryServiceOfGivenCustomer() throws Exception {

        when(cosmeticServiceCategoryService.namesOfCategoryServiceOfGivenCustomer(1L)).thenReturn(cosmeticServiceCategories);

        mockMvc.perform(MockMvcRequestBuilders.get("/cosmeticServiceCategory/namesOfCategoryServiceOfGivenCustomer?customerId=1"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].name").value("AA"));

        assertEquals(cosmeticServiceCategories, cosmeticServiceCategoryController.namesOfCategoryServiceOfGivenCustomer(1L));
    }

    @Test
    public void findAllByServiceType() throws Exception {

        when(cosmeticServiceCategoryService.findAllByServiceType("M")).thenReturn(cosmeticServiceCategories);

        mockMvc.perform(MockMvcRequestBuilders.get("/cosmeticServiceCategory/havingServicesType?type=M"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].id").value(1L));

        assertEquals(cosmeticServiceCategories, cosmeticServiceCategoryController.findAllByServiceType("M"));
    }

    @Test
    public void categoryWithTheLargestNumberOfServices() throws Exception {

        when(cosmeticServiceCategoryService.categoryWithTheLargestNumberOfServices()).thenReturn(cosmeticServiceCategory2);

        mockMvc.perform(MockMvcRequestBuilders.get("/cosmeticServiceCategory/categoryWithTheLargestNumberOfServices"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath(".name").value("BB"));

        assertEquals(cosmeticServiceCategory2, cosmeticServiceCategoryController.categoryWithTheLargestNumberOfServices());
    }

    @Test
    public void categoryWithTheLeastNumberOfServices() throws Exception {

        when(cosmeticServiceCategoryService.categoryWithTheLeastNumberOfServices()).thenReturn(cosmeticServiceCategory1);

        mockMvc.perform(MockMvcRequestBuilders.get("/cosmeticServiceCategory/categoryWithTheLeastNumberOfServices"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath(".name").value("AA"));

        assertEquals(cosmeticServiceCategory1, cosmeticServiceCategoryController.categoryWithTheLeastNumberOfServices());
    }
}