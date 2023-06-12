package com.example.Preproject.repository;



import com.example.Preproject.model.Role;
import com.example.Preproject.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UsersRepository extends JpaRepository<User, Integer> {
    User findByName(String name);
    User findUserById(Integer id);
    List<User> findAllByRoles (Role role);
    User findUserByEmail(String email);
}
