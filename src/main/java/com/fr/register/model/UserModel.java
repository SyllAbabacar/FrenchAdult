package com.fr.register.model;

import java.util.Date;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import com.fr.register.model.constraints.BirthDate;
import com.fr.register.model.constraints.CountryOfResidence;
import com.fr.register.model.constraints.GenderField;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

//Model de donnée

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserModel {

	private Long id;

	@NotBlank(message = "The name must not be empty")
	@Pattern(regexp = "^[a-zA-Z àâäèéêëîïôœùûüÿçÀÂÄÈÉŒÇ]+$", message = "A name with numeric or special characters is not allowed")
	@Size(min = 3, max = 50, message = "A name must have a minimum of 3 characters and a maximum of 50 characters")
	private String name;

	@BirthDate(message = "The birthdate must be greater than or equal to 18 (Only adult)")
	@Past(message = "The birthdate must be in the past ")
	@NotNull(message = "La date de naissance est obligatoire")
	private Date birthdate;

	@CountryOfResidence(message = "The country of residence must be France")
	@Pattern(regexp = "^[a-zA-Z àâäèéêëîïôœùûüÿçÀÂÄÈÉŒÇ]+$", message = "A country with numeric or special characters is not allowed")
	@NotBlank(message = "Country of residence is required")
	private String countryOfResidence;

	@Pattern(regexp = "^(?:(?:\\+|00)33|0)\\s*[1-9] (?:[\\s.-]*\\d{2}){4}$", message = "Incorrect number (Example: 00(+)33 7 01 02 03 04)")
	private String phoneNumber;

	@GenderField(message = "Incorrect gender (Example: M or F)")
	private String gender;

}
