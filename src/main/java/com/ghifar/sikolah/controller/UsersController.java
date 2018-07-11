package com.ghifar.sikolah.controller;

import com.ghifar.sikolah.com.ghifar.sikolah.entities.User;
import com.ghifar.sikolah.configuration.PagerModel;
import com.ghifar.sikolah.repository.RoleRepository;
import com.ghifar.sikolah.repository.UserRepository;
import com.ghifar.sikolah.services.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;
import java.util.logging.Logger;

@Controller
public class UsersController {

    @Autowired
    IUserService userService;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    UserRepository userRepository;

    private static final int BUTTONS_TO_SHOW = 5;
    private static final int INITIAL_PAGE = 0;
    private static final int INITIAL_PAGE_SIZE = 5;
    private static final int[] PAGE_SIZES = { 5, 10,20};

    private Logger logger= Logger.getLogger(getClass().getName());

    @GetMapping("/users")
    public String listUsers(Model model, @RequestParam("pageSize") Optional<Integer> pageSize,
                        @RequestParam("page") Optional<Integer> page){

        // For pagination==
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
        //  For pagination END===

        return"usersList";
    }

}
