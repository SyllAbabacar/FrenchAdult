package com.fr.register.model.constraints;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.apache.commons.lang3.StringUtils;

public class CountryOfResidenceValidator implements ConstraintValidator<CountryOfResidence, String> {
	@Override
	public boolean isValid(final String valueToValidate,
			final ConstraintValidatorContext context) {
		if(StringUtils.isNotBlank(valueToValidate)) {
			boolean result = false ;
			if(valueToValidate.equalsIgnoreCase("france")) {
				result = true ;
			}else {
				result = false ;
			}
			return result ;
		}else {
			return true ;
		}

	}
}
