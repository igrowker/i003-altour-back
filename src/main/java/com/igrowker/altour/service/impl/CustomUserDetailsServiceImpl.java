package com.igrowker.altour.service.impl;

import com.igrowker.altour.persistence.entity.CustomUser;
import com.igrowker.altour.persistence.repository.ICustomUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private ICustomUserRepository userRepository;


    public void setMaxDistance(String username, Integer maxDistance) {
        CustomUser user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));
        user.setMaxSearchDistance(maxDistance);
        userRepository.save(user);
    }

    public void setCrowdLevel(String username, Integer crowdLevel) {
        CustomUser user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));
        user.setPreferredCrowdLevel(crowdLevel);
        userRepository.save(user);
    }
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByEmail(username).orElseThrow(() -> new UsernameNotFoundException("No se encontro el usuario " + username));

    }
}