package com.marekzolek.service;

import com.marekzolek.exception.CategoryNotFoundException;
import com.marekzolek.exception.CustomerNotFoundException;
import com.marekzolek.model.CosmeticService;
import com.marekzolek.model.CosmeticServiceCategory;
import com.marekzolek.model.Customer;
import com.marekzolek.repository.CosmeticServiceCategoryRepository;
import com.marekzolek.repository.CustomerRepository;
import org.junit.Before;
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

    private CosmeticService cosmeticService1;
    private CosmeticService cosmeticService2;
    private CosmeticServiceCategory cosmeticServiceCategory1;
    private CosmeticServiceCategory cosmeticServiceCategory2;


    private List<CosmeticService> cosmeticServices1 = new ArrayList<>();
    private List<CosmeticService> cosmeticServices2 = new ArrayList<>();
    private List<CosmeticServiceCategory> cosmeticServiceCategories = new ArrayList<>();

    @InjectMocks
    private CosmeticServiceCategoryServiceImpl cosmeticServiceCategoryService;

    @Mock
    private CosmeticServiceCategoryRepository cosmeticServiceCategoryRepository;

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

        when(cosmeticServiceCategoryRepository.save(cosmeticServiceCategory1)).thenReturn(cosmeticServiceCategory1);

        assertEquals(cosmeticServiceCategory1, cosmeticServiceCategoryService.add(cosmeticServiceCategory1));
    }

    @Test
    public void delete() {

        cosmeticServiceCategoryRepository.deleteById(1L);
        verify(cosmeticServiceCategoryRepository, times(1)).deleteById(1L);
    }

    @Test
    public void findOne() throws CategoryNotFoundException {

        when(cosmeticServiceCategoryRepository.findById(1L)).thenReturn(Optional.of(cosmeticServiceCategory1));

        assertEquals(cosmeticServiceCategory1, cosmeticServiceCategoryService.findOne(1L));
    }

    @Test
    public void findAll() {

        when(cosmeticServiceCategoryRepository.findAll()).thenReturn(cosmeticServiceCategories);

        assertEquals(cosmeticServiceCategories, cosmeticServiceCategoryService.findAll());
    }

    @Test
    public void namesOfCategoryServiceOfGivenCustomer() throws CustomerNotFoundException {

        Customer customer = new Customer();

        when(customerRepository.findById(1L)).thenReturn(Optional.of(customer));
        when(cosmeticServiceCategoryRepository.namesOfCategoryServiceOfGivenCustomer(1L)).thenReturn(cosmeticServiceCategories);

        assertEquals(cosmeticServiceCategories, cosmeticServiceCategoryService.namesOfCategoryServiceOfGivenCustomer(1L));
    }

    @Test
    public void findAllByServiceType() {

        cosmeticServiceCategories.clear();
        cosmeticServiceCategories.add(cosmeticServiceCategory1);

        when(cosmeticServiceCategoryRepository.findAll()).thenReturn(cosmeticServiceCategories);

        assertEquals(1, cosmeticServiceCategoryService.findAllByServiceType("M").size());
    }

    @Test
    public void categoryWithTheLargestNumberOfServices() throws CategoryNotFoundException {

        when(cosmeticServiceCategoryRepository.findAll()).thenReturn(cosmeticServiceCategories);

        assertEquals(cosmeticServiceCategory2, cosmeticServiceCategoryService.categoryWithTheLargestNumberOfServices());
    }

    @Test
    public void categoryWithTheLeastNumberOfServices() throws CategoryNotFoundException {

        when(cosmeticServiceCategoryRepository.findAll()).thenReturn(cosmeticServiceCategories);

        assertEquals(cosmeticServiceCategory1, cosmeticServiceCategoryService.categoryWithTheLeastNumberOfServices());
    }
}