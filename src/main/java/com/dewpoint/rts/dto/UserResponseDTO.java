package com.dewpoint.rts.dto;

import java.util.List;

public class UserResponseDTO {

    private List<UserDTO> users;

    public List<UserDTO> getUsers() {
        return users;
    }

    public void setUsers(List<UserDTO> users) {
        this.users = users;
    }

    @Override
    public String toString() {
        return "UserResponseDTO{" +
                "users=" + users +
                '}';
    }
}
