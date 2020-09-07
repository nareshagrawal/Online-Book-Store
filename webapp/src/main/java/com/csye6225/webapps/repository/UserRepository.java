package com.csye6225.webapps.repository;


import com.csye6225.webapps.model.Book;
import com.csye6225.webapps.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {

    @Query(value= "FROM User u WHERE u.email = :email")
    User findByEmail(String email);


}
