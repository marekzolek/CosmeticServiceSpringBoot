package com.marekzolek.service;

import com.marekzolek.exception.CategoryNotFoundException;
import com.marekzolek.exception.CustomerNotFoundException;
import com.marekzolek.model.CosmeticService;
import com.marekzolek.model.CosmeticServiceCategory;
import com.marekzolek.model.Customer;
import com.marekzolek.repository.CosmeticServiceCategoryRepository;
import com.marekzolek.repository.CustomerRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;


@RunWith(MockitoJUnitRunner.class)
public class CosmeticServiceCategoryServiceImplTest {

    @InjectMocks
    private CosmeticServiceCategoryServiceImpl cosmeticServiceCategoryService;

    @Mock
    private CosmeticServiceCategoryRepository cosmeticServiceCategoryRepository;

    @Mock
    private CustomerRepository customerRepository;

    @Test
    public void add() {

        CosmeticServiceCategory cosmeticServiceCategory = new CosmeticServiceCategory();

        when(cosmeticServiceCategoryRepository.save(cosmeticServiceCategory)).thenReturn(cosmeticServiceCategory);

        assertEquals(cosmeticServiceCategory, cosmeticServiceCategoryService.add(cosmeticServiceCategory));
    }

    @Test
    public void delete() {

        cosmeticServiceCategoryRepository.deleteById(1L);
        verify(cosmeticServiceCategoryRepository, times(1)).deleteById(1L);
    }

    @Test
    public void findOne() throws CategoryNotFoundException {

        CosmeticServiceCategory cosmeticServiceCategory = new CosmeticServiceCategory();

        when(cosmeticServiceCategoryRepository.findById(1L)).thenReturn(Optional.of(cosmeticServiceCategory));

        assertEquals(cosmeticServiceCategory, cosmeticServiceCategoryService.findOne(1L));
    }

    @Test
    public void findAll() {

        CosmeticServiceCategory cosmeticServiceCategory1 = new CosmeticServiceCategory();
        CosmeticServiceCategory cosmeticServiceCategory2 = new CosmeticServiceCategory();

        List<CosmeticServiceCategory> cosmeticServiceCategories = new ArrayList<>();
        cosmeticServiceCategories.add(cosmeticServiceCategory1);
        cosmeticServiceCategories.add(cosmeticServiceCategory2);

        when(cosmeticServiceCategoryRepository.findAll()).thenReturn(cosmeticServiceCategories);

        assertEquals(cosmeticServiceCategories, cosmeticServiceCategoryService.findAll());
    }

    @Test
    public void namesOfCategoryServiceOfGivenCustomer() throws CustomerNotFoundException {

        Customer customer = new Customer();

        CosmeticServiceCategory cosmeticServiceCategory1 = new CosmeticServiceCategory();
        CosmeticServiceCategory cosmeticServiceCategory2 = new CosmeticServiceCategory();

        List<CosmeticServiceCategory> cosmeticServiceCategories = new ArrayList<>();
        cosmeticServiceCategories.add(cosmeticServiceCategory1);
        cosmeticServiceCategories.add(cosmeticServiceCategory2);

        when(customerRepository.findById(1L)).thenReturn(Optional.of(customer));
        when(cosmeticServiceCategoryRepository.namesOfCategoryServiceOfGivenCustomer(1L)).thenReturn(cosmeticServiceCategories);

        assertEquals(cosmeticServiceCategories, cosmeticServiceCategoryService.namesOfCategoryServiceOfGivenCustomer(1L));
    }

    @Test
    public void findAllByServiceType() {

        CosmeticService cosmeticService1 = new CosmeticService(null, null, "M", null);
        CosmeticService cosmeticService2 = new CosmeticService(null, null, "F", null);

        List<CosmeticService> cosmeticServices1 = new ArrayList<>();
        cosmeticServices1.add(cosmeticService1);
        List<CosmeticService> cosmeticServices2 = new ArrayList<>();
        cosmeticServices2.add(cosmeticService2);

        CosmeticServiceCategory cosmeticServiceCategory1 = new CosmeticServiceCategory(1L, null, cosmeticServices1);
        CosmeticServiceCategory cosmeticServiceCategory2 = new CosmeticServiceCategory(2L, null, cosmeticServices2);

        List<CosmeticServiceCategory> cosmeticServiceCategories = new ArrayList<>();
        cosmeticServiceCategories.add(cosmeticServiceCategory1);
        when(cosmeticServiceCategoryRepository.findAll()).thenReturn(cosmeticServiceCategories);

        assertEquals(cosmeticServiceCategories, cosmeticServiceCategoryService.findAllByServiceType("M"));
    }

    @Test
    public void categoryWithTheLargestNumberOfServices() throws CategoryNotFoundException {

        CosmeticService cosmeticService1 = new CosmeticService();
        CosmeticService cosmeticService2 = new CosmeticService();

        List<CosmeticService> cosmeticServices1 = new ArrayList<>();
        cosmeticServices1.add(cosmeticService1);

        List<CosmeticService> cosmeticServices2 = new ArrayList<>();
        cosmeticServices2.add(cosmeticService1);
        cosmeticServices2.add(cosmeticService2);

        CosmeticServiceCategory cosmeticServiceCategory1 = new CosmeticServiceCategory(1L, null, cosmeticServices1);
        CosmeticServiceCategory cosmeticServiceCategory2 = new CosmeticServiceCategory(2L, null, cosmeticServices2);

        List<CosmeticServiceCategory> cosmeticServiceCategories = new ArrayList<>();
        cosmeticServiceCategories.add(cosmeticServiceCategory1);
        cosmeticServiceCategories.add(cosmeticServiceCategory2);

        when(cosmeticServiceCategoryRepository.findAll()).thenReturn(cosmeticServiceCategories);

        assertEquals(cosmeticServiceCategory2, cosmeticServiceCategoryService.categoryWithTheLargestNumberOfServices());
    }

    @Test
    public void categoryWithTheLeastNumberOfServices() throws CategoryNotFoundException {

        CosmeticService cosmeticService1 = new CosmeticService();
        CosmeticService cosmeticService2 = new CosmeticService();

        List<CosmeticService> cosmeticServices1 = new ArrayList<>();
        cosmeticServices1.add(cosmeticService1);

        List<CosmeticService> cosmeticServices2 = new ArrayList<>();
        cosmeticServices2.add(cosmeticService1);
        cosmeticServices2.add(cosmeticService2);

        CosmeticServiceCategory cosmeticServiceCategory1 = new CosmeticServiceCategory(1L, null, cosmeticServices1);
        CosmeticServiceCategory cosmeticServiceCategory2 = new CosmeticServiceCategory(2L, null, cosmeticServices2);

        List<CosmeticServiceCategory> cosmeticServiceCategories = new ArrayList<>();
        cosmeticServiceCategories.add(cosmeticServiceCategory1);
        cosmeticServiceCategories.add(cosmeticServiceCategory2);

        when(cosmeticServiceCategoryRepository.findAll()).thenReturn(cosmeticServiceCategories);

        assertEquals(cosmeticServiceCategory1, cosmeticServiceCategoryService.categoryWithTheLeastNumberOfServices());
    }
}