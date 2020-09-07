package com.csye6225.webapps.controller;

import com.csye6225.webapps.model.Book;
import com.csye6225.webapps.model.CartItem;
import com.csye6225.webapps.model.ShoppingCart;
import com.csye6225.webapps.model.User;
import com.csye6225.webapps.service.BookService;
import com.csye6225.webapps.service.CartItemService;
import com.csye6225.webapps.service.ShoppingCartService;
import com.timgroup.statsd.NonBlockingStatsDClient;
import com.timgroup.statsd.StatsDClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/buyer/**")
public class BuyerController {

    @Autowired
    ShoppingCartService cartService;

    @Autowired
    CartItemService cartItemService;

    @Autowired
    BookService bookService;

    private final Logger log = LoggerFactory.getLogger(this.getClass());
    private static final StatsDClient statsd = new NonBlockingStatsDClient("csye6225.webapp", "localhost", 8125);

    @RequestMapping(value = "/buyer/addcart", method = RequestMethod.POST)
    public void addTOCart (HttpServletRequest request, CartItem cartItem) {
            long startTime = System.currentTimeMillis();

            HttpSession sessionExit = (HttpSession) request.getSession(false);
            User user = (User) sessionExit.getAttribute("user");
            ShoppingCart cart = cartService.cartByUserID(user.getUserID());
            Book book = bookService.bookById(Long.parseLong(request.getParameter("id")));
            int quant =Integer.parseInt (request.getParameter("quantityAdd"));
            boolean flag = false;
            Long itemID = 0L;

            for(CartItem item: cart.getCartItem()){
                if(item.getBook().getBookID() == book.getBookID()){
                    flag = true;
                    itemID =item.getCartItemID();
                }
            }
            if(flag){
              CartItem exitItem = cartItemService.cartItemByID(itemID);
              int temp =exitItem.getQuantityAdd()+quant;
              exitItem.setQuantityAdd(temp);
              cartItemService.save(exitItem);
              log.info("Book added to cart");
              statsd.recordExecutionTime("add to cart", System.currentTimeMillis() - startTime);
            }
            else {
                cartItem.setQuantityAdd(quant);
                cartItem.setBook(book);
                cartItem.setShoppinhCart(cart);
                cartItemService.save(cartItem);
                cart.getCartItem().add(cartItem);
                cartService.save(cart);
                log.info("Book added to cart");
                statsd.recordExecutionTime("add to cart", System.currentTimeMillis() - startTime);
            }
    }

    @RequestMapping(value = "/buyer/cart", method = RequestMethod.GET)
    public ModelAndView cart (HttpServletRequest request) {
        ModelAndView mv = new ModelAndView();
        // Checking Session
        HttpSession sessionExit = (HttpSession) request.getSession(false);
        if (sessionExit == null)
            mv.setViewName("index");
        else {
            long startTime = System.currentTimeMillis();
            User user = (User) sessionExit.getAttribute("user");
            ShoppingCart cart = cartService.cartByUserID(user.getUserID());
            mv.addObject("cartItem",cart.getCartItem());
            mv.setViewName("shoppingCart");
            log.info("View cart");
            statsd.recordExecutionTime("view cart", System.currentTimeMillis() - startTime);
        }
        return mv;
    }

    @RequestMapping(value = "/buyer/updatecart", method = RequestMethod.POST)
    public ModelAndView updateCart (HttpServletRequest request) {
        ModelAndView mv = new ModelAndView();
        long startTime = System.currentTimeMillis();
        int quant =Integer.parseInt (request.getParameter("quantityAdd"));
        CartItem exitItem = cartItemService.cartItemByID(Long.parseLong(request.getParameter("id")));
        exitItem.setQuantityAdd(quant);
        cartItemService.save(exitItem);
        HttpSession sessionExit = (HttpSession) request.getSession(false);
        User user = (User) sessionExit.getAttribute("user");
        ShoppingCart cart = cartService.cartByUserID(user.getUserID());
        mv.addObject("cartItem",cart.getCartItem());
        mv.setViewName("shoppingCart");
        log.info("Cart updated");
        statsd.recordExecutionTime("update cart", System.currentTimeMillis() - startTime);
        return mv;
    }

    @RequestMapping(value = "/buyer/removeitem", method = RequestMethod.GET)
    public ModelAndView removeItem (HttpServletRequest request) {
        ModelAndView mv = new ModelAndView();
        // Checking Session
        HttpSession sessionExit = (HttpSession) request.getSession(false);
        if (sessionExit == null)
            mv.setViewName("index");
        else {
            long startTime = System.currentTimeMillis();
            User user = (User) sessionExit.getAttribute("user");
            ShoppingCart cart = cartService.cartByUserID(user.getUserID());
            Long id = Long.parseLong(request.getParameter("id"));
            boolean flag = false;
            for(CartItem item: cart.getCartItem()){
                if(item.getCartItemID() ==id ){
                    flag = true;
                }
            }
            if(flag){
                CartItem exitItem = cartItemService.cartItemByID(id);
                cart.getCartItem().remove(exitItem);
                cartService.save(cart);
                cartItemService.delete(exitItem);
                ShoppingCart cartNew = cartService.cartByUserID(user.getUserID());
                mv.addObject("cartItem", cartNew.getCartItem());
                mv.setViewName("shoppingCart");
                log.info("Book removed from cart");
                statsd.recordExecutionTime("remove from cart", System.currentTimeMillis() - startTime);
            }else{
                mv.addObject("error","You can't remove book from cart");
                mv.setViewName("error");
                log.warn("You can't remove this book");
            }
        }
        return mv;
    }
}
