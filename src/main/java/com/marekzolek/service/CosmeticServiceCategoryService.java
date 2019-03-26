package com.marekzolek.service;

import com.marekzolek.exception.CategoryNotFoundException;
import com.marekzolek.exception.CustomerNotFoundException;
import com.marekzolek.model.CosmeticServiceCategory;

import java.util.List;

public interface CosmeticServiceCategoryService {

    CosmeticServiceCategory add(CosmeticServiceCategory cosmeticServiceCategory);

    void delete(Long id);

    CosmeticServiceCategory findOne(Long id) throws CategoryNotFoundException;

    List<CosmeticServiceCategory> findAll();

    List<CosmeticServiceCategory> namesOfCategoryServiceOfGivenCustomer(Long customerId) throws CustomerNotFoundException;

    List<CosmeticServiceCategory> findAllByServiceType(String type);

    CosmeticServiceCategory categoryWithTheLargestNumberOfServices() throws CategoryNotFoundException;

    CosmeticServiceCategory categoryWithTheLeastNumberOfServices() throws CategoryNotFoundException;

}
