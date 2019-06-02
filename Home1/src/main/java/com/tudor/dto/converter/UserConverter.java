package com.tudor.dto.converter;

import com.tudor.dto.UserDTO;
import com.tudor.model.Person;
import com.tudor.model.User;
import com.tudor.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class UserConverter {
    @Autowired
    private UserService userService;

    public UserConverter() {
    }

    public User convertFromUserDTO(UserDTO userDto) {
        return new User(userDto.getName(), null);
    }

    public UserDTO convertToUserDTO(User user) {
        Optional<Person> details = userService.getUserDetails(user);
        if(details.isPresent()) {
            return new UserDTO(user.getName(), details.get());
        }

        return new UserDTO(user.getName());
    }
}