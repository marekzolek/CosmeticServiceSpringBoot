package com.marekzolek.controller;


import com.marekzolek.dto.CustomerDto;
import com.marekzolek.exception.CustomerNotFoundException;
import com.marekzolek.model.Customer;
import com.marekzolek.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.*;

@RestController
@RequestMapping("/customers")
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    @PostMapping("/add")
    public Customer add(@RequestBody Customer customer) {
        return customerService.add(customer);
    }

    @DeleteMapping("/delete")
    public void delete(@RequestParam Long id) {
        customerService.delete(id);
    }

    @GetMapping("/{id}")
    public Customer findOne(@PathVariable Long id) throws CustomerNotFoundException {
        return customerService.findOne(id);
    }

    @GetMapping("/all")
    public List<Customer> findAll() {
        return customerService.findAll();
    }

    @GetMapping("/bornBefore")
    public List<Customer> customersBornBeforeYear(@RequestParam Integer year) {
        return customerService.customersBornBeforeYear(year);
    }

    @GetMapping("/withServicesInYear")
    public List<Customer> customersWhichHadServicesInYear(@RequestParam Integer year) throws ParseException {
        return customerService.customersWhichHadServicesInYear(year);
    }

    @GetMapping("/womanBornBefore")
    public List<Customer> womanBornBeforeYear(@RequestParam Integer year) {
        return customerService.womanBornBeforeYear(year);
    }

    @GetMapping("/mansBornBefore")
    public List<Customer> mansBornBeforeYear(@RequestParam Integer year) {
        return customerService.mansBornBeforeYear(year);
    }

    @GetMapping("/servicesInYear")
    public List<CustomerDto> customersWithSumOfServicesInGivenYear(@RequestParam Integer year) throws ParseException {
        return customerService.customersWithSumOfServicesInGivenYear(year);
    }

    @GetMapping("/hadService")
    public List<Customer> customersWhichHadGivenService(@RequestParam Long serviceId) {
        return customerService.customersWhichHadGivenService(serviceId);
    }

    @GetMapping("/bornInHadServiceFromCategoryInYear")
    public List<Customer> customersBornInGivenYearAndWhichHadServiceFromGivenCategoryInGivenYear(@RequestParam Integer yearOfBirth, @RequestParam Long categoryId, @RequestParam Integer yearOfService) throws ParseException {
        return customerService.customersBornInGivenYearAndWhichHadServiceFromGivenCategoryInGivenYear(yearOfBirth, categoryId, yearOfService);
    }

}


