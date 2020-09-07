package com.csye6225.webapps.service;


import com.amazonaws.services.sns.AmazonSNS;
import com.amazonaws.services.sns.AmazonSNSClientBuilder;
import com.amazonaws.services.sns.model.PublishRequest;
import com.amazonaws.services.sns.model.PublishResult;
import com.csye6225.webapps.model.Book;
import com.csye6225.webapps.model.User;
import com.csye6225.webapps.repository.UserRepository;
import com.timgroup.statsd.NonBlockingStatsDClient;
import com.timgroup.statsd.StatsDClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    @Autowired
    private UserRepository repository;

    @Value("${snstopic:aDefaultValue}")
    private String snsTopic;

    private static final StatsDClient statsd = new NonBlockingStatsDClient("csye6225.webapp", "localhost", 8125);
    private final Logger log = LoggerFactory.getLogger(this.getClass());

    public User save(User user) {
        user.setPassword(BCrypt.hashpw(user.getPassword(), BCrypt.gensalt(10)));
        long startTime = System.currentTimeMillis();
        User u= repository.save(user);
        statsd.recordExecutionTime("DB save user", System.currentTimeMillis() - startTime);
        return u;
    }
    public User findByEmail(String email){
        long startTime = System.currentTimeMillis();
        User u= repository.findByEmail(email);
        statsd.recordExecutionTime("DB findByEmail", System.currentTimeMillis() - startTime);
        return u;
    }

    public User checkLogin(String email, String password) {
        long startTime = System.currentTimeMillis();
          User user = repository.findByEmail(email);
          if(user == null)
              return user ;
          else {
              if (BCrypt.checkpw(password, user.getPassword())) {
                  statsd.recordExecutionTime("DB check login", System.currentTimeMillis() - startTime);
                  return user;
              }
          }
        statsd.recordExecutionTime("DB check login", System.currentTimeMillis() - startTime);
          return null;
    }
    public void updateUser(User u){
        long startTime = System.currentTimeMillis();
        User user = repository.findByEmail(u.getEmail());
        user.setPassword(BCrypt.hashpw(u.getPassword(), BCrypt.gensalt(10)));
        user.setFirstName(u.getFirstName());
        user.setLastName(u.getLastName());
        repository.save(user);
        statsd.recordExecutionTime("DB update user", System.currentTimeMillis() - startTime);
    }

    public void passwordReset(String email){
        log.info("in password reset");
        AmazonSNS snsClient = AmazonSNSClientBuilder.defaultClient();

        final String msg = email;
        final PublishRequest publishRequest = new PublishRequest(snsTopic, msg);
        final PublishResult publishResponse = snsClient.publish(publishRequest);
        log.info("after publish");
    }


}
