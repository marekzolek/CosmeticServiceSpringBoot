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
    }

    @Test
    public void add() {

        Customer customer = new Customer();

        when(customerService.add(customer)).thenReturn(customer);

        assertEquals(customer, customerController.add(customer));
    }

    @Test
    public void delete() {

        customerService.delete(1L);
        verify(customerService, times(1)).delete(1L);
    }

    @Test
    public void findOne() throws Exception {

        Customer customer = new Customer(9354648266L, "ANIA", "KOWALSKA");

        when(customerService.findOne(1L)).thenReturn(customer);

        mockMvc.perform(MockMvcRequestBuilders.get("/customers/1")).
                andExpect(MockMvcResultMatchers.status().isOk()).
                andExpect(MockMvcResultMatchers.jsonPath(".pesel").value(9354648266L));

        assertEquals(customer, customerController.findOne(1L));
    }

    @Test
    public void findAll() throws Exception {

        Customer customer1 = new Customer(9354648266L, "ANIA", "KOWALSKA");
        Customer customer2 = new Customer(9354648263L, "Tomek", "KOWALSKi");

        List<Customer> customers = new ArrayList<>();
        customers.add(customer1);
        customers.add(customer2);

        when(customerService.findAll()).thenReturn(customers);

        mockMvc.perform(MockMvcRequestBuilders.get("/customers/all")).
                andExpect(MockMvcResultMatchers.status().isOk()).
                andExpect(MockMvcResultMatchers.jsonPath("$[0].pesel").value(9354648266L));

        assertEquals(customers, customerController.findAll());
    }

    @Test
    public void customersBornBeforeYear() throws Exception {

        Customer customer1 = new Customer(93546448266L, "ANIA", "KOWALSKA");
        Customer customer2 = new Customer(93546458263L, "Tomek", "KOWALSKi");

        List<Customer> customers = new ArrayList<>();
        customers.add(customer1);
        customers.add(customer2);

        when(customerService.customersBornBeforeYear(94)).thenReturn(customers);

        mockMvc.perform(MockMvcRequestBuilders.get("/customers/bornBefore?year=94").accept(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$[0].pesel").value(93546448266L));

        assertEquals(customers, customerController.customersBornBeforeYear(94));
    }

    @Test
    public void customersWhichHadServicesInYear() throws Exception {

        Customer customer1 = new Customer(9354648266L, "ANIA", "KOWALSKA");
        Customer customer2 = new Customer(9354648263L, "Tomek", "KOWALSKi");

        CosmeticServicesHistory cosmeticServicesHistory = new CosmeticServicesHistory(new CosmeticService(), customer1, "2017-03-04");
        CosmeticServicesHistory cosmeticServicesHistory1 = new CosmeticServicesHistory(new CosmeticService(), customer2, "2017-03-04");

        List<CosmeticServicesHistory> cosmeticServicesHistories = new ArrayList<>();
        cosmeticServicesHistories.add(cosmeticServicesHistory);
        cosmeticServicesHistories.add(cosmeticServicesHistory1);

        List<Customer> customers = new ArrayList<>();
        customers.add(customer1);
        customers.add(customer2);

        when(customerService.customersWhichHadServicesInYear(2017)).thenReturn(customers);

        mockMvc.perform(MockMvcRequestBuilders.get("/customers/withServicesInYear?year=2017"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].pesel").value(9354648263L));

        assertEquals(customers, customerController.customersWhichHadServicesInYear(2017));
    }

    @Test
    public void womanBornBeforeYear() throws Exception {
        Customer customer1 = new Customer(93546482664L, "ANIA", "KOWALSKA");
        Customer customer2 = new Customer(93546482631L, "TOMEK", "KOWALSKI");

        List<Customer> customers = new ArrayList<>();
        customers.add(customer1);

        when(customerService.womanBornBeforeYear(94)).thenReturn(customers);

        mockMvc.perform(MockMvcRequestBuilders.get("/customers/womanBornBefore?year=94"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].name").value("ANIA"));

        assertEquals(customers, customerController.womanBornBeforeYear(94));
    }

    @Test
    public void mansBornBeforeYear() throws Exception {

        Customer customer1 = new Customer(93546482664L, "ANIA", "KOWALSKA");
        Customer customer2 = new Customer(93546482631L, "TOMEK", "KOWALSKI");

        List<Customer> customers = new ArrayList<>();
        customers.add(customer2);

        when(customerService.mansBornBeforeYear(94)).thenReturn(customers);

        mockMvc.perform(MockMvcRequestBuilders.get("/customers/mansBornBefore?year=94"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].name").value("TOMEK"));

        assertEquals(customers, customerController.mansBornBeforeYear(94));
    }

    @Test
    public void customersWithSumOfServicesInGivenYear() throws Exception {

        Customer customer1 = new Customer(9354648266L, "ANIA", "KOWALSKA");
        Customer customer2 = new Customer(9354648263L, "TOMEK", "KOWALSKI");

        CustomerDto customerDto1 = new CustomerDto();
        customerDto1.setPesel(9354648266L);
        customerDto1.setName("ANIA");
        customerDto1.setSurname("KOWALSKA");
        CustomerDto customerDto2 = new CustomerDto();
        customerDto2.setPesel(9354648263L);
        customerDto2.setName("TOMEK");
        customerDto2.setSurname("KOWALSKI");

        CosmeticServicesHistory cosmeticServicesHistory = new CosmeticServicesHistory(new CosmeticService(), customer1, "2017-03-04");
        CosmeticServicesHistory cosmeticServicesHistory1 = new CosmeticServicesHistory(new CosmeticService(), customer2, "2017-03-04");

        List<CosmeticServicesHistory> cosmeticServicesHistories = new ArrayList<>();
        cosmeticServicesHistories.add(cosmeticServicesHistory);
        cosmeticServicesHistories.add(cosmeticServicesHistory1);

        List<Customer> customers = new ArrayList<>();
        customers.add(customer1);
        customers.add(customer2);

        when(customerDtoMapper.customerToDto(customer1)).thenReturn(customerDto1);
        when(customerDtoMapper.customerToDto(customer2)).thenReturn(customerDto2);

        List<CustomerDto> dtoCustomers = new ArrayList<>();
        dtoCustomers.add(customerDto1);
        dtoCustomers.add(customerDto2);

        when(customerService.customersWithSumOfServicesInGivenYear(2017)).thenReturn(dtoCustomers);
        mockMvc.perform(MockMvcRequestBuilders.get("/customers/servicesInYear?year=2017"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].pesel").value(9354648266L));

        assertEquals(dtoCustomers, customerController.customersWithSumOfServicesInGivenYear(2017));
    }

    @Test
    public void customersWhichHadGivenService() throws Exception {

        Customer customer1 = new Customer(93546482664L, "ANIA", "KOWALSKA");
        Customer customer2 = new Customer(93546482631L, "Tomek", "KOWALSKi");

        List<Customer> customers = new ArrayList<>();
        customers.add(customer1);
        customers.add(customer2);

        when(customerService.customersWhichHadGivenService(1L)).thenReturn(customers);
        mockMvc.perform(MockMvcRequestBuilders.get("/customers/hadService?serviceId=1"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].pesel").value(93546482664L));

        assertEquals(customers, customerController.customersWhichHadGivenService(1L));
    }

    @Test
    public void customersBornInGivenYearAndWhichHadServiceFromGivenCategoryInGivenYear() throws Exception {

        Customer customer1 = new Customer(93546448266L, "ANIA", "KOWALSKA");
        Customer customer2 = new Customer(93546458263L, "Tomek", "KOWALSKi");

        CosmeticServiceCategory cosmeticServiceCategory = new CosmeticServiceCategory(2L, "Depilacja Damska", null);

        CosmeticService cosmeticService = new CosmeticService("Depilacja damska", 250, "F", cosmeticServiceCategory);

        CosmeticServicesHistory cosmeticServicesHistory = new CosmeticServicesHistory(cosmeticService, customer1, "2017-03-04");
        CosmeticServicesHistory cosmeticServicesHistory1 = new CosmeticServicesHistory(cosmeticService, customer2, "2017-03-04");

        List<CosmeticServicesHistory> cosmeticServicesHistories = new ArrayList<>();
        cosmeticServicesHistories.add(cosmeticServicesHistory);
        cosmeticServicesHistories.add(cosmeticServicesHistory1);

        List<Customer> customers = new ArrayList<>();
        customers.add(customer1);
        customers.add(customer2);


        when(customerService.customersBornInGivenYearAndWhichHadServiceFromGivenCategoryInGivenYear(93, 2L, 2017)).thenReturn(customers);

        mockMvc.perform(MockMvcRequestBuilders.get("/customers/bornInHadServiceFromCategoryInYear?yearOfBirth=93&categoryId=2&yearOfService=2017"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].pesel").value(93546448266L));

        assertEquals(customers, customerController.customersBornInGivenYearAndWhichHadServiceFromGivenCategoryInGivenYear(93, 2L, 2017));
    }
}