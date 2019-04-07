package com.marekzolek.controller;


import com.marekzolek.configuration.UrlConfiguration;
import com.marekzolek.exception.CategoryNotFoundException;
import com.marekzolek.exception.CustomerNotFoundException;
import com.marekzolek.model.CosmeticServiceCategory;
import com.marekzolek.service.CosmeticServiceCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cosmeticServiceCategory")
public class CosmeticServiceCategoryController {

    private UrlConfiguration urlConfiguration = new UrlConfiguration();

    @Autowired
    private CosmeticServiceCategoryService cosmeticServiceCategoryService;

    @PostMapping()
    public CosmeticServiceCategory add(@RequestBody final CosmeticServiceCategory cosmeticServiceCategory) {
        return cosmeticServiceCategoryService.add(cosmeticServiceCategory);
    }

    @DeleteMapping("/delete")
    public void delete(@RequestParam final Long id) {
        cosmeticServiceCategoryService.delete(id);
    }

    @GetMapping("/{id}")
    public CosmeticServiceCategory findOne(@PathVariable final Long id) throws CategoryNotFoundException {
        return cosmeticServiceCategoryService.findOne(id);
    }

    @GetMapping("/all")
    public List<CosmeticServiceCategory> findAll() {
        return cosmeticServiceCategoryService.findAll();
    }

    @GetMapping("/namesOfCategoryServiceOfGivenCustomer")
    public List<CosmeticServiceCategory> namesOfCategoryServiceOfGivenCustomer(@RequestParam final Long customerId) throws CustomerNotFoundException {
        return cosmeticServiceCategoryService.namesOfCategoryServiceOfGivenCustomer(customerId);
    }

    @GetMapping("/havingServicesType")
    public List<CosmeticServiceCategory> findAllByServiceType(@RequestParam final String type) {
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

    @GetMapping("/countNumberOfCosmeticServicesFromGivenCategoryId/{price}")
    public Integer countNumberOfCosmeticServicesFromGivenCategoryId(@PathVariable final Integer price) {
        return cosmeticServiceCategoryService.countNumberOfCosmeticServicesCostGivenPrice(price);
    }
}
