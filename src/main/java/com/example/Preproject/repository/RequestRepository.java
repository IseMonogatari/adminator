package com.example.Preproject.repository;

import com.example.Preproject.model.Request;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RequestRepository extends JpaRepository<Request, Integer> {
    List<Request> findAllByActiveRequest(boolean activeRequest);
    Request findRequestById(Integer userId);
}
