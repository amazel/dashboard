package com.platillogodin.dashboard.services;

import com.platillogodin.dashboard.domain.User;
import com.platillogodin.dashboard.domain.UserRole;
import com.platillogodin.dashboard.exceptions.DeleteException;
import com.platillogodin.dashboard.exceptions.NotFoundException;
import com.platillogodin.dashboard.repositories.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Hugo Lezama on August - 2018
 */
@Slf4j
@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Value("${dashboard.default.password}")
    private String defaultPassword;

    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public User findById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("El ID de usuario " + id + " no existe"));
    }

    public void delete(User user) {
        if (user.getRole().equals(UserRole.ROLE_ADMIN)) {
            int admins = userRepository.countAllByRole(UserRole.ROLE_ADMIN);
            if (admins < 2) {
                throw new DeleteException("Debe existir al menos un usuario ADMIN");
            }
        }
        userRepository.delete(user);
    }

    public User saveUser(User user) {
        if (user.getId() == null) {
            user.setPassword(passwordEncoder.encode(defaultPassword));
        }
        return userRepository.save(user);
    }

    public void updatePassword(User user, String password) {
        user.setPassword(passwordEncoder.encode(password == null ? defaultPassword : password));
        userRepository.save(user);
    }
}
