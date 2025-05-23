package com.mgcfgs.LibraryManagement.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mgcfgs.LibraryManagement.model.RegisterUser;
import com.mgcfgs.LibraryManagement.repository.UserRepository;


@Service
public class UserServices {
    // This class is responsible for user-related services, such as registration and
    // login.
    @Autowired
    private UserRepository userRepository;

    public void saveUser(RegisterUser user) {
        userRepository.save(user);
    }

    public RegisterUser findByEmail(String email) {
        // This method checks if the user already exists in the database by email.
        // If the user exists, return the user object.
        // Otherwise, return null or throw an exception.
        RegisterUser existingUser = userRepository.findByEmail(email);
        return existingUser;
    }

    public RegisterUser loginUser(String email, String password) {
        // This method checks if the user exists and if the password is correct.
        // If the user exists and the password is correct, return the user object.
        // Otherwise, return null or throw an exception.
        RegisterUser registerUser = userRepository.registerUser(email, password);
        return registerUser;

    }

    public void deleteUserById(Long id) {
        userRepository.deleteById(id);
    }

    public RegisterUser getUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found with ID: " + id));
    }

    public List<RegisterUser> getAllUsers() {
        // This method retrieves all users from the database.
        return userRepository.findAll();
    }

    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }


}

