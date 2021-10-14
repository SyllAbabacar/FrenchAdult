package com.fr.register.entities;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;



@Entity
@Table(name = "USERS")
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@Column(name = "NAME", length = 50 )
	private String name;

	@Column(name = "BIRTHDATE")
	@Temporal(TemporalType.DATE)
	private Date birthdate;

	@Column(name = "COUNTRY_OF_RESIDENCE", length = 40)
	private String countryOfResidence;

	@Column(name = "PHONE_NUMBER", length = 25, nullable = true)
	private String phoneNumber;

	@Column(name = "GENDER", length = 2, nullable = true)
	@Enumerated(EnumType.STRING)
	private Gender gender;

}
