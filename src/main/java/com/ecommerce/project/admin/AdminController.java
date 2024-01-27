package com.ecommerce.project.admin;

import com.ecommerce.project.security.model.User;
import com.ecommerce.project.security.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    UserRepository userRepository;

    @GetMapping("/users")
    @ResponseBody()
    protected List<User> getUsers() {
        List<User> userList = userRepository.findAll();
        return userList;
    }

    @GetMapping("/user/{username}")
    @ResponseBody()
    protected User getUser(@PathVariable String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);
        if (user != null) {
            return user;
        } else {
            throw new UsernameNotFoundException("User with this username doesn't exist");
        }
    }

}
