package com.csye6225.webapps.repository;

import com.csye6225.webapps.model.ShoppingCart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ShoppingCartRepository extends JpaRepository<ShoppingCart,Long> {

    @Query(value="Select * From shopping_cart c where c.user_userid = :userID",nativeQuery = true)
    ShoppingCart cartByUserID(Long userID);
}
