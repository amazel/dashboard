package com.platillogodin.dashboard.controllers;

import com.platillogodin.dashboard.domain.User;
import com.platillogodin.dashboard.exceptions.DeleteException;
import com.platillogodin.dashboard.services.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class UserManagementControllerTest {

    @Mock
    UserService userService;

    UserManagementController userManagementController;
    MockMvc mockMvc;


    private static final String FORM_URL = "users/user_form";
    private static final String LIST_URL = "users/list";
    private static final String BASE_URL = "/users";

    @BeforeEach
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        userManagementController = new UserManagementController(userService);
        mockMvc = MockMvcBuilders.standaloneSetup(userManagementController)
                .setControllerAdvice(new ControllerExceptionHandler()).build();
    }

    @Test
    void manageProfile() {
    }

    @Test
    void listUsers() throws Exception {
        when(userService.findAll()).thenReturn(Arrays.asList(new User()));
        mockMvc.perform(get(BASE_URL))
                .andExpect(status().isOk())
                .andExpect(view().name(LIST_URL))
                .andExpect(model().attributeExists("userList"));
    }

    @Test
    void newUser() throws Exception {
        mockMvc.perform(get(BASE_URL + "/new"))
                .andExpect(status().isOk())
                .andExpect(view().name(FORM_URL))
                .andExpect(model().attributeExists("user"));
    }


    @Test
    void updateUser() throws Exception {
        when(userService.findById(anyLong())).thenReturn(new User());
        mockMvc.perform(get(BASE_URL + "/1/edit"))
                .andExpect(status().isOk())
                .andExpect(view().name(FORM_URL))
                .andExpect(model().attributeExists("user"));
    }

    @Test
    void deleteUser() throws Exception {
        when(userService.findById(anyLong())).thenReturn(new User());
        mockMvc.perform(get(BASE_URL + "/1/delete"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/users"))
                .andExpect(flash().attributeExists("deleteMessage"));
    }

    @Test
    public void deleteUserError() throws Exception {
        when(userService.findById(anyLong())).thenReturn(new User());
        doThrow(new DeleteException("")).when(userService).delete(any());
        mockMvc.perform(get(BASE_URL + "/1/delete"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/users"))
                .andExpect(flash().attributeExists("deleteError"));
    }

    @Test
    void saveOrUpdateUser() throws Exception {
        User saved = new User();
        saved.setId(200L);
        when(userService.saveUser(any())).thenReturn(saved);

        mockMvc.perform(post("/users")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
        )
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/users"));
    }

    @Test
    void updateUserProfile() throws Exception {
        User saved = new User();
        saved.setUsername("username");
        when(userService.findById(any())).thenReturn(saved);
        mockMvc.perform(post("/users/profile")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
        )
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/users/profile/username"))
                .andExpect(flash().attributeExists("message"));
    }

    @Test
    void updateUserProfileError() throws Exception {
        User saved = new User();
        saved.setUsername("username");
        when(userService.findById(any())).thenReturn(saved);
        doThrow(new DeleteException("")).when(userService).updatePassword(any(),any());
        mockMvc.perform(post("/users/profile")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
        )
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/users/profile/username"))
                .andExpect(flash().attributeExists("errorMessage"));
    }
}