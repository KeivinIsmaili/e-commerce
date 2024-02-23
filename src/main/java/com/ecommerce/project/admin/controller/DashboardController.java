package com.ecommerce.project.admin.controller;

import com.ecommerce.project.admin.service.DashboardServiceImpl;
import com.ecommerce.project.security.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@RestController
@RequestMapping("/dashboard")
public class DashboardController {

    @Autowired
    DashboardServiceImpl dashboardService;

    @GetMapping("/users")
    @ResponseBody()
    public List<User> getUsers()
    {
        return dashboardService.getUsers();
    }

    @GetMapping("/user/{username}")
    @ResponseBody()
    public User getUser(@PathVariable String username)
    {
        return dashboardService.getUser(username);
    }

}
