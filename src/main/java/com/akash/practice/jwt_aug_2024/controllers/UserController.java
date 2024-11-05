package com.akash.practice.jwt_aug_2024.controllers;

import javax.management.relation.RoleNotFoundException;
import javax.naming.NoPermissionException;

import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.akash.practice.jwt_aug_2024.models.HumanBeing;
import com.akash.practice.jwt_aug_2024.repositories.UserRepo;
// import com.akash.practice.jwt_aug_2024.repositories.UserRepo;
import com.akash.practice.jwt_aug_2024.services.UserService;
import com.akash.practice.jwt_aug_2024.utilities.exceptionHandling.UserNotAuthenticatedException;

import jakarta.transaction.Transactional;

@RestController
@RequestMapping("/users")
public class UserController {

	UserService userService;
	UserRepo userRepo;

	public UserController(UserService userService, UserRepo userRepo) {
		this.userService = userService;
		this.userRepo = userRepo;
	}

	@GetMapping("/{id}")
	ResponseEntity<Object> getUserById(@PathVariable int id) throws NoPermissionException {
		String email = SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();
		if (userRepo.findByEmail(email).get().getUniqueId() == id) {
			return userService.getUserById(id);
		} else {
			throw new NoPermissionException("You are trying to access details which you are not owner");
		}
	}

	@PostMapping
	public ResponseEntity<Object> addUser(@RequestBody HumanBeing user) throws RoleNotFoundException {
		return userService.saveUser(user);
	}

	// @DeleteMapping
	// @Transactional
	// public ResponseEntity<Object> deleteUser() throws
	// UserNotAuthenticatedException {
	// Authentication authentication =
	// SecurityContextHolder.getContext().getAuthentication();
	// if (authentication.isAuthenticated()) {
	// String email = authentication.getPrincipal().toString();
	// System.out.println("Email is : " + email);
	// return userService.deleteUser(email);
	// } else {
	// throw new UserNotAuthenticatedException("You are not authenticated");
	// }
	// }

	@PutMapping
	ResponseEntity<Object> editUser(@RequestBody HumanBeing user) throws RoleNotFoundException, NoPermissionException {

		String email = SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();
		if (userRepo.findByEmail(email).get().getUniqueId() == user.getUniqueId()) {
			return userService.editUser(user);
		} else {
			throw new NoPermissionException("You are trying to access details which you are not authorized");
		}

	}

}
