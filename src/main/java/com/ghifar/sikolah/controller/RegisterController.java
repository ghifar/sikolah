package com.ghifar.sikolah.controller;

import com.ghifar.sikolah.com.ghifar.sikolah.entities.User;
import com.ghifar.sikolah.dto.UserDto;
import com.ghifar.sikolah.services.IUserService;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;

@Controller
public class RegisterController {

    @Autowired
    private IUserService userService;

    private final Logger LOGGER = LoggerFactory.getLogger(getClass());


    @GetMapping("/register")
    public String registerForm(Model model){
        model.addAttribute("user", new User());
        return"views/registerForm";
    }

    @PostMapping("/register")
    public String register(@Valid @ModelAttribute("user")final UserDto accountDto, BindingResult bindingResult, Model model){

        LOGGER.info("Registering user account with information: {}", accountDto);
        if (bindingResult.hasErrors()) {
            System.out.println(">>>>>> inputan error field mu");
            return "views/registerForm";
        } else{
            userService.registerNewSiswaAccount(accountDto);
        }
//        if(userService.isAlreadyExist(user.getUsername())){
//            model.addAttribute("exist", true);
//            return"views/registerForm";
//        }
//        return "loginPage";
        return "redirect:/";
    }
}
