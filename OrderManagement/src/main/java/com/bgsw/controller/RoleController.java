package com.bgsw.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bgsw.entity.Role;
import com.bgsw.repository.RoleRepository;

//RoleController.java
@RestController
@RequestMapping("/api/roles")
@CrossOrigin(origins = "http://localhost:4200")
public class RoleController {

 @Autowired
 private RoleRepository roleRepository;

 @GetMapping
 public List<Role> getAllRoles() {
     return roleRepository.findAll();
 }
}
