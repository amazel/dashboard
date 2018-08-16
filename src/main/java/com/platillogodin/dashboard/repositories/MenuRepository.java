package com.platillogodin.dashboard.repositories;

import com.platillogodin.dashboard.domain.Menu;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by Hugo Lezama on August - 2018
 */
public interface MenuRepository extends JpaRepository<Menu, String> {
}
