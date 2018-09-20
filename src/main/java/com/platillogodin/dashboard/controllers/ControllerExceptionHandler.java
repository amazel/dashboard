package com.platillogodin.dashboard.controllers;

import com.platillogodin.dashboard.exceptions.NotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

@Slf4j
@ControllerAdvice
public class ControllerExceptionHandler {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({NumberFormatException.class, Exception.class})
    public ModelAndView handleNumberFormatException(Exception e) {
        log.error("Handling NUMBER FORMAT EXCEPTION");
        log.error(e.toString());
        ModelAndView mav = new ModelAndView();
        mav.addObject("exception", e);
        mav.setViewName("error");
        return mav;
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(NotFoundException.class)
    public ModelAndView handleNotFound(Exception exception) {

        log.error("Handling not found exception");
        log.error(exception.toString());

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("error");
        modelAndView.addObject("exception", exception);

        return modelAndView;
    }

}
