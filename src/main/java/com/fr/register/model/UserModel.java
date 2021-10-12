package com.fr.register.model;

import java.util.Date;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Pattern;

import com.fr.register.model.constraints.BirthDate;
import com.fr.register.model.constraints.CountryOfResidence;
import com.fr.register.model.constraints.GenderField;

import lombok.Data;

//Model de donnée

@Data
public class UserModel {

	private Long id ;
	
	@NotEmpty(message = "Le nom est ne doit pas etre vide")
	private String name;

	@BirthDate(message = "la date de naissance doit être supérieure ou égale à 18(Être adulte)")
	@Past(message = "La date de naissance doit être dans le passé.")
	@NotNull(message = "La date de naissance est obligatoire")
	private Date birthdate;

	@CountryOfResidence(message = "Le pays de résidence doit être la france")
	@NotEmpty(message = "Le pays de résidence  est obligatoire")
	private String countryOfResidence;

	@Pattern(regexp = "^(?:(?:\\+|00)33|0)\\s*[1-9] (?:[\\s.-]*\\d{2}){4}$", message = "Numéro incorrecte (Exemple: 00(+)33 7 01 02 03 04)")
	private String phoneNumber;

	@GenderField(message = "Genre incorrect (Exemple: M ou F)")
	private String gender;

	
	
	

}
