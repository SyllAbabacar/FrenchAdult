package com.fr.register.entities;

public enum Gender {
	M("M"),F("F");
	
	private String genre ;

	Gender(String genre) {
		this.genre = genre ;
	}

	public String getGenre() {
		return genre;
	}

	
}
