package com.marekzolek.service;

import com.marekzolek.exception.CategoryNotFoundException;
import com.marekzolek.exception.CustomerNotFoundException;
import com.marekzolek.model.CosmeticServiceCategory;
import com.marekzolek.repository.CosmeticServiceCategoryRepository;
import com.marekzolek.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CosmeticServiceCategoryServiceImpl implements CosmeticServiceCategoryService {

    @Autowired
    private CosmeticServiceCategoryRepository cosmeticServiceCategoryRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Override
    public CosmeticServiceCategory add(CosmeticServiceCategory cosmeticServiceCategory) {
        return cosmeticServiceCategoryRepository.save(cosmeticServiceCategory);
    }

    @Override
    public void delete(Long id) {
        cosmeticServiceCategoryRepository.deleteById(id);
    }

    @Override
    public CosmeticServiceCategory findOne(Long id) throws CategoryNotFoundException {

        CosmeticServiceCategory cosmeticServiceCategory;

        Optional<CosmeticServiceCategory> customerOptional = cosmeticServiceCategoryRepository.findById(id);

        if (!customerOptional.isPresent()) {
            throw new CategoryNotFoundException("Category with id = " + id + " not exist");
        } else {
            cosmeticServiceCategory = customerOptional.get();
        }
        return cosmeticServiceCategory;
    }

    @Override
    public List<CosmeticServiceCategory> findAll() {
        return cosmeticServiceCategoryRepository.findAll();
    }

    @Override
    public List<CosmeticServiceCategory> namesOfCategoryServiceOfGivenCustomer(Long customerId) throws CustomerNotFoundException {

        List<CosmeticServiceCategory> cosmeticServiceCategories;

        if (customerRepository.findById(customerId).isPresent()) {
            cosmeticServiceCategories = cosmeticServiceCategoryRepository.namesOfCategoryServiceOfGivenCustomer(customerId);
        } else {
            throw new CustomerNotFoundException("Customer with id = " + customerId + " does not exist");
        }
        return cosmeticServiceCategories;
    }

    @Override
    public List<CosmeticServiceCategory> findAllByServiceType(String type) {

        return cosmeticServiceCategoryRepository.findAll().stream()
                .filter(cosmeticServiceCategory -> cosmeticServiceCategory.getCosmeticServices().stream()
                        .anyMatch(cosmeticService -> cosmeticService.getType().equals(type))).collect(Collectors.toList());
    }

    @Override
    public CosmeticServiceCategory categoryWithTheLargestNumberOfServices() throws CategoryNotFoundException {

        CosmeticServiceCategory cosmeticServiceCategory;

        Optional<CosmeticServiceCategory> cosmeticServiceCategoryOptional = cosmeticServiceCategoryRepository.findAll().stream()
                .max((o1, o2) -> o1.getCosmeticServices().size() > o2.getCosmeticServices().size() ? 1 : -1);

        if (cosmeticServiceCategoryOptional.isPresent()) {
            cosmeticServiceCategory = cosmeticServiceCategoryOptional.get();
        } else {
            throw new CategoryNotFoundException("Category not found");
        }
        return cosmeticServiceCategory;
    }

    @Override
    public CosmeticServiceCategory categoryWithTheLeastNumberOfServices() throws CategoryNotFoundException {

        CosmeticServiceCategory cosmeticServiceCategory;

        Optional<CosmeticServiceCategory> cosmeticServiceCategoryOptional = cosmeticServiceCategoryRepository.findAll().stream()
                .min((o1, o2) -> o1.getCosmeticServices().size() > o2.getCosmeticServices().size() ? 1 : -1);

        if (cosmeticServiceCategoryOptional.isPresent()) {
            cosmeticServiceCategory = cosmeticServiceCategoryOptional.get();
        } else {
            throw new CategoryNotFoundException("Category not found");
        }
        return cosmeticServiceCategory;
    }

}
