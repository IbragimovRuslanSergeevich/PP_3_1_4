package ru.kata.spring.boot_security.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PathVariable;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.RoleService;
import ru.kata.spring.boot_security.demo.service.UserService;

import javax.validation.Valid;
import java.security.Principal;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final UserService userService;
    private final RoleService roleService;

    public AdminController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @GetMapping
    public String show(Model model, Principal principal) {
        model.addAttribute("users", userService.getUsersList());
        model.addAttribute("user", userService.findByUsername(principal.getName()));
        model.addAttribute("rolesOfUser", roleService.getRolesList());
        return "admin";
    }


    @PostMapping("/new")
    public String createUser(@ModelAttribute("user") @Valid User user) {
        userService.addUser(user);
        return "redirect:/admin";
    }

    @GetMapping("/{id}")
    public String deleteUser(@PathVariable("id") long id) {
        userService.deleteUser(id);
        return "redirect:/admin";
    }


    @PostMapping("/{id}")
    public String editUser(@ModelAttribute("user") @Valid User user) {
        userService.editUser(user);
        return "redirect:/admin";
    }

}
