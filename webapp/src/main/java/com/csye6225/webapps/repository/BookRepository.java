package com.csye6225.webapps.repository;

import com.csye6225.webapps.model.Book;
import com.csye6225.webapps.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface BookRepository extends JpaRepository<Book, Long> {

    @Query(value="Select * From book b where b.user_userid = :userID",nativeQuery = true)
    List<Book> sellerBooks(Long userID);

    @Query(value="Select * From book b where b.quantity >0 And b.user_userid != :userID",nativeQuery = true)
    List<Book> buyerBooks(Long userID);

}
