package com.platillogodin.dashboard.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.security.Principal;

/**
 * Created by Hugo Lezama on August - 2018
 */
@Slf4j
@ControllerAdvice
public class AuthAdvice {
    @ModelAttribute("userName")
    public String populateUserName(Principal principal) {
        log.info("populatingUserName");
        if (principal != null && principal.getName() != null) {
            return principal.getName();
        }
        return "";
    }
}
