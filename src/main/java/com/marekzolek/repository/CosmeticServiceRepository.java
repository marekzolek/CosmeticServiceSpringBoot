package com.marekzolek.repository;

import com.marekzolek.model.CosmeticService;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CosmeticServiceRepository extends JpaRepository<CosmeticService, Long> {

    List<CosmeticService> findAllByType(String type);

    @Query(value = "SELECT c from CosmeticService c order by c.price desc")
    List<CosmeticService> findTheMostExpensiveService();

    @Query(value = "SELECT c from CosmeticService c order by c.price asc")
    List<CosmeticService> findTheCheapestService();

    Integer countServicesByType(String type);
}
