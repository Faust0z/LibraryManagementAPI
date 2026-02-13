package com.faust0z.BookLibraryAPI.service;

import com.faust0z.BookLibraryAPI.dto.*;
import com.faust0z.BookLibraryAPI.entity.UserEntity;
import com.faust0z.BookLibraryAPI.exception.InvalidPasswordException;
import com.faust0z.BookLibraryAPI.exception.ResourceNotFoundException;
import com.faust0z.BookLibraryAPI.exception.SamePasswordException;
import com.faust0z.BookLibraryAPI.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;


    public UserService(UserRepository userRepository, ModelMapper modelMapper, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
        this.passwordEncoder = passwordEncoder;
    }

    public UserDTO convertToDto(UserEntity user) {
        return modelMapper.map(user, UserDTO.class);
    }

    public MyUserDetailsDTO convertToMyDetailsDto(UserEntity user) {
        return modelMapper.map(user, MyUserDetailsDTO.class);
    }

    public AdminUserDTO convertToAdminDto(UserEntity user) {
        return modelMapper.map(user, AdminUserDTO.class);
    }

    @Cacheable(value = "users", key = "'list:all'")
    public List<AdminUserDTO> getAllUsers() {
        return userRepository.findAll()
                .stream()
                .map(this::convertToAdminDto)
                .collect(Collectors.toList());
    }

    @Cacheable(value = "users", key = "'detail:' + #userId")
    public AdminUserDTO getUserbyId(UUID userId) {
        UserEntity user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + userId));

        return convertToAdminDto(user);
    }

    @Cacheable(value = "user_details", key = "#userId")
    public MyUserDetailsDTO getMyDetails(UUID userId) {
        UserEntity user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found (Token might be stale)"));

        return convertToMyDetailsDto(user);
    }

    @Caching(evict = {
            @CacheEvict(value = "users", key = "'detail:' + #userId"),
            @CacheEvict(value = "users", key = "'list:all'")
    })
    @Transactional
    public UserDTO updateUser(UUID userId, UpdateUserDTO dto) {
        UserEntity existingUser = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + userId));
        modelMapper.map(dto, existingUser);

        UserEntity updatedUser = userRepository.save(existingUser);
        return convertToDto(updatedUser);
    }

    @Transactional
    public MyUserDetailsDTO updateUserPassword(UUID userId, UpdateUserPasswordDTO dto) {
        UserEntity existingUser = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + userId));

        if (!passwordEncoder.matches(dto.getCurrentPassword(), existingUser.getPassword())) {
            throw new InvalidPasswordException("Provided current password is incorrect");
        }

        if (passwordEncoder.matches(dto.getNewPassword(), existingUser.getPassword())) {
            throw new SamePasswordException("New password cannot be the same as the old password");
        }

        existingUser.setPassword(passwordEncoder.encode(dto.getNewPassword()));
        UserEntity updatedUser = userRepository.save(existingUser);
        return convertToMyDetailsDto(updatedUser);
    }
}
