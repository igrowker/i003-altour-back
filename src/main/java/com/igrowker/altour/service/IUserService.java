package com.igrowker.altour.service;

import com.igrowker.altour.persistence.entity.CustomUser;

public interface IUserService {
    CustomUser getUser(String email);
    CustomUser saveUser(CustomUser user);
    CustomUser updateUser(CustomUser user);
    void deleteUser(String email);
    boolean checkCredentials(String email, String password);
}
