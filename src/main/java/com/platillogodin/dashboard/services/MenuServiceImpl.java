package com.platillogodin.dashboard.services;

import com.platillogodin.dashboard.domain.Menu;
import com.platillogodin.dashboard.repositories.MenuRepository;
import org.springframework.stereotype.Service;

/**
 * Created by Hugo Lezama on August - 2018
 */
@Service
public class MenuServiceImpl implements MenuService {

    private final MenuRepository menuRepository;

    public MenuServiceImpl(MenuRepository menuRepository) {
        this.menuRepository = menuRepository;
    }

    @Override
    public Menu findById(String id) {
        return menuRepository.findById(id)
                .orElse(menuRepository.save(new Menu(id)));
    }
}
