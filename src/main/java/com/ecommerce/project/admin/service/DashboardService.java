package com.ecommerce.project.admin.service;

import com.ecommerce.project.security.model.User;
import java.util.List;

public interface DashboardService {

     List<User> getUsers();

     User getUser(String username);

}