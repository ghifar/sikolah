package com.ghifar.sikolah.services;

import com.ghifar.sikolah.Exception.UserAlreadyExistException;
import com.ghifar.sikolah.com.ghifar.sikolah.entities.User;
import com.ghifar.sikolah.dto.UserDto;
import com.ghifar.sikolah.repository.RoleRepository;
import com.ghifar.sikolah.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Arrays;
import java.util.List;

@Service
@Transactional
public class UserService implements IUserService {

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public boolean isAlreadyExist(String username){
        if(userRepository.findByUsername(username) != null){
            return true;
        }
        return false;
    }

    @Override
    public boolean findUserByName(String username) {
        if(userRepository.findByUsername(username) != null){
            return true;
        }
            return false;
    }

    @Override
    public Page<User> tampilkanSemua() {

//        List<User> users = userRepository.findAll();
//        for (User user : users) {
//            System.out.println("Name : "+user.getUsername());
//        }

        Page<User> users= userRepository.findAll(new PageRequest(0,5));
        return users;
    }

    @Override
    public User registerNewGuruAccount(UserDto accountDto) {

            if (isAlreadyExist(accountDto.getUsername())) {
                System.out.println(">>>>>>> Username sudah ada");
            throw new UserAlreadyExistException("There is an account with the same username: " + accountDto.getUsername());
            }
            User user = new User();

            user.setName(accountDto.getName());
            user.setUsername(accountDto.getUsername());
            user.setPassword(passwordEncoder.encode(accountDto.getPassword()));
            user.setEnabled(true);

            user.setRoles(Arrays.asList(roleRepository.findByName("ROLE_GURU")));
            return userRepository.save(user);
    }

    @Override
    public User registerNewSiswaAccount(UserDto accountDto) {
        if (isAlreadyExist(accountDto.getUsername())) {
            System.out.println(">>>>>>> Username sudah ada");
            throw new UserAlreadyExistException("There is an account with the same username: " + accountDto.getUsername());
        }
        User user = new User();

        user.setName(accountDto.getName());
        user.setUsername(accountDto.getUsername());
        user.setPassword(passwordEncoder.encode(accountDto.getPassword()));
        user.setEnabled(true);

        user.setRoles(Arrays.asList(roleRepository.findByName("ROLE_SISWA")));
        return userRepository.save(user);
    }
}
