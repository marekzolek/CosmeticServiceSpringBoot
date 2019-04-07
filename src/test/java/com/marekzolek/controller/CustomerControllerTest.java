package com.marekzolek.controller;

import com.marekzolek.dto.CustomerDto;
import com.marekzolek.dto.mapperDto.CustomerDtoMapper;
import com.marekzolek.model.CosmeticService;
import com.marekzolek.model.CosmeticServiceCategory;
import com.marekzolek.model.CosmeticServicesHistory;
import com.marekzolek.model.Customer;
import com.marekzolek.repository.CosmeticServicesHistoryRepository;
import com.marekzolek.repository.CustomerRepository;
import com.marekzolek.service.CustomerService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class CustomerControllerTest {

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
    private CustomerController customerController;

    @Mock
    private CustomerService customerService;

    private MockMvc mockMvc;

    @Mock
    private CustomerDtoMapper customerDtoMapper;

    @Autowired
    private CosmeticServicesHistoryRepository cosmeticServicesHistoryRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Before
    public void init() {

        mockMvc = MockMvcBuilders.standaloneSetup(customerController).build();

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


        when(customerService.add(customer1)).thenReturn(customer1);

        assertEquals(customer1, customerController.add(customer1));
    }

    @Test
    public void delete() {

        customerService.delete(1L);
        verify(customerService, times(1)).delete(1L);
    }

    @Test
    public void findOne() throws Exception {


        when(customerService.findOne(1L)).thenReturn(customer1);

        mockMvc.perform(MockMvcRequestBuilders.get("/customers/1")).
                andExpect(MockMvcResultMatchers.status().isOk()).
                andExpect(MockMvcResultMatchers.jsonPath(".pesel").value(93546448266L));

        assertEquals(customer1, customerController.findOne(1L));
    }

    @Test
    public void findAll() throws Exception {

        when(customerService.findAll()).thenReturn(customers);

        mockMvc.perform(MockMvcRequestBuilders.get("/customers/all")).
                andExpect(MockMvcResultMatchers.status().isOk()).
                andExpect(MockMvcResultMatchers.jsonPath("$[0].pesel").value(93546448266L));

        assertEquals(customers, customerController.findAll());
    }

    @Test
    public void customersBornBeforeYear() throws Exception {

        when(customerService.customersBornBeforeYear(94)).thenReturn(customers);

        mockMvc.perform(MockMvcRequestBuilders.get("/customers/bornBefore?year=94").accept(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$[0].pesel").value(93546448266L));

        assertEquals(customers, customerController.customersBornBeforeYear(94));
    }

    @Test
    public void customersWhichHadServicesInYear() throws Exception {

        when(customerService.customersWhichHadServicesInYear(2017)).thenReturn(customers);

        mockMvc.perform(MockMvcRequestBuilders.get("/customers/withServicesInYear?year=2017"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].pesel").value(93546458293L));

        assertEquals(customers, customerController.customersWhichHadServicesInYear(2017));
    }

    @Test
    public void womanBornBeforeYear() throws Exception {

        customers.clear();
        customers.add(customer1);

        when(customerService.womanBornBeforeYear(94)).thenReturn(customers);

        mockMvc.perform(MockMvcRequestBuilders.get("/customers/womanBornBefore?year=94"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].name").value("ANIA"));

        assertEquals(customers, customerController.womanBornBeforeYear(94));
    }

    @Test
    public void mansBornBeforeYear() throws Exception {

        customers.clear();
        customers.add(customer2);

        when(customerService.mansBornBeforeYear(94)).thenReturn(customers);

        mockMvc.perform(MockMvcRequestBuilders.get("/customers/mansBornBefore?year=94"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].name").value("TOMEK"));

        assertEquals(customers, customerController.mansBornBeforeYear(94));
    }

    @Test
    public void customersWithSumOfServicesInGivenYear() throws Exception {
        when(customerDtoMapper.customerToDto(customer1)).thenReturn(customerDto1);
        when(customerDtoMapper.customerToDto(customer2)).thenReturn(customerDto2);

        List<CustomerDto> dtoCustomers = new ArrayList<>();
        dtoCustomers.add(customerDto1);
        dtoCustomers.add(customerDto2);

        when(customerService.customersWithSumOfServicesInGivenYear(2017)).thenReturn(dtoCustomers);
        mockMvc.perform(MockMvcRequestBuilders.get("/customers/servicesInYear?year=2017"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].pesel").value(93546448266L));

        assertEquals(dtoCustomers, customerController.customersWithSumOfServicesInGivenYear(2017));
    }

    @Test
    public void customersWhichHadGivenService() throws Exception {

        when(customerService.customersWhichHadGivenService(1L)).thenReturn(customers);
        mockMvc.perform(MockMvcRequestBuilders.get("/customers/hadService?serviceId=1"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].pesel").value(93546448266L));

        assertEquals(customers, customerController.customersWhichHadGivenService(1L));
    }

    @Test
    public void customersBornInGivenYearAndWhichHadServiceFromGivenCategoryInGivenYear() throws Exception {

        when(customerService.customersBornInGivenYearAndWhichHadServiceFromGivenCategoryInGivenYear(93, 2L, 2017)).thenReturn(customers);

        mockMvc.perform(MockMvcRequestBuilders.get("/customers/bornInHadServiceFromCategoryInYear?yearOfBirth=93&categoryId=2&yearOfService=2017"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].pesel").value(93546448266L));

        assertEquals(customers, customerController.customersBornInGivenYearAndWhichHadServiceFromGivenCategoryInGivenYear(93, 2L, 2017));
    }
}