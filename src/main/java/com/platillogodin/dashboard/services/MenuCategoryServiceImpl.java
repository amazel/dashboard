package com.platillogodin.dashboard.services;

import com.platillogodin.dashboard.domain.MenuCategory;
import com.platillogodin.dashboard.exceptions.NotFoundException;
import com.platillogodin.dashboard.repositories.MenuCategoryRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Hugo Lezama on August - 2018
 */
@Slf4j
@Service
public class MenuCategoryServiceImpl implements MenuCategoryService {
    private final MenuCategoryRepository menuCategoryRepository;

    public MenuCategoryServiceImpl(MenuCategoryRepository menuCategoryRepository) {
        this.menuCategoryRepository = menuCategoryRepository;
    }

    @Override
    public List<MenuCategory> findAll() {
        return menuCategoryRepository.findAll();
    }

    @Override
    public MenuCategory findById(Long id) {
        return menuCategoryRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("La categoria de menu " + id + " no existe"));
    }

    @Override
    public MenuCategory saveMenuCategory(MenuCategory menuCategory) {
        return menuCategoryRepository.save(menuCategory);
    }

    @Override
    public void delete(MenuCategory menuCategory) {

        menuCategoryRepository.delete(menuCategory);
    }
}
