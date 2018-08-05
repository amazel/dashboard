package com.platillogodin.dashboard.repositories;

import com.platillogodin.dashboard.domain.Recipe;
import com.platillogodin.dashboard.domain.Stock;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by Hugo Lezama on August - 2018
 */
public interface StockRepository extends JpaRepository<Stock, Long> {
}
