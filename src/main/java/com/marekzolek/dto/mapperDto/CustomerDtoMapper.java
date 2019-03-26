package com.marekzolek.dto.mapperDto;

import com.marekzolek.dto.CustomerDto;
import com.marekzolek.model.Customer;
import org.springframework.stereotype.Component;

@Component
public class CustomerDtoMapper {

    public CustomerDto customerToDto(Customer customer) {
        CustomerDto customerDto = new CustomerDto();
        customerDto.setName(customer.getName());
        customerDto.setSurname(customer.getSurname());
        customerDto.setPesel(customer.getPesel());
        return customerDto;
    }
}
