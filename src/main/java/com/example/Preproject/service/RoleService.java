package com.example.Preproject.service;


import com.example.Preproject.model.Role;

import java.util.List;

public interface RoleService {
    Role getRoleByName(String role);
    List<String> namesOfAllRoles();
    List<Role> allRoles();
    Role adminRole();
    Role userRole();
}
