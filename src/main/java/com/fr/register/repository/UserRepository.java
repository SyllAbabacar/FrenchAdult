package com.fr.register.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.fr.register.entities.User;

public interface UserRepository extends JpaRepository<User, Long> {

	Optional<User> findByName(String name);

}
