package com.platillogodin.dashboard.services;

import com.platillogodin.dashboard.domain.User;

import java.util.List;

/**
 * Created by Hugo Lezama on August - 2018
 */
public interface UserService {

    User findByUsername(String username);

    List<User> findAll();

    User findById(Long id);

    void delete(User user);

    User saveUser(User user);

    void updatePassword(User user, String password);
}
