package com.fr.register.dto;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.fr.register.entities.Gender;
import com.fr.register.entities.User;
import com.fr.register.model.UserModel;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class UserDto {

	public User toEntity(UserModel model) {
		
		User entity = new User();
		
		entity.setId(model.getId());
		
		entity.setName(model.getName());
		
		entity.setBirthdate(model.getBirthdate());
		
		entity.setCountryOfResidence(model.getCountryOfResidence());
		
		if (StringUtils.isNotBlank(model.getGender())) {
			
			entity.setGender(Gender.valueOf(model.getGender().toUpperCase()));
		}
		else {
			
			entity.setGender(null);
		}

		entity.setPhoneNumber(model.getPhoneNumber());
		
		return entity;

	}

	public UserModel toModel(User entity) {
		
		UserModel model = new UserModel();
		
		model.setId(entity.getId());
		
		model.setName(entity.getName());
		
		model.setBirthdate(entity.getBirthdate());
		
		model.setCountryOfResidence(entity.getCountryOfResidence());
		
		if (null != entity.getGender() && StringUtils.isNotBlank(entity.getGender().toString())) {
			
			model.setGender(entity.getGender().toString());
		}
		else {
			
			model.setGender(null);
		}

		model.setPhoneNumber(entity.getPhoneNumber());
		return model;

	}

	public List<UserModel> toModels(List<User> entities) {
		
		List<UserModel> models = new ArrayList<>();
		
		if (!entities.isEmpty()) {
			
			entities.forEach(e -> {
				
				models.add(toModel(e));
			});
		}
		
		return models;

	}

	public List<User> toEntities(List<UserModel> models) {
		
		List<User> entities = new ArrayList<>();
		
		if (!models.isEmpty()) {
			
			models.forEach(e -> {
				
				entities.add(toEntity(e));
			});
		}
		
		return entities;

	}

}
