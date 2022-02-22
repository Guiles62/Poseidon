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

/**
 * <b>UserService is the class that calls the UserRepository and implements UserDetailsService</b>
 * <p>
 *     contain methods
 *     <ul>
 *         <li>getUserList</li>
 *         <li>addUser</li>
 *         <li>findById</li>
 *         <li>updateUser</li>
 *         <li>delete</li>
 *         <li>loadUserByUsername</li>
 *     </ul>
 * </p>
 * @author Guillaume C
 */

@Service
public class UserService implements UserDetailsService {

    @Autowired
    UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    private final static Logger logger = LogManager.getLogger("UserService");

    /**
     * Call repository to find all users
     * @return call repository to find all users
     */
    public List<User> getUserList() {
            logger.info("Call repository to find all users");
        return userRepository.findAll();
    }

    /**
     * Call repository to save new user
     * @param user user to save
     * @return call repository to save the user
     */
    public User addUser (User user) {
            logger.info("Call repository to save new user");
                PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
                String password = passwordEncoder.encode(user.getPassword());
                user.setPassword(password);
        return userRepository.save(user);
    }

    /**
     * Call repository to find a user by id
     * @param id id of the user to find
     * @return call repository to find user by his id
     */
    public Optional<User> findById (int id) {
            logger.info("Call repository to find a user by id");
        return userRepository.findById(id);
    }

    /**
     * Call repository to update a user
     * @param id id of the user to update
     * @param user user to update with changes
     * @return call repository to save user
     */
    public User updateUser (int id, User user) {
        User userFind = userRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + id));
            logger.info("Call repository to update a user");
            BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
            userFind.setFullName(user.getFullName());
            userFind.setUserName(user.getUserName());
            userFind.setRole(user.getRole());
            userFind.setPassword(encoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    /**
     * Call repository to delete a user
     * @param user user to delete
     */
    public void delete (User user) {
            logger.info("Call repository to delete a user");
        userRepository.delete(user);
    }


    /**
     * Call repository to find user by userName
     * @param s string username
     * @return call repository to find user by userName with UserDetails casting
     * @throws UsernameNotFoundException
     */
    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
            logger.info("Call repository to find user by userName");
        return (UserDetails) userRepository.findByUserName(s);
    }

}
