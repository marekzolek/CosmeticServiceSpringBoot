package com.marekzolek.service;

import com.marekzolek.exception.CategoryNotFoundException;
import com.marekzolek.exception.CosmeticServiceNotFoundException;
import com.marekzolek.model.CosmeticService;
import com.marekzolek.model.CosmeticServiceCategory;
import com.marekzolek.repository.CosmeticServiceCategoryRepository;
import com.marekzolek.repository.CosmeticServiceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CosmeticServiceServiceImpl implements CosmeticServiceService {

    @Autowired
    private CosmeticServiceRepository cosmeticServiceRepository;

    @Autowired
    private CosmeticServiceCategoryRepository cosmeticServiceCategoryRepository;

    @Override
    public CosmeticService add(final CosmeticService cosmeticService) {
        return cosmeticServiceRepository.save(cosmeticService);
    }

    @Override
    public void delete(final Long id) {
        cosmeticServiceRepository.deleteById(id);
    }

    @Override
    public CosmeticService findOne(final Long id) throws CosmeticServiceNotFoundException {

        CosmeticService cosmeticService;

        Optional<CosmeticService> cosmeticServiceOptional = cosmeticServiceRepository.findById(id);
        if (!cosmeticServiceOptional.isPresent()) {
            throw new CosmeticServiceNotFoundException("Service with id = " + id + " does not exist");
        } else {
            cosmeticService = cosmeticServiceOptional.get();
        }
        return cosmeticService;
    }

    @Override
    public List<CosmeticService> findAll() {
        return cosmeticServiceRepository.findAll();
    }

    @Override
    public List<CosmeticService> findCosmeticServicesWithCategoryId(final Long id) throws CategoryNotFoundException {

        List<CosmeticService> cosmeticServices = new ArrayList<>();

        Optional<CosmeticServiceCategory> cosmeticServiceCategory = cosmeticServiceCategoryRepository.findById(id);

        if (!cosmeticServiceCategory.isPresent()) {
            throw new CategoryNotFoundException("Category with id = " + id + " does not exist");
        } else {
            for (CosmeticService cosmeticService : cosmeticServiceRepository.findAll()) {
                if (cosmeticService.getCategory().getId() == id) {
                    cosmeticServices.add(cosmeticService);
                }
            }
        }

        return cosmeticServices;
    }

    @Override
    public List<CosmeticService> findAllByType(final String type) {
        return cosmeticServiceRepository.findAllByType(type);
    }

    @Override
    public CosmeticService findTheMostExpensiveService() {
        return cosmeticServiceRepository.findTheMostExpensiveService().get(0);
    }

    @Override
    public CosmeticService findCheapestService() {
        return cosmeticServiceRepository.findTheCheapestService().get(0);
    }

    @Override
    public Integer countServicesByType(final String type) {
        return cosmeticServiceRepository.countServicesByType(type);
    }

    @Override
    public List<CosmeticService> findByNameWhereNameStartWithLetter(final String letter) {

        return cosmeticServiceRepository.findByNameWhereNameStartWithLetter(letter);

    }

    @Override
    public List<CosmeticService> findAllByPriceGraterThenGivenPrice(final Integer price) {
        return cosmeticServiceRepository.findAllByPriceGraterThenGivenPrice(price);
    }
}
