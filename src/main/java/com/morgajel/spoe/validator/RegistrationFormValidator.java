package com.morgajel.spoe.validator;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import org.springframework.validation.ValidationUtils;

import com.morgajel.spoe.model.Account;

public class RegistrationFormValidator implements Validator {

	@Override
	public boolean supports(Class<?> clazz) {
        return Account.class.isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object obj, Errors errors) {
        Account account = (Account) obj;
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "username", "error.blank.username");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "password", "error.blank.password");
	}
}
