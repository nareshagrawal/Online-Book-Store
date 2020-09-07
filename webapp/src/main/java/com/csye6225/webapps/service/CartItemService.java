package com.csye6225.webapps.service;

import com.csye6225.webapps.model.CartItem;
import com.csye6225.webapps.repository.CartItemRepository;
import com.timgroup.statsd.NonBlockingStatsDClient;
import com.timgroup.statsd.StatsDClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CartItemService {

    @Autowired
    CartItemRepository repository;

    private static final StatsDClient statsd = new NonBlockingStatsDClient("csye6225.webapp", "localhost", 8125);

    public void save(CartItem item){
        long startTime = System.currentTimeMillis();
        repository.save(item);
        statsd.recordExecutionTime("DB  add to cart", System.currentTimeMillis() - startTime);
    }

    public CartItem cartItemByID(long itemID){
        return repository.findById(itemID).orElse(null);
    }

     public List<CartItem> cartItemByBookID(Long bookID){
         long startTime = System.currentTimeMillis();
         List<CartItem> ci = repository.cartItemByBookID(bookID);
         statsd.recordExecutionTime("DB cartItemByBookID", System.currentTimeMillis() - startTime);
         return ci;
    }

    public void delete(CartItem item){
        long startTime = System.currentTimeMillis();
        repository.delete(item);
        statsd.recordExecutionTime("DB delete book from cart", System.currentTimeMillis() - startTime);
    }
}
