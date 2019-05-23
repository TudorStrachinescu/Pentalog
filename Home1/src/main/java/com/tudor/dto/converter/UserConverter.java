package com.tudor.dto.converter;

import com.tudor.dto.UserDTO;
import com.tudor.model.User;
import org.springframework.stereotype.Component;

@Component
public class UserConverter {

    public UserConverter() {
    }

    public User convertFromUserDTO(UserDTO userDto) {
        return new User(userDto.getName(), null);
    }

    public UserDTO convertToUserDTO(User user) {
        return new UserDTO(user.getName());
    }
}