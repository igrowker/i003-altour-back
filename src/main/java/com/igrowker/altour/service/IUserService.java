package com.igrowker.altour.service;

import com.igrowker.altour.dtos.internal.User.LoginUserDTO;
import com.igrowker.altour.dtos.internal.User.RegistserUserDT0;
import com.igrowker.altour.dtos.internal.User.UserDTO;
import com.igrowker.altour.persistence.entity.CustomUser;

public interface IUserService {
    CustomUser getUser(String email);

    CustomUser updateUser(UserDTO user);
    String deleteUser(String email);
    boolean checkCredentials(String email, String password);


    String login (LoginUserDTO loginUserDTO);

    void validateNewEmail(String email);

    String register (RegistserUserDT0 user);
}
