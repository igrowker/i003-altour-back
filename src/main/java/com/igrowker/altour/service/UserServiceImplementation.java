package com.igrowker.altour.service;

import com.igrowker.altour.persistence.entity.CustomUser;
import com.igrowker.altour.persistence.repository.ICustomUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImplementation implements IUserService {

    @Autowired
    private ICustomUserRepository userRepository;
    @Override
    public CustomUser getUser(String email) {
        CustomUser user = userRepository.findByEmail(email);
        return user;
    }

    @Override
    public CustomUser saveUser(CustomUser user) {

        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String encodedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);

        return userRepository.save(user);
    }

    @Override
    public CustomUser updateUser(CustomUser user) {
        return userRepository.save(user);
    }

    @Override
    public void deleteUser(String email) {
        userRepository.delete(userRepository.findByEmail(email));
    }

    @Override
    public boolean checkCredentials(String email, String password) {
        CustomUser user = userRepository.findByEmail(email);
        if (user == null) {
            return false;
        }

        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        return passwordEncoder.matches(password, user.getPassword());
    }
}
