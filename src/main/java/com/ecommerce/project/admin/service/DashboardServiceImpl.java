package com.ecommerce.project.admin.service;

import com.ecommerce.project.security.model.User;
import com.ecommerce.project.security.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class DashboardServiceImpl implements DashboardService {

    @Autowired
    UserRepository userRepository;

    @Override
    public List<User> getUsers() { return userRepository.findAll(); }

    @Override
    public User getUser(String username) throws UsernameNotFoundException
    {
        return Optional.ofNullable(userRepository.findByUsername(username))
                .orElseThrow(() -> new UsernameNotFoundException("User with this username doesn't exist"));
    }

}