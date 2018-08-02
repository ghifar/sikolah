package com.ghifar.sikolah.services;

import com.ghifar.sikolah.com.ghifar.sikolah.entities.User;
import com.ghifar.sikolah.dto.UserDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface IUserService {

    public User registerNewGuruAccount(UserDto accountDto);
    public User registerNewSiswaAccount(UserDto accountDto);

    public boolean isAlreadyExist(String username);

    public boolean findUserByName(String username);

    public User findById(Long id);

    Page<User> tampilkanSemua();

    public void deleteUserById(Long id);

    public User updateUser(User user);

    public boolean checkIfOldPasswordValid(User user, String oldPassword);

    public User findByUsername(String username);

    public void updateUserPassword(User user, String password);


}
