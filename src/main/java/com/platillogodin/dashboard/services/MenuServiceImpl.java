package com.platillogodin.dashboard.services;

import com.platillogodin.dashboard.domain.Menu;
import com.platillogodin.dashboard.repositories.MenuRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

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
                .orElse(save(new Menu(id)));
    }

    public Menu save(Menu menu) {
        if (menu.getDate() == null) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
            menu.setDate(LocalDate.parse(menu.getId(), formatter));
        }
        return menuRepository.save(menu);
    }
}
