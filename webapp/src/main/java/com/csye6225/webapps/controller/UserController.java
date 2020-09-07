package com.csye6225.webapps.controller;


import com.csye6225.webapps.comparator.BookComparator;
import com.csye6225.webapps.model.Book;
import com.csye6225.webapps.model.ShoppingCart;
import com.csye6225.webapps.model.User;
import com.csye6225.webapps.service.BookService;
import com.csye6225.webapps.service.ShoppingCartService;
import com.csye6225.webapps.service.UserService;
import com.csye6225.webapps.validator.PasswordResetValidator;
import com.csye6225.webapps.validator.UserValidator;
import com.timgroup.statsd.NonBlockingStatsDClient;
import com.timgroup.statsd.StatsDClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Collections;
import java.util.List;

@Controller
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private BookService bookService;

    @Autowired
    private ShoppingCartService cartService;


    @Autowired
    private UserValidator userValidator;

    @Autowired
    private PasswordResetValidator passwordResetValidator;

    private final Logger log = LoggerFactory.getLogger(this.getClass());
    private static final StatsDClient statsd = new NonBlockingStatsDClient("csye6225.webapp", "localhost", 8125);

    @RequestMapping(value = "/")
    public String index(){
        return "index";
    }

    @RequestMapping(value = "/register", method = RequestMethod.GET)
    public String register(ModelMap model){
        model.addAttribute("user",new User());
        return "registerUsers";
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public  ModelAndView register(@ModelAttribute("user") User user, HttpServletRequest request, BindingResult bindingResult, ModelMap model, ShoppingCart cart){

        ModelAndView mv = new ModelAndView();
        userValidator.validate(user, bindingResult);

        if (bindingResult.hasErrors()) {
            model.addAttribute("user",user);
            mv.setViewName("registerUsers");
            return mv;
        }
        long startTime = System.currentTimeMillis();
        User u = userService.save(user);
        cart.setUser(user);
        cartService.save(cart);
        mv.addObject("user",user);
        mv.setViewName("registrationSuccess");
        log.info("User Registered Successfully");
        statsd.recordExecutionTime("Register User", System.currentTimeMillis() - startTime);
        return mv;
    }
    @RequestMapping(value = "/home", method = RequestMethod.POST)
    public ModelAndView login(HttpServletRequest request ){
        ModelAndView mv = new ModelAndView();
        long startTime = System.currentTimeMillis();
        String userName = request.getParameter("username");
        String password = request.getParameter("password");
        User u = userService.checkLogin(userName,password);
       if(u==null) {
           mv.addObject("error","Email/Password invalid");
           mv.setViewName("error");
           log.error("Email/Password invalid");
       }
       else {
           HttpSession session = (HttpSession) request.getSession();
           session.setAttribute("user", u);
           mv.addObject("user",u);
           List<Book> books = bookService.buyerBooks(u.getUserID());
           Collections.sort(books, new BookComparator());
           mv.addObject("buyerBooks",books);
           mv.setViewName("home");
           log.info("User with UserID:"+u.getUserID()+" login");
           statsd.recordExecutionTime("Login", System.currentTimeMillis() - startTime);
           statsd.incrementCounter("books viewed");
       }
       return mv;
    }

    @RequestMapping(value = "/home", method = RequestMethod.GET)
    public ModelAndView home (HttpServletRequest request){
        ModelAndView mv = new ModelAndView();
        // Checking Session
        HttpSession sessionExpired = (HttpSession) request.getSession(false);
        if(sessionExpired==null )
            mv.setViewName("index");
        else {
            HttpSession session = (HttpSession) request.getSession(false);
            User u = (User) session.getAttribute("user");
            mv.addObject("user",u);
            List<Book> books = bookService.buyerBooks(u.getUserID());
            Collections.sort(books, new BookComparator());
            mv.addObject("buyerBooks",books);
            mv.setViewName("home");
            log.info("User Home");
            statsd.incrementCounter("books viewed");
        }
        return mv;
    }

    @RequestMapping(value = "/updatedetail", method = RequestMethod.GET)
    public ModelAndView updateDetails(HttpServletRequest request){
        ModelAndView mv = new ModelAndView();
        // Checking Session
        HttpSession sessionExpired = (HttpSession) request.getSession(false);
        if(sessionExpired==null)
            mv.setViewName("index");
        else {
            HttpSession session = (HttpSession) request.getSession(false);
            User u = (User) session.getAttribute("user");
            mv.addObject("user",u);
            mv.setViewName("updateUser");
        }
        return mv;
    }
    @RequestMapping(value = "/updatedetail", method = RequestMethod.POST)
    public ModelAndView updateDetail(HttpServletRequest request){
        ModelAndView mv = new ModelAndView();
        // Checking Session
        HttpSession sessionExpired = (HttpSession) request.getSession(false);
        if(sessionExpired==null )
            mv.setViewName("index");
        else {
            long startTime = System.currentTimeMillis();
            HttpSession session = (HttpSession) request.getSession(false);
            User u = (User) session.getAttribute("user");
            u.setFirstName(request.getParameter("firstName"));
            u.setLastName(request.getParameter("lastName"));
            u.setPassword(request.getParameter("password"));

            userService.updateUser(u);
            session.setAttribute("user",u);
            List<Book> books = bookService.buyerBooks(u.getUserID());
            Collections.sort(books, new BookComparator());
            mv.addObject("buyerBooks",books);
            mv.setViewName("home");
            log.info("User detail updated");
            statsd.recordExecutionTime("update user details", System.currentTimeMillis() - startTime);
        }
        return mv;
    }
    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public String logout(HttpServletRequest request){
        long startTime = System.currentTimeMillis();
        HttpSession session = (HttpSession) request.getSession();
        session.invalidate();
        log.info("User logout");
        statsd.recordExecutionTime("logout", System.currentTimeMillis() - startTime);
        return "index";
    }
    @RequestMapping(value = "/passwordreset", method = RequestMethod.GET)
    public String paasordRest(ModelMap model){
        model.addAttribute("password",new User());
        return "passwordReset";
    }

    @RequestMapping(value = "/passwordreset", method = RequestMethod.POST)
    public  ModelAndView passwordReset(@ModelAttribute("password") User password, BindingResult bindingResult, ModelMap model){

        ModelAndView mv = new ModelAndView();
        passwordResetValidator.validate(password, bindingResult);

        if (bindingResult.hasErrors()) {
            model.addAttribute("password",password);
            mv.setViewName("passwordReset");
            return mv;
        }
        long startTime = System.currentTimeMillis();
        userService.passwordReset(password.getEmail());
        mv.setViewName("index");
        log.info("password reset");
        statsd.recordExecutionTime("password reset", System.currentTimeMillis() - startTime);
        return mv;
    }
}
