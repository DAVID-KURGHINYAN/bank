package com.spring.bank.repositories;

import com.spring.bank.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;



public interface UserRepo extends JpaRepository<User, Integer> {

    User findByUsername(String username);
    User findByid(Integer id);


}
