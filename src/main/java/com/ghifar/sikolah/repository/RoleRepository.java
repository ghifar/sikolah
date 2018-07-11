package com.ghifar.sikolah.repository;

import com.ghifar.sikolah.com.ghifar.sikolah.entities.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {

//    @Query("select r.name from Role r where r")
//    List<Role> getAllRolesName(String name);

    Role findByName(String name);

    List<Role> countByName(String name);


}
