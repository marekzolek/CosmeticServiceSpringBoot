package com.marekzolek.service;

import com.marekzolek.exception.CategoryNotFoundException;
import com.marekzolek.exception.CosmeticServiceNotFoundException;
import com.marekzolek.model.CosmeticService;
import com.marekzolek.model.CosmeticServiceCategory;
import com.marekzolek.repository.CosmeticServiceCategoryRepository;
import com.marekzolek.repository.CosmeticServiceRepository;
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

    @InjectMocks
    private CosmeticServiceServiceImpl cosmeticServiceService;

    @Mock
    private CosmeticServiceRepository cosmeticServiceRepository;

    @Mock
    private CosmeticServiceCategoryRepository cosmeticServiceCategoryRepository;

    @Test
    public void add() {

        CosmeticService cosmeticService = new CosmeticService();

        when(cosmeticServiceRepository.save(cosmeticService)).thenReturn(cosmeticService);

        assertEquals(cosmeticService, cosmeticServiceService.add(cosmeticService));
    }

    @Test
    public void delete() {

        cosmeticServiceRepository.deleteById(1L);
        verify(cosmeticServiceRepository, times(1)).deleteById(1L);
    }

    @Test
    public void findOne() throws CosmeticServiceNotFoundException {

        CosmeticService cosmeticService = new CosmeticService();

        when(cosmeticServiceRepository.findById(1L)).thenReturn(Optional.of(cosmeticService));

        assertEquals(cosmeticService, cosmeticServiceService.findOne(1L));
    }

    @Test
    public void findAll() {

        CosmeticService cosmeticService1 = new CosmeticService();
        CosmeticService cosmeticService2 = new CosmeticService();

        List<CosmeticService> cosmeticServices = new ArrayList<>();
        cosmeticServices.add(cosmeticService1);
        cosmeticServices.add(cosmeticService2);

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

        CosmeticService cosmeticService1 = new CosmeticService(null, null, "M", null);
        CosmeticService cosmeticService2 = new CosmeticService(null, null, "F", null);

        List<CosmeticService> cosmeticServices = new ArrayList<>();
        cosmeticServices.add(cosmeticService1);

        when(cosmeticServiceRepository.findAllByType("M")).thenReturn(cosmeticServices);

        assertEquals(cosmeticServices, cosmeticServiceService.findAllByType("M"));
    }

    @Test
    public void findTheMostExpensiveService() {

        CosmeticService cosmeticService = new CosmeticService();

        List<CosmeticService> cosmeticServices = new ArrayList<>();
        cosmeticServices.add(cosmeticService);

        when(cosmeticServiceRepository.findTheMostExpensiveService()).thenReturn(cosmeticServices);

        assertEquals(cosmeticService, cosmeticServiceService.findTheMostExpensiveService());
    }

    @Test
    public void findTheCheapestService() {

        CosmeticService cosmeticService = new CosmeticService();

        List<CosmeticService> cosmeticServices = new ArrayList<>();
        cosmeticServices.add(cosmeticService);

        when(cosmeticServiceRepository.findTheCheapestService()).thenReturn(cosmeticServices);

        assertEquals(cosmeticService, cosmeticServiceService.findCheapestService());
    }

    @Test
    public void countServicesByType() {

        CosmeticService cosmeticService1 = new CosmeticService(null, null, "M", null);
        CosmeticService cosmeticService2 = new CosmeticService(null, null, "F", null);

        List<CosmeticService> cosmeticServices = new ArrayList<>();
        cosmeticServices.add(cosmeticService1);
        cosmeticServices.add(cosmeticService2);

        when(cosmeticServiceRepository.countServicesByType("M")).thenReturn(1);

        assertEquals(Optional.of(1).get(), cosmeticServiceService.countServicesByType("M"));
    }
}