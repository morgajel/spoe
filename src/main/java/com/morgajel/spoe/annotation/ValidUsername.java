package com.morgajel.spoe.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.constraints.NotNull;

import com.morgajel.spoe.validator.UsernameValidator;
import java.lang.annotation.ElementType;
import java.lang.annotation.RetentionPolicy;
/**

 * @author jmorgan
 *
 */
@NotNull
@Target({ElementType.FIELD, ElementType.PARAMETER })
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = UsernameValidator.class)
@Documented
public @interface ValidUsername {

    String message() default "Username is Invalid";
    Class[] groups() default {};
    Class[] payload() default {};

}

