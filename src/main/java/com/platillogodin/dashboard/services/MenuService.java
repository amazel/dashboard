package com.platillogodin.dashboard.services;

import com.platillogodin.dashboard.domain.Menu;
import com.platillogodin.dashboard.exceptions.GenericException;

/**
 * Created by Hugo Lezama on August - 2018
 */
public interface MenuService {
    Menu findById(String id);

    Menu save(Menu menu);

    void processMenuDay(Menu menu) throws GenericException;
}
