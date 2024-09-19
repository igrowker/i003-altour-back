package com.igrowker.altour.api.externalDtos.User;


import com.igrowker.altour.persistence.entity.CustomUser;

import java.math.BigDecimal;

public class UserDTO {
    private Long id;

    private String username;

    private String email;

    private String password;

    private String activity;

    private BigDecimal maxDistance;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getActivity() {
        return activity;
    }

    public void setActivity(String activity) {
        this.activity = activity;
    }

    public BigDecimal getMaxDistance() {
        return maxDistance;
    }

    public void setMaxDistance(BigDecimal maxDistance) {
        this.maxDistance = maxDistance;
    }

    public CustomUser toEntity() {
        CustomUser user = new CustomUser();
        user.setId(this.id);
        user.setUsername(this.username);
        user.setEmail(this.email);
        user.setPassword(this.password);
        user.setActivity(this.activity);
        user.setMaxDistance(this.maxDistance);
        return user;
    }
}
