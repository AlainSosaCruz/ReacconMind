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
        User user = modelMapper.map(userDTO, User.class);
        user.setImageFacade("https://firebasestorage.googleapis.com/v0/b/reacconmind-e99ee.appspot.com/o/cd16b824-7188-473b-b991-22d09edb98e0.jpg?alt=media&token=92b3ddb6-a9e9-4f17-b10f-9675c0f72d58");
        user.setImageProfile("https://firebasestorage.googleapis.com/v0/b/reacconmind-e99ee.appspot.com/o/8936051b-d9c1-42d4-a9f0-f522116700ee.png?alt=media&token=31a4ea56-5d4c-411f-b253-c191464b7a9e");
        user.setThumbnail("https://firebasestorage.googleapis.com/v0/b/reacconmind-e99ee.appspot.com/o/thumb_8936051b-d9c1-42d4-a9f0-f522116700ee.png?alt=media&token=f19a1d1d-2769-46c8-acd4-66582ddab82e");
        userService.saveUser(userDTO);
        return user;
    }

    @QueryMapping
    public List<User> getUserPagination(@Argument int page, @Argument int pageSize) {
        return userService.getAll(page, pageSize);
    }

}
