package com.ghifar.sikolah.controller;

import com.ghifar.sikolah.com.ghifar.sikolah.entities.Role;
import com.ghifar.sikolah.com.ghifar.sikolah.entities.User;
import com.ghifar.sikolah.dto.UserDto;
import com.ghifar.sikolah.repository.RoleRepository;
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

    @Autowired
    private RoleRepository roleRepository;

    private final Logger LOGGER = LoggerFactory.getLogger(getClass());


//    @ModelAttribute("userku")
//    public User user() {
//        return new User();
//    }
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
//admin
    @GetMapping("/admin/register")
    public String registerFromAdmin( Model model){
        model.addAttribute("user", new User());
        model.addAttribute("rolesku", roleRepository.findAll().subList(1,3));
//        sublist for only show role_guru and role_siswa. we wont see role_admin which is located at index 0

        return "admin/adminRegisterForm";
    }

    @PostMapping("/admin/register")
    public String processRegister(@Valid @ModelAttribute("user")final UserDto accountDto, BindingResult bindingResult,Model model){
        model.addAttribute("rolesku", roleRepository.findAll().subList(1,3));
//        these 1 lines above needed again, so we can make select option appear again when errors occurred
        LOGGER.info(">> executing process register");
        System.out.println(">> " +
                "ROLE YANG DIPILIH : "+accountDto.getRoles().iterator().next().getName().isEmpty());

        if(bindingResult.hasErrors()) {
            System.out.println(">>>>>> inputan error field mu");
           return "admin/adminRegisterForm";

        }else if(userService.isAlreadyExist(accountDto.getUsername())) {
            model.addAttribute("exist", true);
            return"admin/adminRegisterForm";


        }else{
                if (accountDto.getRoles().iterator().next().getName().equalsIgnoreCase("ROLE_SISWA")) {
                    LOGGER.info(">> SAVING ROLE SISWA WITH = " + accountDto);
                    userService.registerNewSiswaAccount(accountDto);
                } else if (accountDto.getRoles().iterator().next().getName().equalsIgnoreCase("ROLE_GURU")) {
                    LOGGER.info(">> SAVING ROLE GURU WITH = " + accountDto);
                    userService.registerNewGuruAccount(accountDto);
                }
                model.addAttribute("success", true);

            }
        return "admin/adminRegisterForm";

    }


}
