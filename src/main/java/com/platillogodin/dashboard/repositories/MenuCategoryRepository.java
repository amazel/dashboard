package com.platillogodin.dashboard.repositories;

import com.platillogodin.dashboard.domain.MenuCategory;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by Hugo Lezama on August - 2018
 */
public interface MenuCategoryRepository extends JpaRepository<MenuCategory, Long> {
}
