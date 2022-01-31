package com.nnk.springboot.services;


import com.nnk.springboot.domain.User;
import com.nnk.springboot.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

    public List<User> getUserList() {
        return userRepository.findAll();
    }

    public User addUser (User user) {
        return userRepository.save(user);
    }

    public Optional<User> findById (int id) {
        return userRepository.findById(id);
    }

    public User updateUser (int id, User user) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        user.setPassword(encoder.encode(user.getPassword()));
        user.setId(id);
        return userRepository.save(user);
    }

    public void delete (User user) {
        userRepository.delete(user);
    }



}
