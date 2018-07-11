package com.ghifar.sikolah.controller;

import com.ghifar.sikolah.com.ghifar.sikolah.entities.User;
import com.ghifar.sikolah.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;

@Controller
public class LoginController {

//    @Autowired
//    UserService userService;

//    @GetMapping("/login")
//    public String showLoginForm(Model model){
//        model.addAttribute("user", new User());
//        return"/";
//    }
//
//    @PostMapping("/login")
//    public String processLogin(@Valid User user, BindingResult bindingResult, Model model){
//        if (bindingResult.hasErrors()) {
//            System.out.println("inputan error field mu jing");
//            System.out.println(bindingResult.getFieldError());
//            return "loginPage";
//        }
//        if(!userService.isAlreadyExist(user.getUsername()) ){
//            model.addAttribute("exist", true);
//            return"loginPage";
//        }
//
//        System.out.println("Login!!!");
//        return "index";
//    }
}
