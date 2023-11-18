package ru.kata.spring.boot_security.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
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
//
//    @GetMapping(params = "id")
//    public String showIdUser(@RequestParam(value = "id") long id, Model model) {
//        model.addAttribute("user", userService.getUser(id));
//        return "id_user";
//    }
//
//    @GetMapping("/new")
//    public String newUser(Model model, @ModelAttribute("user") User user) {
//        model.addAttribute("rolesOfUser", roleService.getRolesList());
//        return "new";
//    }

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

//    @GetMapping("/edit")
//    public String editUserForm(Model model, @RequestParam("id") long id) {
//        model.addAttribute("user", userService.getUser(id));
//        model.addAttribute("rolesOfUser", roleService.getRolesList());
//        return "redirect:/admin";
//    }

    @PostMapping("/{id}")
    public String editUser(@ModelAttribute("user") @Valid User user) {
        userService.editUser(user);
        return "redirect:/admin";
    }

}
