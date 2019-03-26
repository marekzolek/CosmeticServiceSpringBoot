package com.marekzolek.service;

import com.marekzolek.exception.CategoryNotFoundException;
import com.marekzolek.exception.CosmeticServiceNotFoundException;
import com.marekzolek.model.CosmeticService;

import java.util.List;

public interface CosmeticServiceService {

    CosmeticService add(CosmeticService cosmeticService);

    void delete(Long id);

    CosmeticService findOne(Long id) throws CosmeticServiceNotFoundException;

    List<CosmeticService> findAll();

    List<CosmeticService> findCosmeticServicesWithCategoryId(Long id) throws CategoryNotFoundException;

    List<CosmeticService> findAllByType(String type);

    CosmeticService findTheMostExpensiveService();

    CosmeticService findCheapestService();

    Integer countServicesByType(String type);
}
