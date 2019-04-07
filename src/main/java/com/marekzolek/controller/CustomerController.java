package com.marekzolek.controller;


import com.marekzolek.dto.CustomerDto;
import com.marekzolek.exception.CustomerNotFoundException;
import com.marekzolek.model.Customer;
import com.marekzolek.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.List;

@RestController
@RequestMapping("/customers")
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    @PostMapping("/add")
    public Customer add(@RequestBody final Customer customer) {
        return customerService.add(customer);
    }

    @DeleteMapping("/delete")
    public void delete(@RequestParam final Long id) {
        customerService.delete(id);
    }

    @GetMapping("/{id}")
    public Customer findOne(@PathVariable final Long id) throws CustomerNotFoundException {
        return customerService.findOne(id);
    }

    @GetMapping("/all")
    public List<Customer> findAll() {
        return customerService.findAll();
    }

    @GetMapping("/bornBefore")
    public List<Customer> customersBornBeforeYear(@RequestParam final Integer year) {
        return customerService.customersBornBeforeYear(year);
    }

    @GetMapping("/withServicesInYear")
    public List<Customer> customersWhichHadServicesInYear(@RequestParam final Integer year) throws ParseException {
        return customerService.customersWhichHadServicesInYear(year);
    }

    @GetMapping("/womanBornBefore")
    public List<Customer> womanBornBeforeYear(@RequestParam final Integer year) {
        return customerService.womanBornBeforeYear(year);
    }

    @GetMapping("/mansBornBefore")
    public List<Customer> mansBornBeforeYear(@RequestParam final Integer year) {
        return customerService.mansBornBeforeYear(year);
    }

    @GetMapping("/servicesInYear")
    public List<CustomerDto> customersWithSumOfServicesInGivenYear(@RequestParam final Integer year) throws ParseException {
        return customerService.customersWithSumOfServicesInGivenYear(year);
    }

    @GetMapping("/hadService")
    public List<Customer> customersWhichHadGivenService(@RequestParam final Long serviceId) {
        return customerService.customersWhichHadGivenService(serviceId);
    }

    @GetMapping("/bornInHadServiceFromCategoryInYear")
    public List<Customer> customersBornInGivenYearAndWhichHadServiceFromGivenCategoryInGivenYear(@RequestParam final Integer yearOfBirth, @RequestParam final Long categoryId, @RequestParam final Integer yearOfService) throws ParseException {
        return customerService.customersBornInGivenYearAndWhichHadServiceFromGivenCategoryInGivenYear(yearOfBirth, categoryId, yearOfService);
    }

}


