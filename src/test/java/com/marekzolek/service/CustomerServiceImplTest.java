package com.marekzolek.service;

import com.marekzolek.dto.CustomerDto;
import com.marekzolek.dto.mapperDto.CustomerDtoMapper;
import com.marekzolek.exception.CustomerNotFoundException;
import com.marekzolek.model.CosmeticService;
import com.marekzolek.model.CosmeticServiceCategory;
import com.marekzolek.model.CosmeticServicesHistory;
import com.marekzolek.model.Customer;
import com.marekzolek.repository.CosmeticServicesHistoryRepository;
import com.marekzolek.repository.CustomerRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class CustomerServiceImplTest {

    private Customer customer1;
    private Customer customer2;
    private CosmeticService cosmeticService1;
    private CosmeticService cosmeticService2;
    private CosmeticServicesHistory cosmeticServicesHistory1;
    private CosmeticServicesHistory cosmeticServicesHistory2;
    private CosmeticServiceCategory cosmeticServiceCategory1;
    private CosmeticServiceCategory cosmeticServiceCategory2;
    private CustomerDto customerDto1 = new CustomerDto();
    private CustomerDto customerDto2 = new CustomerDto();

    private List<CosmeticServicesHistory> cosmeticServicesHistories = new ArrayList<>();
    private List<Customer> customers = new ArrayList<>();
    private List<CustomerDto> dtoCustomers = new ArrayList<>();

    @InjectMocks
    private CustomerServiceImpl customerService;

    @Mock
    private CustomerRepository customerRepository;

    @Mock
    private CosmeticServicesHistoryRepository cosmeticServicesHistoryRepository;

    @Mock
    private CustomerDtoMapper customerDtoMapper;

    @Before
    public void init() {

        customer1 = new Customer.CustomerBuilder()
                .pesel(93546448266L)
                .name("ANIA")
                .surname("KOWALSKA")
                .build();

        customer2 = new Customer.CustomerBuilder()
                .pesel(93546458293L)
                .name("TOMEK")
                .surname("KOWALSKI")
                .build();

        customers.add(customer1);
        customers.add(customer2);

        customerDto1.setPesel(93546448266L);
        customerDto1.setName("ANIA");
        customerDto1.setSurname("KOWALSKA");

        customerDto2.setPesel(93546458293L);
        customerDto2.setName("TOMEK");
        customerDto2.setSurname("KOWALSKI");

        dtoCustomers.add(customerDto1);
        dtoCustomers.add(customerDto2);

        cosmeticServiceCategory1 = new CosmeticServiceCategory.CosmeticServiceCategoryBuilder()
                .id(1L)
                .build();
        cosmeticServiceCategory2 = new CosmeticServiceCategory.CosmeticServiceCategoryBuilder()
                .id(2L)
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
                .type("M")
                .category(cosmeticServiceCategory2)
                .build();

        cosmeticServicesHistory1 = new CosmeticServicesHistory.CosmeticServicesHistoryBuilder()
                .cosmeticService(cosmeticService1)
                .customer(customer1)
                .date("2017-03-04")
                .build();

        cosmeticServicesHistory2 = new CosmeticServicesHistory.CosmeticServicesHistoryBuilder()
                .cosmeticService(cosmeticService2)
                .customer(customer2)
                .date("2017-02-04")
                .build();

        cosmeticServicesHistories.add(cosmeticServicesHistory1);
        cosmeticServicesHistories.add(cosmeticServicesHistory2);
    }

    @Test
    public void add() {

        when(customerRepository.save(customer1)).thenReturn(customer1);

        assertEquals(customer1, customerService.add(customer1));
    }

    @Test
    public void delete() {

        customerRepository.deleteById(11L);
        verify(customerRepository, times(1)).deleteById(11L);

    }

    @Test
    public void findOne() throws CustomerNotFoundException {

        when(customerRepository.findById(101L)).thenReturn(Optional.of(customer1));

        assertEquals(customer1, customerService.findOne(101L));
    }

    @Test
    public void findAll() {

        when(customerRepository.findAll()).thenReturn(customers);

        assertEquals(customers, customerService.findAll());
    }

    @Test
    public void customersBornBeforeYear() {

        when(customerRepository.findAll()).thenReturn(customers);

        assertEquals(customers, customerService.customersBornBeforeYear(94));
    }

    @Test
    public void customersWhichHadServicesInYear() throws ParseException {

        when(cosmeticServicesHistoryRepository.findAll()).thenReturn(cosmeticServicesHistories);

        assertEquals(customers, customerService.customersWhichHadServicesInYear(2017));
    }

    @Test
    public void womanBornBeforeYear() {

        when(customerRepository.findAll()).thenReturn(customers);

        assertEquals(customer1, customerService.womanBornBeforeYear(94).get(0));
    }

    @Test
    public void mansBornBeforeYear() {

        when(customerRepository.findAll()).thenReturn(customers);

        assertEquals(customer2, customerService.mansBornBeforeYear(94).get(0));
    }

    @Test
    public void customersWithSumOfServicesInGivenYear() throws ParseException {

        when(customerDtoMapper.customerToDto(customer1)).thenReturn(customerDto1);
        when(customerDtoMapper.customerToDto(customer2)).thenReturn(customerDto2);

        when(cosmeticServicesHistoryRepository.findAll()).thenReturn(cosmeticServicesHistories);
        assertTrue(customerService.customersWithSumOfServicesInGivenYear(2017).contains(customerDto1));
        assertEquals(2, customerService.customersWithSumOfServicesInGivenYear(2017).size());

    }

    @Test
    public void customersWhichHadGivenService() {

        when(customerRepository.customersWhichHadGivenService(94L)).thenReturn(customers);

        assertEquals(customers, customerService.customersWhichHadGivenService(94L));

    }

    @Test
    public void customersBornInGivenYearAndWhichHadServiceFromGivenCategoryInGivenYear() throws ParseException {

        customers.clear();
        customers.add(customer1);

        when(cosmeticServicesHistoryRepository.findAll()).thenReturn(cosmeticServicesHistories);

        assertEquals(customers, customerService.customersBornInGivenYearAndWhichHadServiceFromGivenCategoryInGivenYear(93, 1L, 2017));
    }
}