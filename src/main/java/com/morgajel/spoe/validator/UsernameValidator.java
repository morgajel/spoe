package com.morgajel.spoe.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import com.morgajel.spoe.annotation.ValidUsername;

/**
 * @author jmorgan
 */

public class UsernameValidator implements ConstraintValidator<ValidUsername, String> {

    public void initialize(ValidUsername constraintAnnotation) {
        //nothing to do
    }

    public boolean isValid(String username, ConstraintValidatorContext constraintContext) {
        //if (username.length() < 3 || username.length() > 32 )
        //    return false;
        return false;
    }
}
