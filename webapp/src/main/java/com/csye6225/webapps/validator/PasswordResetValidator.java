package com.csye6225.webapps.validator;

import com.csye6225.webapps.model.User;
import com.csye6225.webapps.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import java.util.regex.Pattern;

@Component
public class PasswordResetValidator implements Validator {

    private final Pattern patternemail=Pattern.compile("[a-zA-Z0-9_!#$%&’*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$");

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private UserService userService;

    @Override
    public boolean supports(Class<?> aClass) {
        return User.class.isAssignableFrom(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {

        User user = (User) o;

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "email", "error.invalid.email","Email Required");

        try {

            User u = userService.findByEmail(user.getEmail());
            if (u == null){
                errors.rejectValue("email", "error.invalid.user", "User doesn't exist");
            }

        } catch (Exception e) {
            // TODO Auto-generated catch block
            log.warn(e.getMessage());
        }

    }
}

