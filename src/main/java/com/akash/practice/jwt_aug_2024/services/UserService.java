package com.akash.practice.jwt_aug_2024.services;

import java.util.Optional;

import javax.management.relation.RoleNotFoundException;

// import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.akash.practice.jwt_aug_2024.models.HumanBeing;
import com.akash.practice.jwt_aug_2024.repositories.UserRepo;
import com.akash.practice.jwt_aug_2024.utilities.UniqueId;

import jakarta.transaction.Transactional;

@Service
public class UserService {

    // @Autowired
    UserRepo userRepo;

    public UserService(UserRepo userRepo) {
        this.userRepo = userRepo;
    }

    public ResponseEntity<Object> getUserById(int id) {
        Optional<HumanBeing> temp = userRepo.findById(id);
        if (temp.isPresent()) {
            return ResponseEntity.status(HttpStatus.OK).body(temp.get());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User Id not found");
        }
    }

    public ResponseEntity<Object> saveUser(HumanBeing user) throws RoleNotFoundException {
        if (userRepo.existsByEmail(user.getEmail())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("User with this Email Id is already exists, please try with some different Email Id");
        } else {
            int uniqueId = UniqueId.getNextUserCount();
            if (userRepo.existsByUniqueId(uniqueId)) {
                user.setUniqueId(uniqueId);
                return ResponseEntity.ok(userRepo.save(user));
            } else {
                while (userRepo.existsByUniqueId(uniqueId)) {
                    uniqueId = UniqueId.getNextUserCount();
                }
                user.setUniqueId(uniqueId);
                user.setRolesList();
                return ResponseEntity.ok(userRepo.save(user));
            }
        }

    }

    public ResponseEntity<Object> editUser(HumanBeing user) throws RoleNotFoundException {
        System.out.println("Unique ID is : " + user.getUniqueId());
        System.out.println("ID is : " + user.getId());
        System.out.println("User exist with above unique ID ? " + userRepo.existsByUniqueId(user.getUniqueId()));

        if (user.getUniqueId() == 0) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Please send unique user id with request.");
        }

        if (userRepo.existsByUniqueId(user.getUniqueId())) {

            HumanBeing tempUser = userRepo.findByUniqueId(user.getUniqueId()).get();
            System.out.println(tempUser.getMatchingString(tempUser, user));
            boolean isPreviousAndNewEmailSame = user.getEmail().equals(tempUser.getEmail());

            if (user.equals(tempUser)) {
                // System.out.println("both objects are equal");
                return ResponseEntity.status(HttpStatus.OK).body("No change in data");
            } else {
                if (isPreviousAndNewEmailSame) {
                    // user.setRolesList();
                    return saveUser(user, tempUser);
                } else if (userRepo.existsByEmail(user.getEmail())) {
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                            "User with this Email Id is already exists, please try updating with some different Email Id");
                } else {
                    // user.setRolesList();
                    return saveUser(user, tempUser);
                }
            }

        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("User with this Unique Id not exists.");
        }
    }

    private ResponseEntity<Object> saveUser(HumanBeing user, HumanBeing tempUser) throws RoleNotFoundException {
        tempUser.setValues(user);
        tempUser.setRolesList();
        // String matching = tempUser.getEqualAnswer();
        // System.out.println("from save User : " + tempUser.getEqualAnswer());
        return ResponseEntity.ok(userRepo.save(tempUser));
    }

    @Transactional
    public ResponseEntity<Object> deleteUser(String email) {
        System.out.println("deleting user : ");
        HumanBeing user = userRepo.findByEmail(email).get();
        System.out.println("Unique Id : " + user.getUniqueId());
        // userRepo.delete(user);
        userRepo.deleteAllByEmail(email);
        System.out.println("We are deleting user with unique Id : " + userRepo.findByEmail(email).get().getUniqueId());
        return ResponseEntity.status(HttpStatus.OK).body("Your details deleted");
    }
}
