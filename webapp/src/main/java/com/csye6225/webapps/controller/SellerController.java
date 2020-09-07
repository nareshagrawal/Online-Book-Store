package com.csye6225.webapps.controller;

import com.csye6225.webapps.comparator.BookComparator;
import com.csye6225.webapps.model.Book;
import com.csye6225.webapps.model.BookImages;
import com.csye6225.webapps.model.CartItem;
import com.csye6225.webapps.model.User;
import com.csye6225.webapps.service.BookImageService;
import com.csye6225.webapps.service.BookService;
import com.csye6225.webapps.service.CartItemService;
import com.timgroup.statsd.NonBlockingStatsDClient;
import com.timgroup.statsd.StatsDClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.*;

@Controller
@RequestMapping("/seller/**")
public class SellerController {

    @Autowired
    BookService bookService;

    @Autowired
    CartItemService cartItemService;

    @Autowired
    BookImageService imageService;

    private final Logger log = LoggerFactory.getLogger(this.getClass());
    private static final StatsDClient statsd = new NonBlockingStatsDClient("csye6225.webapp", "localhost", 8125);

    @RequestMapping(value = "/seller", method = RequestMethod.GET)
    public ModelAndView sellerHome (HttpServletRequest request) {
        ModelAndView mv = new ModelAndView();
        // Checking Session
        HttpSession sessionExit = (HttpSession) request.getSession(false);
        if (sessionExit == null)
            mv.setViewName("index");
        else {
            long startTime = System.currentTimeMillis();
            User user = (User) sessionExit.getAttribute("user");
            List<Book> books = bookService.sellerBooks(user.getUserID());
            Collections.sort(books, new BookComparator());
            mv.addObject("sellerBooks",books);
            mv.setViewName("Seller");
            log.info("Seller home page");
            statsd.recordExecutionTime("seller home", System.currentTimeMillis() - startTime);
        }
        return mv;
    }

    @RequestMapping(value = "/seller/addbook", method = RequestMethod.GET)
    public ModelAndView addBook (HttpServletRequest request, Model model) {
        ModelAndView mv = new ModelAndView();
        // Checking Session
        HttpSession sessionExit = (HttpSession) request.getSession(false);
        if (sessionExit == null)
            mv.setViewName("index");
        else {
            mv.setViewName("addBook");
        }
        return mv;
    }
    @RequestMapping(value = "/seller/addbook", method = RequestMethod.POST)
    public ModelAndView registerBook (HttpServletRequest request, Book book, @RequestParam("image") List<MultipartFile> file) {
        ModelAndView mv = new ModelAndView();
        HttpSession session = (HttpSession) request.getSession();
        long startTime = System.currentTimeMillis();
        User user = (User) session.getAttribute("user");
        book.setTitle(request.getParameter("title"));
        book.setISBN(request.getParameter("ISBN"));
        book.setAuthors(request.getParameter("authors"));
        book.setPublicationDate(request.getParameter("publicationDate"));
        book.setQuantity( Integer.parseInt (request.getParameter("quantity")));
        book.setPrice(Double.parseDouble(request.getParameter("price")));
        book.setUser(user);
        book.setBookAddedTime(new Date());

        Book b = bookService.save(book);

        if(!(file.get(0).isEmpty())){

                Iterator<MultipartFile> i = file.iterator();
                while (i.hasNext()) {
                MultipartFile temp = i.next();
                imageService.uploadeImage(temp, b);
            }
        }
        List<Book> books = bookService.sellerBooks(user.getUserID());
        Collections.sort(books, new BookComparator());
        mv.addObject("sellerBooks",books);
        mv.setViewName("Seller");
        log.info("New book added");
        statsd.recordExecutionTime("book added to store", System.currentTimeMillis() - startTime);
        return mv;
    }
    @RequestMapping(value = "/seller/updatebook", method = RequestMethod.GET)
    public ModelAndView updateBook (HttpServletRequest request) {
        ModelAndView mv = new ModelAndView();
        HashMap<String, String > imagesURL = new HashMap<>();
        // Checking Session
        HttpSession sessionExit = (HttpSession) request.getSession(false);
        if (sessionExit == null)
            mv.setViewName("index");
        else {
            long startTime = System.currentTimeMillis();
            boolean flag = false;
            Long id = Long.parseLong(request.getParameter("id"));
            User user = (User) sessionExit.getAttribute("user");
            List<Book> books = bookService.sellerBooks(user.getUserID());
            Iterator<Book> i=books.iterator();
            while(i.hasNext()) {
                Book temp =i.next();
                if(temp.getBookID()== id){
                    flag=true;
                }
            }
            if(flag){
                List<String> imagesName = imageService.imagesName(id);
                if(imagesName==null) imagesName = new ArrayList<>();
                    Iterator<String> k = imagesName.iterator();
                    while (k.hasNext()) {
                        String temp = k.next();
                        String image = imageService.viewImage(temp);
                        imagesURL.put(temp, image);
                    }
                    mv.addObject("images", imagesURL);
                    mv.addObject("book", bookService.bookById(id));
                    mv.setViewName("updateBook");
                statsd.recordExecutionTime("book update GET", System.currentTimeMillis() - startTime);

            }else{
                mv.addObject("error","You can't update this book");
                mv.setViewName("error");
                log.warn("You can't update this book");
            }
        }
        return mv;
    }
    @RequestMapping(value = "/seller/updatebook", method = RequestMethod.POST)
    public ModelAndView updateBookS (HttpServletRequest request,@RequestParam("image") List<MultipartFile> file) {
        ModelAndView mv = new ModelAndView();
        long startTime = System.currentTimeMillis();
        Long id = Long.parseLong(request.getParameter("id"));
        Book book = bookService.bookById(id);
        book.setTitle(request.getParameter("title"));
        book.setISBN(request.getParameter("ISBN"));
        book.setAuthors(request.getParameter("authors"));
        book.setPublicationDate(request.getParameter("publicationDate"));
        book.setQuantity( Integer.parseInt (request.getParameter("quantity")));
        book.setPrice(Double.parseDouble(request.getParameter("price")));
        book.setBookUpdateTime(new Date());
        Book b = bookService.save(book);
        if(!(file.get(0).isEmpty())) {
            Iterator<MultipartFile> i = file.iterator();
            while (i.hasNext()) {
                MultipartFile temp = i.next();
                imageService.uploadeImage(temp, b);
            }
        }
        HttpSession sessionExit = (HttpSession) request.getSession(false);
        User user = (User) sessionExit.getAttribute("user");
        List<Book> books = bookService.sellerBooks(user.getUserID());
        Collections.sort(books, new BookComparator());
        mv.addObject("sellerBooks",books);
        mv.setViewName("Seller");
        log.info("Book updated");
        statsd.recordExecutionTime("book update POST", System.currentTimeMillis() - startTime);
        return mv;
    }

    @RequestMapping(value = "/seller/deletebook", method = RequestMethod.GET)
    public ModelAndView deleteBook (HttpServletRequest request) {
        ModelAndView mv = new ModelAndView();
        boolean flag = false;
        long startTime = System.currentTimeMillis();
        Long id = Long.parseLong(request.getParameter("id"));
        HttpSession sessionExit = (HttpSession) request.getSession(false);
        User user = (User) sessionExit.getAttribute("user");
        List<Book> books = bookService.sellerBooks(user.getUserID());
        Iterator<Book> i=books.iterator();
        while(i.hasNext()) {
            Book temp =i.next();
            if(temp.getBookID()== id){
                flag=true;
            }
        }
        if(flag){
            List<CartItem> item = cartItemService.cartItemByBookID(id);
            if(item.size()!=0){
                Iterator<CartItem> it =item.iterator();
                while(it.hasNext()) {
                    CartItem temp = it.next();
                    cartItemService.delete(temp);
                }
            }
            List<String> imagesName = imageService.imagesName(id);
            if(imagesName.size()!=0) {

                Iterator<String> k = imagesName.iterator();
                while (k.hasNext()) {
                    String temp = k.next();
                    imageService.deleteS3Image(temp);
                }

                List<BookImages> images = imageService.images(id);
                Iterator<BookImages> j = images.iterator();
                while (j.hasNext()) {
                    BookImages temp = j.next();
                    imageService.deleteDBImage(temp.getImageID());
                }
            }
            bookService.deleteBook(id);
            List<Book> bookUp = bookService.sellerBooks(user.getUserID());
            Collections.sort(bookUp, new BookComparator());
            mv.addObject("sellerBooks",bookUp);
            mv.setViewName("Seller");
            log.info("Book deleted");
            statsd.recordExecutionTime("book deleted", System.currentTimeMillis() - startTime);
        }else{
            mv.addObject("error","You can't delete this book");
            mv.setViewName("error");
            log.warn("You can't delete this book");
        }
       return mv;
    }

    @RequestMapping(value = "/seller/viewimage", method = RequestMethod.GET)
    public ModelAndView viewImage (HttpServletRequest request) {
        HashMap<String, String > imagesURL = new HashMap<>();
        ModelAndView mv = new ModelAndView();
        // Checking Session
        HttpSession sessionExit = (HttpSession) request.getSession(false);
        if (sessionExit == null)
            mv.setViewName("index");
        else {
            Long bookID = Long.parseLong(request.getParameter("id"));
            List<String> imagesName = imageService.imagesName(bookID);
            if(imagesName.size()==0){
                mv.addObject("error","Image is not available");
                mv.setViewName("error");
            }else{
                long startTime = System.currentTimeMillis();
                Iterator<String> k = imagesName.iterator();
                while (k.hasNext()) {
                    String temp = k.next();
                    String image = imageService.viewImage(temp);
                    imagesURL.put(temp,image);
                }
                mv.addObject("images",imagesURL);
                mv.setViewName("viewImages");
                log.info("Viewing Book images");
                statsd.recordExecutionTime("view book image", System.currentTimeMillis() - startTime);
            }
        }
        return mv;
    }

@RequestMapping(value = "/seller/deleteimage", method = RequestMethod.GET)
public ModelAndView deleteImage (HttpServletRequest request) {
    ModelAndView mv = new ModelAndView();
    HashMap<String, String > imagesURL = new HashMap<>();
    boolean flag = false;
    // Checking Session
    HttpSession sessionExit = (HttpSession) request.getSession(false);
    if (sessionExit == null)
        mv.setViewName("index");
    else {
        long startTime = System.currentTimeMillis();
        String imageName = request.getParameter("imageName");
        Long id = Long.parseLong(request.getParameter("id"));
        User user = (User) sessionExit.getAttribute("user");
        List<Book> books = bookService.sellerBooks(user.getUserID());
        Iterator<Book> i = books.iterator();
        while (i.hasNext()) {
            Book temp = i.next();
            if (temp.getBookID() == id) {
                flag = true;
            }
        }
        if (flag) {
            BookImages image = imageService.imageByName(imageName);
            if (image==null){
                mv.addObject("error","You can't delete this image");
                mv.setViewName("error");
            }else {
                imageService.deleteS3Image(image.getImageName());
                imageService.deleteDBImage(image.getImageID());
                List<String> imagesName = imageService.imagesName(id);
                if (imagesName == null)
                    imagesName = new ArrayList<>();

                Iterator<String> k = imagesName.iterator();
                while (k.hasNext()) {
                    String temp = k.next();
                    String imageU = imageService.viewImage(temp);
                    imagesURL.put(temp, imageU);
                }
                mv.addObject("images", imagesURL);
                mv.addObject("book", bookService.bookById(id));
                mv.setViewName("updateBook");
                log.info("Book images deleted");
                statsd.recordExecutionTime("delete book image", System.currentTimeMillis() - startTime);
            }
        }else{
            mv.addObject("error","You can't delete this image");
            mv.setViewName("error");
            log.warn("You can't delete this image");
        }
    }

    return mv;
    }

}
