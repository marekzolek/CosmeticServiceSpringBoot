package com.marekzolek.repository;

import com.marekzolek.model.CosmeticServicesHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;


public interface CosmeticServicesHistoryRepository extends JpaRepository<CosmeticServicesHistory, Long> {

    @Query(value = "SELECT sum(s.price) from CosmeticServicesHistory h join h.customer c join h.cosmeticService s where c.id=?1")
    Integer findTotalPriceOfServicesOfGivenCustomer(Long customerId);

    @Query(value = "select count(h.cosmeticService) from CosmeticServicesHistory h where h.customer.id=?1")
    Integer countEachServicesOfGivenCustomer(Long customerId);
}
