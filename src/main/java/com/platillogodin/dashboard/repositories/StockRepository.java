package com.platillogodin.dashboard.repositories;

import com.platillogodin.dashboard.domain.Ingredient;
import com.platillogodin.dashboard.domain.Stock;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

/**
 * Created by Hugo Lezama on August - 2018
 */
public interface StockRepository extends JpaRepository<Stock, Long> {
    @Query("SELECT stock " +
            "FROM Stock stock JOIN FETCH stock.stockEntries ent " +
            "WHERE stock.id = :stockId AND ent.currentQty > 0")
    Optional<Stock> findByIdFiltered(@Param("stockId") Long stockId);

    Stock findByIngredient(Ingredient ingredient);

    List<Stock> findAllByIngredientIn(List<Ingredient> ingredients);
}
