package com.csye6225.webapps.repository;

import com.csye6225.webapps.model.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CartItemRepository extends JpaRepository<CartItem,Long> {

    @Query(value="Select * From cart_item i where i.book_bookid = :bookID",nativeQuery = true)
    List<CartItem> cartItemByBookID(Long bookID);
}
