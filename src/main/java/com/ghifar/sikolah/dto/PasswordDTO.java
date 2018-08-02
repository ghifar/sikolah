package com.ghifar.sikolah.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class PasswordDTO {


    private String oldPassword;

    @NotNull
    @Size(min=3,message = "size minimal 3")
    private String newPassword;

    private String matchPassword;

    public String getMatchPassword() {
        return matchPassword;
    }

    public void setMatchPassword(String matchPassword) {
        this.matchPassword = matchPassword;
    }


    public String getOldPassword() {
        return oldPassword;
    }

    public void setOldPassword(String oldPassword) {
        this.oldPassword = oldPassword;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }
}
