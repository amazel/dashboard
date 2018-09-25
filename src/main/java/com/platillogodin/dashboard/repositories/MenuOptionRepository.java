package com.platillogodin.dashboard.repositories;

import com.platillogodin.dashboard.commands.CategoryTotal;
import com.platillogodin.dashboard.domain.MenuOption;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Created by Hugo Lezama on August - 2018
 */
public interface MenuOptionRepository extends JpaRepository<MenuOption, Long> {

    @Query("SELECT new com.platillogodin.dashboard.commands.CategoryTotal(mo.menu.id, mo.menuCategory.name, " +
            "SUM(CASE WHEN mo.menuOptionType = 'MAIN' THEN mo.actualQuantity ELSE 0 END), " +
            "SUM(mo.cost)) " +
            "FROM MenuOption mo " +
            "WHERE mo.cost > 0 " +
            "GROUP BY mo.menu.id, mo.menuCategory.name " +
            "ORDER BY mo.menu.id, mo.menuCategory.name")
    List<CategoryTotal> getTotalsByCategory(Pageable pageable);

}
