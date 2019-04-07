package com.marekzolek.repository;

import com.marekzolek.model.CosmeticServiceCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CosmeticServiceCategoryRepository extends JpaRepository<CosmeticServiceCategory, Long> {

    @Query(value = "SELECT s.category from CosmeticServicesHistory h join h.cosmeticService s where h.customer.id=?1")
    List<CosmeticServiceCategory> namesOfCategoryServiceOfGivenCustomer(final Long customerId);

    Integer countNumberOfCosmeticServicesCostGivenPrice(final Integer price);
}
