package com.irlix.traineeship.workplacebooking.services.impl;

import com.irlix.traineeship.workplacebooking.entities.UserEntity;
import com.irlix.traineeship.workplacebooking.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {
    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        log.info("Loading user by email: {}", email);
        UserEntity user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with email " + email));
        if (user.getIsDeleted()) {
            log.warn("User is deleted: {}", email);
            throw new DisabledException("User is deleted! " + email);
        }
        log.info("User loaded successfully: {}", email);
        return UserDetailsImpl.build(user);
    }
}
