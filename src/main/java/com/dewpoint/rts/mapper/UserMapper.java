package com.dewpoint.rts.mapper;

import com.dewpoint.rts.dto.UserDTO;
import com.dewpoint.rts.dto.UserRequestDTO;
import com.dewpoint.rts.model.User;
import com.dewpoint.rts.util.ApiConstants;
import org.springframework.stereotype.Component;

import java.security.Principal;
import java.util.Date;

@Component
public class UserMapper {

    public User formatCreateEntry(UserRequestDTO userDTO, Principal principal) {
        User user = new User();
        user.setFullName(userDTO.getUserFullName());
        user.setRole(userDTO.getRole());
        user.setPassword(ApiConstants.DEFAULT_INITIAL_PASSWORD);
        user.setUserId(userDTO.getUserId());
        user.setStatus(ApiConstants.USER_STATUS_ACTIVE);
        user.setCreatedBy(principal.getName());
        user.setCreatedOn(new Date());
        return user;
    }

    public User formatSearchEntry(UserRequestDTO userDTO) {
        User user = new User();
        user.setUserId(userDTO.getUserId());
        return user;
    }

    public User formatUpdateEntry(User user, Principal principal) {
        user.setModifiedBy(principal.getName());
        user.setModifiedOn(new Date());
        return user;
    }

    public User formatDeleteEntry(User user, Principal principal) {
        user.setStatus(ApiConstants.USER_STATUS_INACTIVE);
        user.setModifiedBy(principal.getName());
        user.setModifiedOn(new Date());
        return user;
    }

    public UserDTO convertEntityToDTO(User user) {
        UserDTO userDTO = new UserDTO();
        userDTO.setUserFullName(user.getFullName());
        userDTO.setRole(user.getRole());
        userDTO.setStatus(user.getStatus());
        userDTO.setUserId(user.getUserId());
        userDTO.setCreatedBy(user.getCreatedBy());
        userDTO.setCreatedOn(user.getCreatedOn());
        userDTO.setLastModifiedBy(user.getModifiedBy());
        userDTO.setLastModifiedOn(user.getModifiedOn());
        return userDTO;
    }
}
