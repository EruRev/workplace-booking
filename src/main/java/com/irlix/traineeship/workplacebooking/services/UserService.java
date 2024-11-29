package com.irlix.traineeship.workplacebooking.services;

import com.irlix.traineeship.workplacebooking.dto.UserDTO;
import com.irlix.traineeship.workplacebooking.dto.UserForAdminDTO;
import com.irlix.traineeship.workplacebooking.dto.UserForUserDTO;
import com.irlix.traineeship.workplacebooking.entities.Role;
import com.irlix.traineeship.workplacebooking.entities.BookingEntity;
import com.irlix.traineeship.workplacebooking.entities.UserEntity;
import com.irlix.traineeship.workplacebooking.entities.enums.EnumRole;
import com.irlix.traineeship.workplacebooking.exceptions.ResourceNotFoundException;
import com.irlix.traineeship.workplacebooking.mappers.UserMapper;
import com.irlix.traineeship.workplacebooking.observer.UserRegistrationSubject;
import com.irlix.traineeship.workplacebooking.payload.request.ChangePasswordRequest;
import com.irlix.traineeship.workplacebooking.repositories.RoleRepository;
import com.irlix.traineeship.workplacebooking.repositories.UserRepository;
import com.irlix.traineeship.workplacebooking.services.impl.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {
    private final int QUANTITY_MINUTES = 2400;

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final UserRegistrationSubject userRegistrationSubject;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    @Value("${password.user-pass}")
    private String USER_PASSWORD;

    public UserDTO showUser(final UserDetailsImpl currentUser, final UUID id) {
        UserEntity userEntity = userRepository.findById(id).orElseThrow(() ->
                new ResourceNotFoundException("User not found"));
        if (checkAdminRole(currentUser.getId())) {
            return userMapper.toUserForAdminDTO(userEntity);
        }
        return userMapper.toUserForUserDTO(userEntity);
    }

    public List<UserForAdminDTO> showAllUsers(final Boolean status) {
        if (Objects.isNull(status)) {
            return userRepository.findAll().stream()
                    .map(userMapper::toUserForAdminDTO)
                    .toList();
        }
        return userRepository.findByIsDeleted(status)
                .stream().map(userMapper::toUserForAdminDTO).toList();
    }

    public List<UserForAdminDTO> searchUsers(final String fullName, final String phoneNumber, final String email) {
        if (Objects.isNull(fullName) && Objects.isNull(phoneNumber) && Objects.isNull(email)) {
            return userRepository.findAll().stream()
                    .map(userMapper::toUserForAdminDTO)
                    .toList();
        } else {
            return userRepository.findByFullNameOrPhoneNumberOrEmail(fullName, phoneNumber, email)
                    .stream()
                    .map(userMapper::toUserForAdminDTO)
                    .toList();
        }
    }

    public UserForAdminDTO createUser(final UserForAdminDTO userForAdminDTO) {
        UserEntity userEntity = userMapper.toUserEntity(userForAdminDTO);
        String generatedPassword = passwordEncoder.encode(USER_PASSWORD);
        userEntity.setPassword(generatedPassword);
        Role userRole = roleRepository.findByRoles(EnumRole.ROLE_USER).orElseThrow(() ->
                new RuntimeException("Error: Role USER is not found."));
        userEntity.getRoles().add(userRole);
        userEntity.setPasswordNeedsChange(true);
        userEntity.setAvailableMinutes(QUANTITY_MINUTES);
        userEntity.setIsDeleted(false);
        UserEntity savedUserEntity = userRepository.save(userEntity);
        userRegistrationSubject.notifyAllObservers(savedUserEntity);
        return userMapper.toUserForAdminDTO(savedUserEntity);
    }

    public UserForAdminDTO updateUser(final UUID id, final UserForAdminDTO userForAdminDTO) {
        UserEntity userEntity = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
        userMapper.updateUserEntityFromUserForAdminDTO(userForAdminDTO, userEntity);
        userEntity.setIsDeleted(false);
        return userMapper.toUserForAdminDTO(userRepository.save(userEntity));
    }

    public void deleteUser(final UUID id) {
        UserEntity userEntity = userRepository.findById(id).orElse(null);
        if (Objects.nonNull(userEntity)) {
            userEntity.setIsDeleted(true);
            for (BookingEntity bookingEntity : userEntity.getAvailableBookings()) {
                bookingEntity.setConfirmed(false);
            }
            userRepository.save(userEntity);
        }
    }

    public void changePassword(ChangePasswordRequest request) {
        log.info("Attempting to change password for user with email: {}", request.email());

        UserEntity user = userRepository.findByEmail(request.email())
                .orElseThrow(() -> new UsernameNotFoundException("User not found" + request.email()));

        if (!passwordEncoder.matches(request.oldPassword(), user.getPassword())) {
            log.error("Old password is incorrect for user with email: {}", request.email());
            throw new IllegalArgumentException("Old password is incorrect.");
        }

        String encodedNewPassword = passwordEncoder.encode(request.newPassword());
        user.setPassword(encodedNewPassword);
        user.setPasswordNeedsChange(false);
        userRepository.save(user);
    }

    public void update(UserEntity userEntity) {
        userRepository.save(userEntity);
    }

    public UserEntity getUserByName(final String username) {
        return userRepository.findByFullName(username);
    }

    public boolean checkAdminRole(final UUID id) {
        UserEntity userEntity = userRepository.findById(id).orElse(null);
        return userEntity.getRoles().stream().map(role ->
                role.getRoles()).collect(Collectors.toSet()).contains(EnumRole.ROLE_ADMIN);
    }

    public UserEntity getUserById(final UUID id) {
        return userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("User not found"));
    }
}