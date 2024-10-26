package com.akash.practice.JWT_aug_2024;

import static org.junit.jupiter.api.Assertions.assertTrue;

import javax.management.relation.RoleNotFoundException;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;

import com.akash.practice.jwt_aug_2024.controllers.UserController;
import com.akash.practice.jwt_aug_2024.models.HumanBeing;

@SpringBootTest
class JwtAug2024ApplicationTests {

	@Autowired
	UserController userController;

	@Test
	void addOneUser() throws RoleNotFoundException {
		ResponseEntity<Object> returnedObjectEntity = userController
				.addUser(new HumanBeing("akash", "akash1212@email.com",
						(short) 24, "ADMIN", "01/01/2000", "pass"));
		// System.out.println(returnedObjectEntity.getBody());
		String className = returnedObjectEntity.getBody().getClass().toString();
		// System.out.println("returned class name is : " + className);
		assertTrue(className.contains("HumanBeing"));
	}

}
