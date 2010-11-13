/**
 * 
 */
package com.morgajel.spoe.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import com.morgajel.spoe.annotation.ValidPassword;
import com.morgajel.spoe.annotation.ValidUsername;

/**
 * @author jmorgan
 *
 */
public class PasswordValidator implements ConstraintValidator<ValidPassword, String> {

    public void initialize(ValidPassword constraintAnnotation) {
        //nothing to do
    }

    public boolean isValid(String password, ConstraintValidatorContext constraintContext) {
        if (password.length() < 4 || password.length() > 32 )
            return false;
        return true;
    }
}
