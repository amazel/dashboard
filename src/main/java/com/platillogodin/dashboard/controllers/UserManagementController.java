package com.platillogodin.dashboard.controllers;

import com.platillogodin.dashboard.commands.UserProfile;
import com.platillogodin.dashboard.domain.User;
import com.platillogodin.dashboard.exceptions.DeleteException;
import com.platillogodin.dashboard.services.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.util.List;

/**
 * Created by Hugo Lezama on August - 2018
 */
@Slf4j
@Controller
public class UserManagementController {

    private static final String FORM_URL = "users/user_form";
    private static final String LIST_URL = "users/list";

    private final UserService userService;

    public UserManagementController(UserService userService) {
        this.userService = userService;
    }


    @GetMapping("/users/profile/{userName}")
    public String manageProfile(@PathVariable("userName") String userName, Model model, Principal principal) {
        if (principal.getName().equals(userName)) {
            User user = userService.findByUsername(userName);
            UserProfile userProfile = new UserProfile();
            userProfile.setId(user.getId());
            userProfile.setUsername(user.getUsername());
            model.addAttribute("userProfile", userProfile);
            return "users/profile";
        }
        return "403";
    }

    @GetMapping("/users")
    public String listUsers(Model model) {
        List<User> userList = userService.findAll();
        model.addAttribute("userList", userList);
        return LIST_URL;
    }

    @GetMapping("/users/new")
    public String newUser(Model model) {
        model.addAttribute("user", new User());
        return FORM_URL;
    }

    @GetMapping("/users/{id}/edit")
    public String updateUser(@PathVariable Long id, Model model) {
        User user = userService.findById(id);
        model.addAttribute("user", user);
        return FORM_URL;
    }

    @GetMapping("/users/{id}/delete")
    public String deleteUser(@PathVariable Long id, Model model, RedirectAttributes ra, HttpServletRequest req) {
        log.info("Deleting user {}, user= {}", id, req.getSession(false).getAttribute("user"));
        User user = userService.findById(id);
        log.info("Username= {}", user.getUsername());
        try {
            userService.delete(user);
            ra.addFlashAttribute("message", "El usuario " + user.getUsername() + " fue eliminado correctamente");
        } catch (DeleteException de) {
            ra.addFlashAttribute("errorMessage", de.getMessage());
        }
        return "redirect:/users";
    }

    @GetMapping("/users/{id}/reset-password")
    public String resetPassword(@PathVariable Long id, Model model, RedirectAttributes redirectAttributes, HttpServletRequest req) {
        log.info("Reset password for user {}, user= {}", id, req.getSession(false).getAttribute("user"));
        User user = userService.findById(id);
        log.info("Username= {}", user.getUsername());
        userService.updatePassword(user, null);
        redirectAttributes.addFlashAttribute("message", "Contraseña restablecida con éxito");

        return "redirect:/users";
    }

    @PostMapping("/users")
    public String saveOrUpdateUser(@ModelAttribute("user") User user, HttpServletRequest req) {
        log.info("Creating new user, user= {}", req.getSession(false).getAttribute("user"));
        log.info("New User= {}", user);
        userService.saveUser(user);
        return "redirect:/users";
    }

    @PostMapping("/users/profile")
    public String updateUserProfile(@ModelAttribute("userProfile") UserProfile userProfile, RedirectAttributes redirectAttributes) {
        log.info("Updating userProfile (password)");
        User user = userService.findById(userProfile.getId());
        log.info("user {}", user);
        try {
            userService.updatePassword(user, userProfile.getPassword());
            redirectAttributes.addFlashAttribute("message", "Contraseña actualizada con éxito");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Error al actualizar la contraseña");
        }

        return "redirect:/users/profile/" + user.getUsername();
    }

}
