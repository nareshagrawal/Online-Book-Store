package com.csye6225.webapps;

import com.csye6225.webapps.model.User;
import com.csye6225.webapps.repository.UserRepository;
import com.csye6225.webapps.service.UserService;
import org.junit.Before;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class RegistrationAndMailTest {

//    @InjectMocks
//    UserService service;
//
//    @Mock
//    UserRepository repository;
//
//    @Before
//    public void init() {
//        MockitoAnnotations.initMocks(this);
//    }
//
//
//    @Test
//    public void checkEmail() {
//
//        User user = new User();
//        user.setEmail("naresh@gmail.com");
//
//        try {
//
//            when(repository.findByEmail("naresh@gmail.com")).thenReturn(user);
//            User result =(service.findByEmail("naresh@gmail.com"));
//            assertEquals(user.getEmail() ,result.getEmail());
//
//        }catch(Exception e) {
//            e.printStackTrace();
//        }
//    }
//
//    @Test
//    public void registerUser() {
//        User user = new User();
//        user.setFirstName("naresh");
//        user.setLastName("agrawal");
//        user.setEmail("naresh@gmail.com");
//        user.setPassword("123456Na");
//
//        try {
//
//            when(repository.save(user)).thenReturn(user);
//            User result =(service.save(user));
//            assertEquals(user ,result);
//
//        }catch(Exception e) {
//            e.printStackTrace();
//        }
//    }

}
