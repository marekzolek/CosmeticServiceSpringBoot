package com.marekzolek.controller;


import com.marekzolek.exception.CategoryNotFoundException;
import com.marekzolek.exception.CustomerNotFoundException;
import com.marekzolek.model.CosmeticServiceCategory;
import com.marekzolek.service.CosmeticServiceCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/cosmeticServiceCategory")
public class CosmeticServiceCategoryController {


    @Autowired
    private CosmeticServiceCategoryService cosmeticServiceCategoryService;

    @PostMapping("/add")
    public CosmeticServiceCategory add(@RequestBody CosmeticServiceCategory cosmeticServiceCategory) {
        return cosmeticServiceCategoryService.add(cosmeticServiceCategory);
    }

    @DeleteMapping("/delete")
    public void delete(@RequestParam Long id) {
        cosmeticServiceCategoryService.delete(id);
    }

    @GetMapping("/{id}")
    public CosmeticServiceCategory findOne(@PathVariable Long id) throws CategoryNotFoundException {
        return cosmeticServiceCategoryService.findOne(id);
    }

    @GetMapping("/all")
    public List<CosmeticServiceCategory> findAll() {
        return cosmeticServiceCategoryService.findAll();
    }

    @GetMapping("/namesOfCategoryServiceOfGivenCustomer")
    public List<CosmeticServiceCategory> namesOfCategoryServiceOfGivenCustomer(@RequestParam Long customerId) throws CustomerNotFoundException {
        return cosmeticServiceCategoryService.namesOfCategoryServiceOfGivenCustomer(customerId);
    }

    @GetMapping("/havingServicesType")
    public List<CosmeticServiceCategory> findAllByServiceType(@RequestParam String type) {
        return cosmeticServiceCategoryService.findAllByServiceType(type);
    }

    @GetMapping("/categoryWithTheLargestNumberOfServices")
    public CosmeticServiceCategory categoryWithTheLargestNumberOfServices() throws CategoryNotFoundException {
        return cosmeticServiceCategoryService.categoryWithTheLargestNumberOfServices();
    }

    @GetMapping("/categoryWithTheLeastNumberOfServices")
    public CosmeticServiceCategory categoryWithTheLeastNumberOfServices() throws CategoryNotFoundException {
        return cosmeticServiceCategoryService.categoryWithTheLeastNumberOfServices();
    }
}
