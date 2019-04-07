package com.marekzolek.repository;

import com.marekzolek.model.CosmeticService;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CosmeticServiceRepository extends JpaRepository<CosmeticService, Long> {

    @Query(value = "Select s from CosmeticService s where name like :letter%")
    List<CosmeticService> findByNameWhereNameStartWithLetter(final String letter);

    @Query(value = "SELECT s from CosmeticService s where price > :price")
    List<CosmeticService> findAllByPriceGraterThenGivenPrice(final Integer price);

    List<CosmeticService> findAllByType(final String type);

    @Query(value = "SELECT c from CosmeticService c order by c.price desc")
    List<CosmeticService> findTheMostExpensiveService();

    @Query(value = "SELECT c from CosmeticService c order by c.price asc")
    List<CosmeticService> findTheCheapestService();

    Integer countServicesByType(final String type);
}
