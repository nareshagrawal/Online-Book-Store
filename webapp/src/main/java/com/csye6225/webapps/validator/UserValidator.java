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
public class UserValidator implements Validator {

    private final Pattern pattern= Pattern.compile("[a-zA-Z ]+");
    private final Pattern patternemail=Pattern.compile("[a-zA-Z0-9_!#$%&’*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$");
    private final Pattern patternPassword=Pattern.compile("^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9]).{8,15}$");

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
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "firstName", "error.invalid.user", "First Name Required");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "lastName", "error.invalid.user", "Last Name Required");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "password", "error.invalid.password", "Password Required");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "confirmPassword", "error.invalid.confirmPassword", " Confirm Password Required");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "email", "error.invalid.email","Email Required");

        try {

            if(!pattern.matcher(user.getFirstName()).matches()) {
                errors.rejectValue("firstName","error.invalid.user","Name contains only Alphabet");
            }
            if(!pattern.matcher(user.getLastName()).matches()) {
                errors.rejectValue("lastName","error.invalid.user","Name contains only Alphabet");
            }
            if(!patternemail.matcher(user.getEmail()).matches()) {
                errors.rejectValue("email","error.invalid.employee","Invalid Email Format");
            }

            if(!patternPassword.matcher(user.getPassword()).matches()) {
                errors.rejectValue("password","error.invalid.user","Password must be greater than 8 characters,\n must include at least one upper case letter, one lower case letter and one numeric digit");
            }

            User u = userService.findByEmail(user.getEmail());
            if (u != null){
                errors.rejectValue("email", "error.invalid.user", "Email ID already taken");
            }

            if(!user.getConfirmPassword().equals(user.getPassword())){
                errors.rejectValue("confirmPassword", "error.invalid.user", "Password must be same");
            }

        } catch (Exception e) {
            // TODO Auto-generated catch block
            log.warn(e.getMessage());
        }

    }
}

