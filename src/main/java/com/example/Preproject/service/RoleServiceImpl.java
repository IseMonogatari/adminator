package com.example.Preproject.service;

import com.example.Preproject.model.Role;
import com.example.Preproject.repository.RolesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class RoleServiceImpl implements RoleService {

    @Autowired
    private RolesRepository roleRepository;

    @Override
    public Role getRoleByName(String role) {
        return roleRepository.findByRole(role);
    }

    @Override
    public List<String> namesOfAllRoles() {
        return roleRepository.findAll()
                .stream()
                .map(Role::getRole)
                .collect(Collectors.toList());
    }

    @Override
    public List<Role> allRoles() {
        return roleRepository.findAll();
    }

    @Override
    public Role adminRole() {
        return roleRepository.findByRole("ROLE_ADMIN");
    }

    @Override
    public Role userRole() {
        return roleRepository.findByRole("ROLE_USER");
    }
}
