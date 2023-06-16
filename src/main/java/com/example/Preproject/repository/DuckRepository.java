package com.example.Preproject.repository;

import com.example.Preproject.model.Duck;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DuckRepository extends JpaRepository<Duck, Integer> {
    Duck findDuckById(Integer userId);
}
