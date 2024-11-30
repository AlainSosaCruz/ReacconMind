package com.reacconmind.reacconmind.controller;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;
import com.reacconmind.reacconmind.service.UserService;
import com.reacconmind.reacconmind.dto.UserAddDTO;
import com.reacconmind.reacconmind.model.User;

@Controller
public class UserGraphqlController {
    @Autowired
    private UserService userService;
    @Autowired
    private ModelMapper modelMapper;

    @QueryMapping
    public User getById(@Argument int id) {
        return userService.getByIdUser(id);
    }

    @MutationMapping
    public User addUser(@Argument(value = "user") UserAddDTO userDTO) {
        return userService.saveUser(userDTO);
    }

    @QueryMapping
    public List<User> getUserPagination(@Argument int page, @Argument int pageSize) {
        return userService.getAll(page, pageSize);
    }

}
