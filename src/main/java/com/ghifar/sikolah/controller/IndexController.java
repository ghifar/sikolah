package com.ghifar.sikolah.controller;

import com.ghifar.sikolah.com.ghifar.sikolah.entities.Role;
import com.ghifar.sikolah.com.ghifar.sikolah.entities.User;
import com.ghifar.sikolah.configuration.PagerModel;
import com.ghifar.sikolah.dto.PasswordDTO;
import com.ghifar.sikolah.dto.UserDto;
import com.ghifar.sikolah.repository.RoleRepository;
import com.ghifar.sikolah.repository.UserRepository;
import com.ghifar.sikolah.services.IUserService;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

@Controller
public class IndexController {

    @Autowired
    IUserService userService;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    private static final int BUTTONS_TO_SHOW = 5;
    private static final int INITIAL_PAGE = 0;
    private static final int INITIAL_PAGE_SIZE = 5;
    private static final int[] PAGE_SIZES = { 5, 10,20};


//    private Logger logger= Logger.getLogger(getClass().getName());
    private final org.slf4j.Logger logger = LoggerFactory.getLogger(getClass());

    @GetMapping("/")
    public String index(Model model, @RequestParam("pageSize") Optional<Integer> pageSize,
                        @RequestParam("page") Optional<Integer> page){

        model.addAttribute("jumlahAdmin", userRepository.findUserByRolesName("ROLE_ADMIN"));
        model.addAttribute("jumlahGuru", userRepository.findUserByRolesName("ROLE_GURU"));
        model.addAttribute("jumlahSiswa", userRepository.findUserByRolesName("ROLE_SISWA"));

//        For pagination==
        // Evaluate page size. If requested parameter is null, return initial
        // page size
        int evalPageSize = pageSize.orElse(INITIAL_PAGE_SIZE);
        // Evaluate page. If requested parameter is null or less than 0 (to
        // prevent exception), return initial size. Otherwise, return value of
        // param. decreased by 1.
        int evalPage = (page.orElse(0) < 1) ? INITIAL_PAGE : page.get() - 1;

        logger.info(">> executing client repo findAll()"+ userRepository.findAll());
        Page<User> userList= userRepository.findAll(new PageRequest(evalPage, evalPageSize));
        logger.info(">> User list get total pages: "+userList.getTotalPages()+"User list get number: "+userList.getNumber());
        PagerModel pager= new PagerModel(userList.getTotalPages(),userList.getNumber(),BUTTONS_TO_SHOW);

        model.addAttribute("users", userList);
        model.addAttribute("selectedPageSize", evalPageSize);
        // add page sizes
        model.addAttribute("pageSizes", PAGE_SIZES);
        // add pager
        model.addAttribute("pager", pager);
//        For pagination END===

        return"index";
    }


    @GetMapping("/user/delete/{id}")
    public String deleteUser(@PathVariable Long id){
        System.out.println(">> deleting");
        userService.deleteUserById(id);
        return "redirect:/";
    }

    @GetMapping("/user/edit/{id}")
    public String editUser(@PathVariable Long id, Model model){
        System.out.println(">> editting");
//        userService.deleteUserById(id);
        model.addAttribute("user", userService.findById(id));
        return "views/editForm";
    }

    @PostMapping("/updateUser")
    public String updateUser(User user){
        System.out.println(">> Process Update");
        logger.info("Registering user account with information: {}", user);
        userRepository.setUserInfoById(user.getName(), user.getUsername(), user.getId());
        return "redirect:/";
    }

    @GetMapping("/changePassword")
    public String showChangePasswordForm(PasswordDTO passwordDTO, Model model){

        model.addAttribute("user",passwordDTO);
        return "views/changePassword";
    }

    @PostMapping("/changePassword")
    public String processChangePassword(@Valid @ModelAttribute ("user")PasswordDTO passwordDTO, BindingResult bindingResult, @RequestParam(value = "submitParam") String submitParam, Model model){
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user= userService.findByUsername(((UserDetails)principal).getUsername());

//        if (principal instanceof UserDetails) {
//            String username = ((UserDetails)principal).getUsername();
//            System.out.println("OIOIOIOI "+ username);
//        } else {
//            String username = principal.toString();
//            System.out.println("UIIUIUIU "+ username);
//        }

        if(submitParam.equalsIgnoreCase("submit")) {
            if ((userService.checkIfOldPasswordValid(user, passwordDTO.getOldPassword())) && (passwordDTO.getNewPassword().matches(passwordDTO.getMatchPassword()))  && !bindingResult.hasErrors()) {
                System.out.println("Update new password");
                userService.updateUserPassword(user, passwordDTO.getNewPassword());
            } else if (!userService.checkIfOldPasswordValid(user, passwordDTO.getOldPassword())) {
                model.addAttribute("wrongOldPassword", true);
                logger.info("Password lama salah");
            } else if(bindingResult.hasErrors()){
                logger.info("Wrong input!");

            }
            else {
                System.out.println("password tidak sama");
                model.addAttribute("matchingPassword", true);
            }
        }else if(submitParam.equalsIgnoreCase("cancel")){
            return "redirect:/";

        }
        return "views/changePassword";

    }


    @GetMapping("/login")
    public String loginForm(){
        logger.info(">> Processing Loggin");
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        if(!(auth instanceof AnonymousAuthenticationToken)){
            return "redirect:/";
//            return "forward:/";
        }
        else
            return "loginPage";
    }

    @GetMapping("/403")
    public String error(Model model){
        return"views/403";
    }


}
