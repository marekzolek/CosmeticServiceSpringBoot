package com.marekzolek.service;

import com.marekzolek.dto.CustomerDto;
import com.marekzolek.exception.CustomerNotFoundException;
import com.marekzolek.model.Customer;

import java.text.ParseException;
import java.util.List;

public interface CustomerService {

    Customer add(Customer customer);

    void delete(Long id);

    Customer findOne(Long id) throws CustomerNotFoundException;

    List<Customer> findAll();

    List<Customer> customersBornBeforeYear(Integer year);

    List<Customer> customersWhichHadServicesInYear(Integer year) throws ParseException;

    List<Customer> womanBornBeforeYear(Integer year);

    List<Customer> mansBornBeforeYear(Integer year);

    List<CustomerDto> customersWithSumOfServicesInGivenYear(Integer year) throws ParseException;

    List<Customer> customersWhichHadGivenService(Long serviceId);

    List<Customer> customersBornInGivenYearAndWhichHadServiceFromGivenCategoryInGivenYear(Integer yearOfBirth, Long categoryId, Integer yearOfService) throws ParseException;
}
