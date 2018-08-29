package com.platillogodin.dashboard.repositories;

import com.platillogodin.dashboard.domain.User;
import com.platillogodin.dashboard.domain.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by Hugo Lezama on August - 2018
 */
public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);
    int countAllByRole(UserRole userRole);
}
