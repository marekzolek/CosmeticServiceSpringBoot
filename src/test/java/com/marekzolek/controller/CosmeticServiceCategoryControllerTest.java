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

    @InjectMocks
    private CosmeticServiceCategoryController cosmeticServiceCategoryController;

    @Mock
    private CosmeticServiceCategoryService cosmeticServiceCategoryService;

    private MockMvc mockMvc;

    @Before
    public void init() {
        mockMvc = MockMvcBuilders.standaloneSetup(cosmeticServiceCategoryController).build();
    }

    @Test
    public void add() {

        CosmeticServiceCategory cosmeticServiceCategory = new CosmeticServiceCategory();

        when(cosmeticServiceCategoryService.add(cosmeticServiceCategory)).thenReturn(cosmeticServiceCategory);

        assertEquals(cosmeticServiceCategory, cosmeticServiceCategoryController.add(cosmeticServiceCategory));
    }

    @Test
    public void delete() {

        cosmeticServiceCategoryService.delete(1L);
        verify(cosmeticServiceCategoryService, times(1)).delete(1L);
    }

    @Test
    public void findOne() throws Exception {

        CosmeticServiceCategory cosmeticServiceCategory = new CosmeticServiceCategory(1L, "AA", null);

        when(cosmeticServiceCategoryService.findOne(1L)).thenReturn(cosmeticServiceCategory);

        mockMvc.perform(MockMvcRequestBuilders.get("/cosmeticServiceCategory/1"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath(".name").value("AA"));

        assertEquals(cosmeticServiceCategory, cosmeticServiceCategoryController.findOne(1L));
    }

    @Test
    public void findAll() throws Exception {

        CosmeticServiceCategory cosmeticServiceCategory1 = new CosmeticServiceCategory(1L, "AA", null);
        CosmeticServiceCategory cosmeticServiceCategory2 = new CosmeticServiceCategory();

        List<CosmeticServiceCategory> cosmeticServiceCategories = new ArrayList<>();
        cosmeticServiceCategories.add(cosmeticServiceCategory1);
        cosmeticServiceCategories.add(cosmeticServiceCategory2);

        when(cosmeticServiceCategoryService.findAll()).thenReturn(cosmeticServiceCategories);

        mockMvc.perform(MockMvcRequestBuilders.get("/cosmeticServiceCategory/all"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].name").value("AA"));

        assertEquals(cosmeticServiceCategories, cosmeticServiceCategoryController.findAll());
    }

    @Test
    public void namesOfCategoryServiceOfGivenCustomer() throws Exception {

        CosmeticServiceCategory cosmeticServiceCategory1 = new CosmeticServiceCategory(1L, "AA", null);
        CosmeticServiceCategory cosmeticServiceCategory2 = new CosmeticServiceCategory();

        List<CosmeticServiceCategory> cosmeticServiceCategories = new ArrayList<>();
        cosmeticServiceCategories.add(cosmeticServiceCategory1);
        cosmeticServiceCategories.add(cosmeticServiceCategory2);

        when(cosmeticServiceCategoryService.namesOfCategoryServiceOfGivenCustomer(1L)).thenReturn(cosmeticServiceCategories);

        mockMvc.perform(MockMvcRequestBuilders.get("/cosmeticServiceCategory/namesOfCategoryServiceOfGivenCustomer?customerId=1"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].name").value("AA"));

        assertEquals(cosmeticServiceCategories, cosmeticServiceCategoryController.namesOfCategoryServiceOfGivenCustomer(1L));
    }

    @Test
    public void findAllByServiceType() throws Exception {

        CosmeticService cosmeticService1 = new CosmeticService("AA", 250, "M", null);
        CosmeticService cosmeticService2 = new CosmeticService("BB", 200, "F", null);

        List<CosmeticService> cosmeticServices1 = new ArrayList<>();
        cosmeticServices1.add(cosmeticService1);
        List<CosmeticService> cosmeticServices2 = new ArrayList<>();
        cosmeticServices2.add(cosmeticService2);

        CosmeticServiceCategory cosmeticServiceCategory1 = new CosmeticServiceCategory(1L, null, cosmeticServices1);
        CosmeticServiceCategory cosmeticServiceCategory2 = new CosmeticServiceCategory(2L, null, cosmeticServices2);

        List<CosmeticServiceCategory> cosmeticServiceCategories = new ArrayList<>();
        cosmeticServiceCategories.add(cosmeticServiceCategory1);

        when(cosmeticServiceCategoryService.findAllByServiceType("M")).thenReturn(cosmeticServiceCategories);

        mockMvc.perform(MockMvcRequestBuilders.get("/cosmeticServiceCategory/havingServicesType?type=M"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].id").value(1L));

        assertEquals(cosmeticServiceCategories, cosmeticServiceCategoryController.findAllByServiceType("M"));
    }

    @Test
    public void categoryWithTheLargestNumberOfServices() throws Exception {

        CosmeticService cosmeticService1 = new CosmeticService();
        CosmeticService cosmeticService2 = new CosmeticService();

        List<CosmeticService> cosmeticServices1 = new ArrayList<>();
        cosmeticServices1.add(cosmeticService1);

        List<CosmeticService> cosmeticServices2 = new ArrayList<>();
        cosmeticServices2.add(cosmeticService1);
        cosmeticServices2.add(cosmeticService2);

        CosmeticServiceCategory cosmeticServiceCategory1 = new CosmeticServiceCategory(1L, "AA", cosmeticServices1);
        CosmeticServiceCategory cosmeticServiceCategory2 = new CosmeticServiceCategory(4L, "BB", cosmeticServices2);

        List<CosmeticServiceCategory> cosmeticServiceCategories = new ArrayList<>();
        cosmeticServiceCategories.add(cosmeticServiceCategory1);
        cosmeticServiceCategories.add(cosmeticServiceCategory2);

        when(cosmeticServiceCategoryService.categoryWithTheLargestNumberOfServices()).thenReturn(cosmeticServiceCategory2);

        mockMvc.perform(MockMvcRequestBuilders.get("/cosmeticServiceCategory/categoryWithTheLargestNumberOfServices"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath(".name").value("BB"));

        assertEquals(cosmeticServiceCategory2, cosmeticServiceCategoryController.categoryWithTheLargestNumberOfServices());
    }

    @Test
    public void categoryWithTheLeastNumberOfServices() throws Exception {

        CosmeticService cosmeticService1 = new CosmeticService();
        CosmeticService cosmeticService2 = new CosmeticService();

        List<CosmeticService> cosmeticServices1 = new ArrayList<>();
        cosmeticServices1.add(cosmeticService1);

        List<CosmeticService> cosmeticServices2 = new ArrayList<>();
        cosmeticServices2.add(cosmeticService1);
        cosmeticServices2.add(cosmeticService2);

        CosmeticServiceCategory cosmeticServiceCategory1 = new CosmeticServiceCategory(1L, "AA", cosmeticServices1);
        CosmeticServiceCategory cosmeticServiceCategory2 = new CosmeticServiceCategory(4L, "BB", cosmeticServices2);

        List<CosmeticServiceCategory> cosmeticServiceCategories = new ArrayList<>();
        cosmeticServiceCategories.add(cosmeticServiceCategory1);
        cosmeticServiceCategories.add(cosmeticServiceCategory2);

        when(cosmeticServiceCategoryService.categoryWithTheLeastNumberOfServices()).thenReturn(cosmeticServiceCategory1);

        mockMvc.perform(MockMvcRequestBuilders.get("/cosmeticServiceCategory/categoryWithTheLeastNumberOfServices"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath(".name").value("AA"));

        assertEquals(cosmeticServiceCategory1, cosmeticServiceCategoryController.categoryWithTheLeastNumberOfServices());
    }
}