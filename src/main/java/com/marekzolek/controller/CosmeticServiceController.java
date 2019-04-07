package com.marekzolek.controller;


import com.marekzolek.exception.CategoryNotFoundException;
import com.marekzolek.exception.CosmeticServiceNotFoundException;
import com.marekzolek.model.CosmeticService;
import com.marekzolek.service.CosmeticServiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cosmeticService")
public class CosmeticServiceController {

    @Autowired
    private CosmeticServiceService cosmeticServiceService;

    @PostMapping("/add")
    public CosmeticService add(@RequestBody final CosmeticService cosmeticService) {
        return cosmeticServiceService.add(cosmeticService);
    }

    @DeleteMapping("/delete")
    public void delete(@RequestParam final Long id) {
        cosmeticServiceService.delete(id);
    }

    @GetMapping("/{id}")
    public CosmeticService findOne(@PathVariable final Long id) throws CosmeticServiceNotFoundException {
        return cosmeticServiceService.findOne(id);
    }

    @GetMapping("/all")
    public List<CosmeticService> findAllCosmeticServices() {
        return cosmeticServiceService.findAll();
    }

    @GetMapping("/withCategoryId/{categoryId}")
    public List<CosmeticService> findCosmeticServicesWithCategoryId(@PathVariable final Long categoryId) throws CategoryNotFoundException {
        return cosmeticServiceService.findCosmeticServicesWithCategoryId(categoryId);
    }

    @GetMapping("/type")
    public List<CosmeticService> findAllByType(@RequestParam final String type) {
        return cosmeticServiceService.findAllByType(type);
    }

    @GetMapping("/theMostExpensiveService")
    public CosmeticService findTheMostExpensiveService() {
        return cosmeticServiceService.findTheMostExpensiveService();
    }

    @GetMapping("/theCheapestService")
    public CosmeticService findTheCheapestService() {
        return cosmeticServiceService.findCheapestService();
    }

    @GetMapping("/numberOfServicesByType")
    public Integer countServicesByType(@RequestParam final String type) {
        return cosmeticServiceService.countServicesByType(type);
    }

    @GetMapping("/cosmeticServiceStartWithLetter/{letter}")
    public List<CosmeticService> findByNameWhereNameStartWithLetter(@PathVariable final String letter) {
        return cosmeticServiceService.findByNameWhereNameStartWithLetter(letter);
    }

    @GetMapping("/findAllByPriceGraterThenGivenPrice/{price}")
    public List<CosmeticService> findAllByPriceGraterThenGivenPrice(@PathVariable final Integer price) {
        return cosmeticServiceService.findAllByPriceGraterThenGivenPrice(price);
    }
}
