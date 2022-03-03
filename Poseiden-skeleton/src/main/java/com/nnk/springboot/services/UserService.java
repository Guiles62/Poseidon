package com.nnk.springboot.services;


import com.nnk.springboot.domain.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

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


public interface UserService {


    Logger logger = LogManager.getLogger("UserService");

    /**
     * Call repository to find all users
     * @return call repository to find all users
     */
    List<User> getUserList();

    /**
     * Call repository to save new user
     * @param user user to save
     * @return call repository to save the user
     */
    User addUser (User user);

    /**
     * Call repository to find a user by id
     * @param id id of the user to find
     * @return call repository to find user by his id
     */
    Optional<User> findById (int id);

    /**
     * Call repository to update a user
     * @param id id of the user to update
     * @param user user to update with changes
     * @return call repository to save user
     */
    User updateUser (int id, User user);

    /**
     * Call repository to delete a user
     * @param user user to delete
     */
    void delete (User user);

}
