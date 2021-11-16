package com.spring.bank.services.impl;

import com.spring.bank.HelperUtil;
import com.spring.bank.entities.Transaction;
import com.spring.bank.entities.User;
import com.spring.bank.enums.Role;
import com.spring.bank.repositories.UserRepo;
import com.spring.bank.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Base64;
import java.util.List;
import java.util.Set;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserRepo userRepo;

    @Override
    public User createUser(User user) {
        String encodedString = HelperUtil.base64(user.getPassword());
        user.setPassword(encodedString);
        LocalDate date = LocalDate.now();
        user.setCreatedAt(date);
        user.setRole(Role.USER);
        return userRepo.save(user);
    }

    @Override
    public User getUserByUsername(String username) {
        return userRepo.findByUsername(username);
    }

    @Override
    public User getUserById(Integer userId) {
        return userRepo.findByid(userId);
    }

    @Override
    public User checkLogin(User user) {
        User logUser = getUserByUsername(user.getUsername());
        if (logUser != null) {
            String userPassword = Base64.getEncoder().encodeToString(user.getPassword().getBytes());
            if (userPassword.equals(logUser.getPassword())) {
                // success
                return user;
            } else {
                return null;
            }
        } else {
            return null;
        }
    }

    @Override
    public Set<Transaction> getUserHistory(Integer userId) {
        User user = getUserById(userId);
        if (user.getTransactions() == null) {
            return null;
        } else {
            Set<Transaction> set = user.getTransactions();
            for (Transaction tr : set){
                tr.setUser(null);
            }
            return set;
        }
    }

    @Override
    public User changeRoleOfUser(Integer loggedId, User toBeChangedUser) {
        User loggedUser = userRepo.findByid(loggedId);
        if (loggedUser.getRole().name().equals("ADMIN")) {
            User userToChangeRole = userRepo.findByUsername(toBeChangedUser.getUsername());
            if (userToChangeRole != null) {
                userToChangeRole.setRole(toBeChangedUser.getRole());
                userRepo.save(userToChangeRole);
                return userToChangeRole;
            } else {
                return null;
            }
        }
        else{
            return null;
        }
    }

    @Override
    public List<User> getAllUsers() {
        return userRepo.findAll();
    }
}
