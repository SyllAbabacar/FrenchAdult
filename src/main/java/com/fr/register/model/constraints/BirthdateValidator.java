package com.fr.register.model.constraints;

import java.util.Calendar;
import java.util.Date;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class BirthdateValidator implements ConstraintValidator<BirthDate, Date> {
	@Override
	public boolean isValid(final Date valueToValidate,
			final ConstraintValidatorContext context) {
		if(null != valueToValidate) {
			Calendar dateInCalendar = Calendar.getInstance();
			dateInCalendar.setTime(valueToValidate);
			return Calendar.getInstance().get(Calendar.YEAR) - dateInCalendar.get(Calendar.YEAR) >= 18;
		}else {
			return true ;
		}

	}
}
