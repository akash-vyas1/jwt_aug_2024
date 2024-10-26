package com.akash.practice.jwt_aug_2024.security;

import java.util.Optional;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import com.akash.practice.jwt_aug_2024.models.HumanBeing;
import com.akash.practice.jwt_aug_2024.repositories.UserRepo;

@Component
public class MyUserDetailsService implements UserDetailsService {

    UserRepo userRepo;

    public MyUserDetailsService(UserRepo userRepo) {
        this.userRepo = userRepo;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<HumanBeing> user = userRepo.findByEmail(email);
        if (user.isPresent()) {

            return new User(
                    user.get().getEmail(),
                    user.get().getPassword(),
                    AuthorityUtils.createAuthorityList(user.get().getRolesList()));

        } else {
            throw new UsernameNotFoundException("Email " + email + " not found in system.");
        }
    }

}
