package com.ghifar.sikolah.configuration;

import com.ghifar.sikolah.com.ghifar.sikolah.entities.Privilege;
import com.ghifar.sikolah.com.ghifar.sikolah.entities.Role;
import com.ghifar.sikolah.com.ghifar.sikolah.entities.User;
import com.ghifar.sikolah.repository.PrivilegeRepository;
import com.ghifar.sikolah.repository.RoleRepository;
import com.ghifar.sikolah.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

@Component
public class SetupDataLoader implements ApplicationListener<ContextRefreshedEvent> {

    private boolean alreadySetup = false;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PrivilegeRepository privilegeRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        if (alreadySetup) {
            return;
        }

        // == create initial privileges
        final Privilege readPrivilege = createPrivilegeIfNotFound("READ_PRIVILEGE");
        final Privilege writePrivilege = createPrivilegeIfNotFound("WRITE_PRIVILEGE");
        final Privilege passwordPrivilege = createPrivilegeIfNotFound("CHANGE_PASSWORD_PRIVILEGE");

        // == create initial roles
        final List<Privilege> adminPrivileges = new ArrayList<Privilege>(Arrays.asList(readPrivilege, writePrivilege, passwordPrivilege));
        final List<Privilege> guruPrivileges = new ArrayList<Privilege>(Arrays.asList(readPrivilege, passwordPrivilege));
        final List<Privilege> siswaPrivileges = new ArrayList<Privilege>(Arrays.asList(readPrivilege, passwordPrivilege));
        createRoleIfNotFound("ROLE_ADMIN", adminPrivileges);
        createRoleIfNotFound("ROLE_GURU", guruPrivileges);
        createRoleIfNotFound("ROLE_SISWA", siswaPrivileges);

        // == create initial user
             Role adminRole = roleRepository.findByName("ROLE_ADMIN");
             Role guruRole = roleRepository.findByName("ROLE_GURU");
             Role siswaRole = roleRepository.findByName("ROLE_SISWA");

        createUserIfNotFound("admin", "admin", "admin", new ArrayList<Role>(Arrays.asList(adminRole)));
        createUserIfNotFound("guru", "guru", "guru", new ArrayList<Role>(Arrays.asList(guruRole)));
        createUserIfNotFound("guru1", "guru1", "guru1", new ArrayList<Role>(Arrays.asList(guruRole)));
        createUserIfNotFound("siswa", "siswa", "siswa", new ArrayList<Role>(Arrays.asList(siswaRole)));
        createUserIfNotFound("siswa1", "siswa1", "siswa1", new ArrayList<Role>(Arrays.asList(siswaRole)));
        createUserIfNotFound("siswa2", "siswa2", "siswa2", new ArrayList<Role>(Arrays.asList(siswaRole)));
        createUserIfNotFound("siswa3", "siswa3", "siswa3", new ArrayList<Role>(Arrays.asList(siswaRole)));
        createUserIfNotFound("siswa4", "siswa4", "siswa1", new ArrayList<Role>(Arrays.asList(siswaRole)));
        createUserIfNotFound("siswa5", "siswa5", "siswa2", new ArrayList<Role>(Arrays.asList(siswaRole)));
        createUserIfNotFound("siswa6", "siswa6", "siswa3", new ArrayList<Role>(Arrays.asList(siswaRole)));
        createUserIfNotFound("siswa7", "siswa7", "siswa1", new ArrayList<Role>(Arrays.asList(siswaRole)));
        createUserIfNotFound("siswa8", "siswa8", "siswa2", new ArrayList<Role>(Arrays.asList(siswaRole)));
        createUserIfNotFound("siswa9", "siswa9", "siswa3", new ArrayList<Role>(Arrays.asList(siswaRole)));
        createUserIfNotFound("siswa10", "siswa10", "siswa1", new ArrayList<Role>(Arrays.asList(siswaRole)));
        createUserIfNotFound("siswa11", "siswa11", "siswa2", new ArrayList<Role>(Arrays.asList(siswaRole)));
        createUserIfNotFound("siswa12", "siswa12", "siswa3", new ArrayList<Role>(Arrays.asList(siswaRole)));

        alreadySetup = true;

    }

    @Transactional
    private final Privilege createPrivilegeIfNotFound(final String name) {
        Privilege privilege = privilegeRepository.findByName(name);
        if (privilege == null) {
            privilege = new Privilege(name);
            privilege = privilegeRepository.save(privilege);
        }
        return privilege;
    }

    @Transactional
    private final Role createRoleIfNotFound(final String name, final Collection<Privilege> privileges) {
        Role role = roleRepository.findByName(name);
        if (role == null) {
            role = new Role(name);
        }
        role.setPrivileges(privileges);
        role = roleRepository.save(role);
        return role;
    }

    @Transactional
    private final User createUserIfNotFound(final String username, final String name, final String password, final List<Role> roles) {
        User user = userRepository.findByUsername(username);
        if (user == null) {
            user = new User();
            user.setPassword(passwordEncoder.encode(password));
            user.setUsername(username);
            user.setName(name);
            user.setEnabled(true);
        }
        user.setRoles(roles);
        user = userRepository.save(user);
        return user;
    }
}
