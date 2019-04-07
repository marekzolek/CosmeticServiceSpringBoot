package com.marekzolek.service;

import com.marekzolek.exception.CategoryNotFoundException;
import com.marekzolek.exception.CosmeticServiceNotFoundException;
import com.marekzolek.model.CosmeticService;

import java.util.List;

public interface CosmeticServiceService {

    CosmeticService add(final CosmeticService cosmeticService);

    void delete(final Long id);

    CosmeticService findOne(final Long id) throws CosmeticServiceNotFoundException;

    List<CosmeticService> findAll();

    List<CosmeticService> findCosmeticServicesWithCategoryId(final Long id) throws CategoryNotFoundException;

    List<CosmeticService> findAllByType(final String type);

    CosmeticService findTheMostExpensiveService();

    CosmeticService findCheapestService();

    Integer countServicesByType(final String type);

    List<CosmeticService> findByNameWhereNameStartWithLetter(final String letter);

    List<CosmeticService> findAllByPriceGraterThenGivenPrice(final Integer price);
}
