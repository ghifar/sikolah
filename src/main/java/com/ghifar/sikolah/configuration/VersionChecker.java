package com.ghifar.sikolah.configuration;


import org.springframework.core.SpringVersion;
import org.springframework.security.core.SpringSecurityCoreVersion;

public class VersionChecker {


    public static void main(String[] args) {
        System.out.println("SPring Version : "+SpringVersion.getVersion());
        System.out.println("Spring Sec Version : "+SpringSecurityCoreVersion.getVersion());


//        System.out.println(userRepository.findByUsername("test"));




    }
}
