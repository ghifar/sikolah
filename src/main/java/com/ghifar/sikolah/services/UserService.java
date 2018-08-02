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
    public User findById(Long id) {
        return userRepository.findById(id);
    }

    @Override
    public Page<User> tampilkanSemua() {

        Page<User> users= userRepository.findAll(new PageRequest(0,5));
        return users;
    }

    @Override
    public void deleteUserById(Long id){
//        userRepository.delete(id);
        userRepository.delete(id);

    }

    @Override
    public User updateUser(User user){
        user.setName(user.getName());
        user.setUsername(user.getUsername());
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setEnabled(true);
        return userRepository.save(user);
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

    @Override
    public boolean checkIfOldPasswordValid(User user, String oldPassword){

        return passwordEncoder.matches(oldPassword, user.getPassword());

    }

    @Override
    public void updateUserPassword(User user, String password){
        user.setPassword(passwordEncoder.encode(password));
        userRepository.save(user);
    }

    @Override
    public User findByUsername(String username){
        return userRepository.findByUsername(username);
    }
}
