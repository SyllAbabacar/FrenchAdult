package com.fr.register.model.constraints;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.apache.commons.lang3.StringUtils;

import com.fr.register.entities.Gender;

public class GenderValidator implements ConstraintValidator<GenderField, String> {
	@Override
	public boolean isValid(final String valueToValidate,
			final ConstraintValidatorContext context) {
		boolean result = false;
		if(StringUtils.isNotBlank(valueToValidate)) {
			for(Gender genre : Gender.values()) {
				if(genre.getGenre().equals(valueToValidate.toUpperCase())) {
					result = true ;
				}
			}
		}else {
			result = true ;
		}
		
		return result ;
	}
}
