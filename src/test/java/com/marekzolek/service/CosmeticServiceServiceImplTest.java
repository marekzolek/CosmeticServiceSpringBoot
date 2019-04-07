package com.marekzolek.service;

import com.marekzolek.exception.CategoryNotFoundException;
import com.marekzolek.exception.CosmeticServiceNotFoundException;
import com.marekzolek.model.CosmeticService;
import com.marekzolek.model.CosmeticServiceCategory;
import com.marekzolek.repository.CosmeticServiceCategoryRepository;
import com.marekzolek.repository.CosmeticServiceRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class CosmeticServiceServiceImplTest {

    CosmeticService cosmeticService1;
    CosmeticService cosmeticService2;
    CosmeticServiceCategory cosmeticServiceCategory1;
    List<CosmeticService> cosmeticServices = new ArrayList<>();

    @InjectMocks
    private CosmeticServiceServiceImpl cosmeticServiceService;

    @Mock
    private CosmeticServiceRepository cosmeticServiceRepository;

    @Mock
    private CosmeticServiceCategoryRepository cosmeticServiceCategoryRepository;

    @Before
    public void init() {

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

        when(cosmeticServiceRepository.save(cosmeticService1)).thenReturn(cosmeticService1);

        assertEquals(cosmeticService1, cosmeticServiceService.add(cosmeticService1));
    }

    @Test
    public void delete() {

        cosmeticServiceRepository.deleteById(1L);
        verify(cosmeticServiceRepository, times(1)).deleteById(1L);
    }

    @Test
    public void findOne() throws CosmeticServiceNotFoundException {

        when(cosmeticServiceRepository.findById(1L)).thenReturn(Optional.of(cosmeticService1));

        assertEquals(cosmeticService1, cosmeticServiceService.findOne(1L));
    }

    @Test
    public void findAll() {

        when(cosmeticServiceRepository.findAll()).thenReturn(cosmeticServices);

        assertEquals(cosmeticServices, cosmeticServiceService.findAll());
    }

    @Test
    public void findCosmeticServicesWithCategoryId() throws CategoryNotFoundException {

        CosmeticServiceCategory cosmeticServiceCategory = new CosmeticServiceCategory(1L, null, null);

        CosmeticService cosmeticService1 = new CosmeticService(null, null, null, cosmeticServiceCategory);
        CosmeticService cosmeticService2 = new CosmeticService(null, null, null, cosmeticServiceCategory);

        List<CosmeticService> cosmeticServices = new ArrayList<>();
        cosmeticServices.add(cosmeticService1);
        cosmeticServices.add(cosmeticService2);

        when(cosmeticServiceCategoryRepository.findById(1L)).thenReturn(Optional.of(cosmeticServiceCategory));

        when(cosmeticServiceRepository.findAll()).thenReturn(cosmeticServices);

        assertEquals(cosmeticServices, cosmeticServiceService.findCosmeticServicesWithCategoryId(1L));
    }

    @Test
    public void findAllByType() {

        cosmeticServices.clear();
        cosmeticServices.add(cosmeticService1);

        when(cosmeticServiceRepository.findAllByType("M")).thenReturn(cosmeticServices);

        assertEquals(cosmeticServices, cosmeticServiceService.findAllByType("M"));
    }

    @Test
    public void findTheMostExpensiveService() {

        cosmeticServices.clear();
        cosmeticServices.add(cosmeticService1);

        when(cosmeticServiceRepository.findTheMostExpensiveService()).thenReturn(cosmeticServices);

        assertEquals(cosmeticService1, cosmeticServiceService.findTheMostExpensiveService());
    }

    @Test
    public void findTheCheapestService() {

        cosmeticServices.clear();
        cosmeticServices.add(cosmeticService2);

        when(cosmeticServiceRepository.findTheCheapestService()).thenReturn(cosmeticServices);

        assertEquals(cosmeticService2, cosmeticServiceService.findCheapestService());
    }

    @Test
    public void countServicesByType() {

        when(cosmeticServiceRepository.countServicesByType("M")).thenReturn(1);

        assertEquals(Optional.of(1).get(), cosmeticServiceService.countServicesByType("M"));
    }
}