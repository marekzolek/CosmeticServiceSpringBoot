package com.marekzolek.service;

import com.marekzolek.dto.CustomerDto;
import com.marekzolek.dto.mapperDto.CustomerDtoMapper;
import com.marekzolek.exception.CustomerNotFoundException;
import com.marekzolek.model.CosmeticServicesHistory;
import com.marekzolek.model.Customer;
import com.marekzolek.repository.CosmeticServicesHistoryRepository;
import com.marekzolek.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class CustomerServiceImpl implements CustomerService {


    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private CosmeticServicesHistoryRepository cosmeticServicesHistoryRepository;

    @Autowired
    private CustomerDtoMapper customerDtoMapper;

    @Override
    public Customer add(Customer customer) {
        return customerRepository.save(customer);
    }

    @Override
    public void delete(Long id) {
        customerRepository.deleteById(id);
    }

    @Override
    public Customer findOne(Long id) throws CustomerNotFoundException {

        Customer customer;

        Optional<Customer> customerOptional = customerRepository.findById(id);

        if (!customerOptional.isPresent()) {
            throw new CustomerNotFoundException("Customer with id = " + id + " does not exist");
        } else {
            customer = customerOptional.get();
        }

        return customer;
    }

    @Override
    public List<Customer> findAll() {
        return customerRepository.findAll();
    }

    @Override
    public List<Customer> customersBornBeforeYear(Integer year) {

        List<Customer> customers = new ArrayList<>();
        int givenYear = Integer.parseInt(year.toString().substring(0, 2));
        int yearOfBirth;

        for (Customer customer : customerRepository.findAll()) {
            yearOfBirth = Integer.parseInt(customer.getPesel().toString().substring(0, 2));
            if (givenYear > yearOfBirth) {
                customers.add(customer);
            }
        }
        return customers;
    }

    @Override
    public List<Customer> customersWhichHadServicesInYear(Integer year) throws ParseException {

        List<Customer> customers = new ArrayList<>();

        Date date;
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        for (CosmeticServicesHistory cosmeticServicesHistory : cosmeticServicesHistoryRepository.findAll()) {
            date = sdf.parse(cosmeticServicesHistory.getDate());
            calendar.setTime(date);
            if (calendar.get(Calendar.YEAR) == year) {
                customers.add(cosmeticServicesHistory.getCustomer());
            }
        }

        return customers;
    }

    @Override
    public List<Customer> womanBornBeforeYear(Integer year) {

        List<Customer> customers = new ArrayList<>();
        int customerYearOfBirth;

        for (Customer customer : customerRepository.findAll()) {
            customerYearOfBirth = Integer.parseInt(customer.getPesel().toString().substring(0, 2));
            if (customerYearOfBirth < year && customer.getPesel().toString().charAt(9) % 2 == 0) {
                customers.add(customer);
            }
        }

        return customers;
    }

    @Override
    public List<Customer> mansBornBeforeYear(Integer year) {

        List<Customer> customers = new ArrayList<>();
        int customerYearOfBirth;

        for (Customer customer : customerRepository.findAll()) {
            customerYearOfBirth = Integer.parseInt(customer.getPesel().toString().substring(0, 2));
            if (customerYearOfBirth < year && customer.getPesel().toString().charAt(9) % 2 != 0) {
                customers.add(customer);
            }
        }

        return customers;
    }

    @Override
    public List<CustomerDto> customersWithSumOfServicesInGivenYear(Integer year) throws ParseException {

        List<CustomerDto> dtoCustomers = new ArrayList<>();
        Map<Customer, Integer> customerMap = new HashMap<>();

        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        CustomerDto customerDto;

        for (CosmeticServicesHistory cosmeticServicesHistory : cosmeticServicesHistoryRepository.findAll()) {
            calendar.setTime(sdf.parse(cosmeticServicesHistory.getDate()));
            if (calendar.get(Calendar.YEAR) == year) {
                if (customerMap.containsKey(cosmeticServicesHistory.getCustomer())) {
                    customerMap.put(cosmeticServicesHistory.getCustomer(), customerMap.get(cosmeticServicesHistory.getCustomer()) +
                            cosmeticServicesHistory.getCosmeticService().getPrice());
                } else {
                    customerMap.put(cosmeticServicesHistory.getCustomer(), cosmeticServicesHistory.getCosmeticService().getPrice());
                }
            }
        }

        for (Customer customer : customerMap.keySet()) {
            customerDto = customerDtoMapper.customerToDto(customer);
            customerDto.setSumPrice(customerMap.get(customer));
            dtoCustomers.add(customerDto);
        }
        return dtoCustomers;
    }

    @Override
    public List<Customer> customersWhichHadGivenService(Long serviceId) {
        return customerRepository.customersWhichHadGivenService(serviceId);
    }

    @Override
    public List<Customer> customersBornInGivenYearAndWhichHadServiceFromGivenCategoryInGivenYear(Integer yearOfBirth, Long categoryId, Integer yearOfService) throws ParseException {

        List<Customer> customers = new ArrayList<>();

        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        for (CosmeticServicesHistory cosmeticServicesHistory : cosmeticServicesHistoryRepository.findAll()) {
            calendar.setTime(sdf.parse(cosmeticServicesHistory.getDate()));
            if (calendar.get(Calendar.YEAR) == yearOfService) {
                if (cosmeticServicesHistory.getCosmeticService().getCategory().getId() == categoryId) {
                    if (Integer.parseInt(String.valueOf(cosmeticServicesHistory.getCustomer().getPesel()).substring(0, 2)) == yearOfBirth) {
                        customers.add(cosmeticServicesHistory.getCustomer());
                    }
                }
            }
        }
        return customers;
    }

}
