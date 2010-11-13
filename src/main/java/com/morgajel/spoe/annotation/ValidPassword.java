package com.morgajel.spoe.annotation;

import static java.lang.annotation.ElementType.*;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.*;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.constraints.NotNull;

import com.morgajel.spoe.validator.PasswordValidator;
import java.lang.annotation.ElementType;
import java.lang.annotation.RetentionPolicy;

/**
 * @author jmorgan
 *
 */

@NotNull
@Target({ElementType.FIELD, ElementType.PARAMETER })
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = PasswordValidator.class)
@Documented
public @interface ValidPassword {

    String message() default "Password is Invalid";
    Class[] groups() default {};
    Class[] payload() default {};

}
