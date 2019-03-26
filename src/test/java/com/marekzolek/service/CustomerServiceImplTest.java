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
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class CustomerServiceImplTest {

    @InjectMocks
    private CustomerServiceImpl customerService;

    @Mock
    private CustomerRepository customerRepository;

    @Mock
    private CosmeticServicesHistoryRepository cosmeticServicesHistoryRepository;

    @Mock
    private CustomerDtoMapper customerDtoMapper;

    @Test
    public void add() {

        Customer customer = new Customer(9354648266L, "ANIA", "KOWALSKA");

        when(customerRepository.save(customer)).thenReturn(customer);

        assertEquals(customer, customerService.add(customer));
    }

    @Test
    public void delete() {

        customerRepository.deleteById(11L);
        verify(customerRepository, times(1)).deleteById(11L);

    }

    @Test
    public void findOne() throws CustomerNotFoundException {

        Customer customer = new Customer(9354648266L, "ANIA", "KOWALSKA");

        when(customerRepository.findById(101L)).thenReturn(Optional.of(customer));

        assertEquals(customer, customerService.findOne(101L));
    }

    @Test
    public void findAll() {

        Customer customer1 = new Customer(9354648266L, "ANIA", "KOWALSKA");
        Customer customer2 = new Customer(9354648263L, "Tomek", "KOWALSKi");

        List<Customer> customers = new ArrayList<>();
        customers.add(customer1);
        customers.add(customer2);

        when(customerRepository.findAll()).thenReturn(customers);

        assertEquals(customers, customerService.findAll());
    }

    @Test
    public void customersBornBeforeYear() {

        Customer customer1 = new Customer(9354648266L, "ANIA", "KOWALSKA");
        Customer customer2 = new Customer(9354648263L, "Tomek", "KOWALSKi");

        List<Customer> customers = new ArrayList<>();
        customers.add(customer1);
        customers.add(customer2);

        when(customerRepository.findAll()).thenReturn(customers);

        assertEquals(customers, customerService.customersBornBeforeYear(94));
    }

    @Test
    public void customersWhichHadServicesInYear() throws ParseException {

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

        when(cosmeticServicesHistoryRepository.findAll()).thenReturn(cosmeticServicesHistories);

        assertEquals(customers, customerService.customersWhichHadServicesInYear(2017));
    }

    @Test
    public void womanBornBeforeYear() {

        Customer customer1 = new Customer(93546482664L, "ANIA", "KOWALSKA");
        Customer customer2 = new Customer(93546482631L, "Tomek", "KOWALSKi");

        List<Customer> customers = new ArrayList<>();
        customers.add(customer1);

        when(customerRepository.findAll()).thenReturn(customers);

        assertEquals(customers, customerService.womanBornBeforeYear(94));
    }

    @Test
    public void mansBornBeforeYear() {

        Customer customer1 = new Customer(93546482664L, "ANIA", "KOWALSKA");
        Customer customer2 = new Customer(93546482631L, "Tomek", "KOWALSKi");

        List<Customer> customers = new ArrayList<>();
        customers.add(customer2);

        when(customerRepository.findAll()).thenReturn(customers);

        assertEquals(customers, customerService.mansBornBeforeYear(94));
    }

    @Test
    public void customersWithSumOfServicesInGivenYear() throws ParseException {

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

        when(cosmeticServicesHistoryRepository.findAll()).thenReturn(cosmeticServicesHistories);
        assertEquals(dtoCustomers, customerService.customersWithSumOfServicesInGivenYear(2017));

    }

    @Test
    public void customersWhichHadGivenService() {

        Customer customer1 = new Customer(93546482664L, "ANIA", "KOWALSKA");
        Customer customer2 = new Customer(93546482631L, "Tomek", "KOWALSKi");

        List<Customer> customers = new ArrayList<>();
        customers.add(customer1);
        customers.add(customer2);

        when(customerRepository.customersWhichHadGivenService(94L)).thenReturn(customers);

        assertEquals(customers, customerService.customersWhichHadGivenService(94L));

    }

    @Test
    public void customersBornInGivenYearAndWhichHadServiceFromGivenCategoryInGivenYear() throws ParseException {

        Customer customer1 = new Customer(9354648266L, "ANIA", "KOWALSKA");
        Customer customer2 = new Customer(9354648263L, "Tomek", "KOWALSKi");

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

        when(cosmeticServicesHistoryRepository.findAll()).thenReturn(cosmeticServicesHistories);

        assertEquals(customers, customerService.customersBornInGivenYearAndWhichHadServiceFromGivenCategoryInGivenYear(93, 2L, 2017));
    }
}