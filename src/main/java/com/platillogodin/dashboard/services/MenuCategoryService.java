package com.platillogodin.dashboard.services;

import com.platillogodin.dashboard.domain.MenuCategory;

import java.util.List;

/**
 * Created by Hugo Lezama on August - 2018
 */

public interface MenuCategoryService {
    List<MenuCategory> findAll();

    MenuCategory findById(Long id);

    MenuCategory saveMenuCategory(MenuCategory menuCategory);

    void delete(MenuCategory menuCategory);
}
