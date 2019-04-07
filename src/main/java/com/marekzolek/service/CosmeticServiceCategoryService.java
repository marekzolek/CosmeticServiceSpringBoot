package com.marekzolek.service;

import com.marekzolek.exception.CategoryNotFoundException;
import com.marekzolek.exception.CustomerNotFoundException;
import com.marekzolek.model.CosmeticServiceCategory;

import java.util.List;

public interface CosmeticServiceCategoryService {

    CosmeticServiceCategory add(final CosmeticServiceCategory cosmeticServiceCategory);

    void delete(final Long id);

    CosmeticServiceCategory findOne(final Long id) throws CategoryNotFoundException;

    List<CosmeticServiceCategory> findAll();

    List<CosmeticServiceCategory> namesOfCategoryServiceOfGivenCustomer(final Long customerId) throws CustomerNotFoundException;

    List<CosmeticServiceCategory> findAllByServiceType(final String type);

    CosmeticServiceCategory categoryWithTheLargestNumberOfServices() throws CategoryNotFoundException;

    CosmeticServiceCategory categoryWithTheLeastNumberOfServices() throws CategoryNotFoundException;

    Integer countNumberOfCosmeticServicesCostGivenPrice(final Integer price);
}
