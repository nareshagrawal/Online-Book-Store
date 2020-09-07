package com.csye6225.webapps.service;

import com.csye6225.webapps.model.ShoppingCart;
import com.csye6225.webapps.repository.ShoppingCartRepository;
import com.timgroup.statsd.NonBlockingStatsDClient;
import com.timgroup.statsd.StatsDClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ShoppingCartService {

    @Autowired
    ShoppingCartRepository repository;

    private static final StatsDClient statsd = new NonBlockingStatsDClient("csye6225.webapp", "localhost", 8125);

    public void save(ShoppingCart cart){
        long startTime = System.currentTimeMillis();
        repository.save(cart);
        statsd.recordExecutionTime("DB  save cart", System.currentTimeMillis() - startTime);
    }

    public ShoppingCart cartByUserID(Long userID){
        long startTime = System.currentTimeMillis();
        ShoppingCart c= repository.cartByUserID(userID);
        statsd.recordExecutionTime("DB  cartByUserID", System.currentTimeMillis() - startTime);
        return c;
    }

}
