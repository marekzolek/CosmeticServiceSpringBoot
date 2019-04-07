package com.marekzolek.service;

import com.marekzolek.exception.CosmeticServiceNotFoundException;
import com.marekzolek.exception.CustomerNotFoundException;
import com.marekzolek.exception.HistoryNotFoundException;
import com.marekzolek.model.CosmeticService;
import com.marekzolek.model.CosmeticServicesHistory;
import com.marekzolek.repository.CosmeticServicesHistoryRepository;
import com.marekzolek.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class CosmeticServicesHistoryServiceImpl implements CosmeticServicesHistoryService {

    @Autowired
    private CosmeticServicesHistoryRepository cosmeticServicesHistoryRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Override
    public CosmeticServicesHistory add(final CosmeticServicesHistory cosmeticServicesHistory) {
        return cosmeticServicesHistoryRepository.save(cosmeticServicesHistory);
    }

    @Override
    public void delete(final Long id) throws HistoryNotFoundException {
        if (cosmeticServicesHistoryRepository.findById(id).isPresent()) {
            cosmeticServicesHistoryRepository.deleteById(id);
        } else {
            throw new HistoryNotFoundException("History with id = " + id + " does not exist");
        }
    }

    @Override
    public CosmeticServicesHistory findOne(final Long id) {
        CosmeticServicesHistory cosmeticServicesHistory = null;

        Optional<CosmeticServicesHistory> cosmeticServicesHistoryOptional = cosmeticServicesHistoryRepository.findById(id);
        try {
            if (!cosmeticServicesHistoryOptional.isPresent()) {
                throw new HistoryNotFoundException("History with id = " + id + " does not exist");
            } else {
                cosmeticServicesHistory = cosmeticServicesHistoryOptional.get();
            }
        } catch (HistoryNotFoundException e) {
            e.printStackTrace();
        }
        return cosmeticServicesHistory;
    }

    @Override
    public List<CosmeticServicesHistory> findAll() {
        return cosmeticServicesHistoryRepository.findAll();
    }

    @Override
    public Integer sumOfPricesAllServicesOfGivenCustomerTest(final Long customerId) throws CustomerNotFoundException {
        Integer result;

        if (customerRepository.findById(customerId).isPresent()) {
            result = cosmeticServicesHistoryRepository.findTotalPriceOfServicesOfGivenCustomer(customerId);
        } else {
            throw new CustomerNotFoundException("Customer with id = " + customerId + " does not exist");
        }
        return result;

    }

    @Override
    public Integer countEachServicesOfGivenCustomer(final Long customerId) throws CustomerNotFoundException {

        Integer result;

        if (customerRepository.findById(customerId).isPresent()) {
            result = cosmeticServicesHistoryRepository.countEachServicesOfGivenCustomer(customerId);
        } else {
            throw new CustomerNotFoundException("Customer with id = " + customerId + " does not exist");
        }

        return result;
    }

    @Override
    public CosmeticService theMostPopularService() throws CosmeticServiceNotFoundException {

        Map<CosmeticService, Integer> cosmeticServiceIntegerMap = new HashMap<>();
        Optional<CosmeticService> cosmeticServiceOptional;
        CosmeticService cosmeticService;

        cosmeticServiceOptional = cosmeticServicesHistoryRepository.findAll().stream().map(cosmeticServicesHistory -> cosmeticServicesHistory.getCosmeticService()).max(
                (o1, o2) -> cosmeticServicesHistoryRepository.findAll().stream().filter(cosmeticServicesHistory -> cosmeticServicesHistory.getCosmeticService().equals(o1)).count()
                        >
                        cosmeticServicesHistoryRepository.findAll().stream().filter(cosmeticServicesHistory -> cosmeticServicesHistory.getCosmeticService().equals(o2)).count() ? 1 : -1
        );

        if (cosmeticServiceOptional.isPresent()) {
            cosmeticService = cosmeticServiceOptional.get();
        } else {
            throw new CosmeticServiceNotFoundException("Service not found");
        }
        return cosmeticService;
    }

    @Override
    public CosmeticService leastPopularService() throws CosmeticServiceNotFoundException {


        Map<CosmeticService, Integer> cosmeticServiceIntegerMap = new HashMap<>();

        Optional<CosmeticService> cosmeticServiceOptional;
        CosmeticService cosmeticService;

        cosmeticServiceOptional = cosmeticServicesHistoryRepository.findAll().stream().map(cosmeticServicesHistory -> cosmeticServicesHistory.getCosmeticService()).min(
                (o1, o2) -> cosmeticServicesHistoryRepository.findAll().stream().filter(cosmeticServicesHistory -> cosmeticServicesHistory.getCosmeticService().equals(o1)).count()
                        >
                        cosmeticServicesHistoryRepository.findAll().stream().filter(cosmeticServicesHistory -> cosmeticServicesHistory.getCosmeticService().equals(o2)).count() ? 1 : -1
        );
        if (cosmeticServiceOptional.isPresent()) {
            cosmeticService = cosmeticServiceOptional.get();
        } else {
            throw new CosmeticServiceNotFoundException("Service not found");
        }
        return cosmeticService;
    }
}
