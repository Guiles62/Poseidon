package com.nnk.springboot.serviceTest;

import com.nnk.springboot.domain.User;
import com.nnk.springboot.repositories.UserRepository;
import com.nnk.springboot.services.UserService;
import com.nnk.springboot.services.impl.UserServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserServiceTest {

    @Autowired
    UserServiceImpl userServiceImpl;

    @Mock
    UserRepository userRepository;

    @InjectMocks
    User user;

    List<User> userList = new ArrayList<>();

    @Before
    public void setup() {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String pass = encoder.encode("1234");
        user = new User();
        user.setId(1);
        user.setPassword(pass);
        user.setRole("ADMIN");
        when(userRepository.findAll()).thenReturn(userList);
        userServiceImpl = new UserServiceImpl(userRepository);
    }

    @Test
    @WithMockUser(username = "gui")
    public void getUserListTest() {
        when(userRepository.findAll()).thenReturn(userList);
        assertEquals(0, userServiceImpl.getUserList().size());
    }

    @Test
    @WithMockUser(username = "gui")
    public void addUserTest() {
        when(userRepository.save(user)).thenReturn(user);
        userServiceImpl.addUser(user);
        verify(userRepository,times(1)).save(user);
    }

    @Test
    @WithMockUser(username = "gui")
    public void findByIdTest() {
        when(userRepository.findById(1)).thenReturn(Optional.ofNullable(user));
        userServiceImpl.findById(1);
        verify(userRepository,times(1)).findById(1);
    }

    @Test
    @WithMockUser(username = "gui")
    public void updateUserTest() {
        when(userRepository.findById(1)).thenReturn(Optional.ofNullable(user));
        when(userRepository.save(user)).thenReturn(user);
        userServiceImpl.updateUser(1, user);
        verify(userRepository,times(1)).save(user);
    }

    @Test
    @WithMockUser(username = "gui")
    public void deleteTest() {
        userServiceImpl.delete(user);
        verify(userRepository,times(1)).delete(user);
    }
}
