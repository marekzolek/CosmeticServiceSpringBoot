package com.marekzolek.repository;

import com.marekzolek.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CustomerRepository extends JpaRepository<Customer, Long> {

    @Query(value = "select h.customer from CosmeticServicesHistory h where h.cosmeticService.id=?1")
    List<Customer> customersWhichHadGivenService(Long serviceId);
}
