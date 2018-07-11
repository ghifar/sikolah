package com.ghifar.sikolah.repository;


import com.ghifar.sikolah.com.ghifar.sikolah.entities.User;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {


    User findByUsername(String username);

    List<User> findAllByUsernameContains(String username);

    //    this is the actual query i tested from mysql
//    SELECT u.name FROM USER u INNER JOIN role r INNER JOIN user_roles ur WHERE r.name=“ROLE_SISWA” AND ur.user_id=u.id AND ur.role_id=r.id
    @Query("select u.name from user u join u.roles ur where ur.name=:name")
    List<User> findUserByRolesName(@Param("name") String name);




}
