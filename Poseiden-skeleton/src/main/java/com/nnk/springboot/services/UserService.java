package com.nnk.springboot.services;


import com.nnk.springboot.domain.User;
import com.nnk.springboot.repositories.UserRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;


@Service
public class UserService implements UserDetailsService {

    @Autowired
    UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    private final static Logger logger = LogManager.getLogger("UserService");

    public List<User> getUserList() {
            logger.info("getUserList");
        return userRepository.findAll();
    }

    public User addUser (User user) {

            logger.info("addUser");
                PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
                String password = passwordEncoder.encode(user.getPassword());
                user.setPassword(password);
        return userRepository.save(user);
    }

    public Optional<User> findById (int id) {
            logger.info("findById");
        return userRepository.findById(id);
    }

    public User updateUser (int id, User user) {
        User userFind = userRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + id));
            logger.info("updateUser");
            BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
            userFind.setFullName(user.getFullName());
            userFind.setUserName(user.getUserName());
            userFind.setRole(user.getRole());
            userFind.setPassword(encoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    public void delete (User user) {
            logger.info("delete");
        userRepository.delete(user);
    }


    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
            logger.info("loadByUsername");
        return (UserDetails) userRepository.findByUserName(s);
    }

}
