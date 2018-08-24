package com.platillogodin.dashboard.services;

import com.platillogodin.dashboard.domain.Menu;

/**
 * Created by Hugo Lezama on August - 2018
 */
public interface MenuService {
    Menu findById(String id);

    Menu save(Menu menu);
}
