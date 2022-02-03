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

import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    UserRepository userRepository;

    private final static Logger logger = LogManager.getLogger("UserService");

    public List<User> getUserList() {
        try {
            logger.info("getUserList");
        } catch (Exception ex) {
            logger.error("getUserList error");
        }
        return userRepository.findAll();
    }

    public User addUser (User user) {
        try {
            logger.info("addUser");
                PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
                String password = passwordEncoder.encode(user.getPassword());
                user.setPassword(password);
        } catch (Exception ex) {
            logger.error("addUser error");
        }
        return userRepository.save(user);
    }

    public Optional<User> findById (int id) {
        try {
            logger.info("findById");
        } catch (Exception ex) {
            logger.error("findById error");
        }
        return userRepository.findById(id);
    }

    public User updateUser (int id, User user) {
        User userFind = userRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + id));
        try {
            logger.info("updateUser");
            BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
            userFind.setFullName(user.getFullName());
            userFind.setUserName(user.getUserName());
            userFind.setRole(user.getRole());
            userFind.setPassword(encoder.encode(user.getPassword()));
        } catch (Exception ex) {
            logger.error("updateUser error");
        }
        return userRepository.save(user);
    }

    public void delete (User user) {
        try {
            logger.info("delete");
        } catch (Exception ex) {
            logger.error("delete error");
        }
        userRepository.delete(user);
    }


    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        try {
            logger.info("loadByUsername");
        } catch (Exception ex) {
            logger.error("loadByUsername error");
        }
        return (UserDetails) userRepository.findByUserName(s);
    }
}
