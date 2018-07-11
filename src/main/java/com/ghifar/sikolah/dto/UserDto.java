package com.ghifar.sikolah.dto;


import com.ghifar.sikolah.com.ghifar.sikolah.entities.User;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class UserDto extends User{

    @NotNull
    @Size(min = 3, message = "Size.userDto.username")
    private String username;

    @Size(min = 3, message = "Size.userDto.password")
    private String password;

    @Size(min = 3, message = "Size.userDto.name")
    private String name;



    private int role;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getRole() {
        return role;
    }

    public void setRole(int role) {
        this.role = role;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append("UserDto [firstName=").append(username).append(", password=").append(password).append(", matchingPassword=").append(", role=").append(role).append("]");
        return builder.toString();
    }
}
