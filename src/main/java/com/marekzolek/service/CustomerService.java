package com.marekzolek.service;

import com.marekzolek.dto.CustomerDto;
import com.marekzolek.exception.CustomerNotFoundException;
import com.marekzolek.model.Customer;

import java.text.ParseException;
import java.util.List;

public interface CustomerService {

    Customer add(final Customer customer);

    void delete(final Long id);

    Customer findOne(final Long id) throws CustomerNotFoundException;

    List<Customer> findAll();

    List<Customer> customersBornBeforeYear(final Integer year);

    List<Customer> customersWhichHadServicesInYear(final Integer year) throws ParseException;

    List<Customer> womanBornBeforeYear(final Integer year);

    List<Customer> mansBornBeforeYear(final Integer year);

    List<CustomerDto> customersWithSumOfServicesInGivenYear(final Integer year) throws ParseException;

    List<Customer> customersWhichHadGivenService(final Long serviceId);

    List<Customer> customersBornInGivenYearAndWhichHadServiceFromGivenCategoryInGivenYear(final Integer yearOfBirth, final Long categoryId, final Integer yearOfService) throws ParseException;
}
