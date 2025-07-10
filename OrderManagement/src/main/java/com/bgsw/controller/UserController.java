package com.bgsw.controller;

import com.bgsw.entity.User;
import com.bgsw.repository.UserRepository;
import com.bgsw.service.UserService;
import com.bgsw.util.JWTUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.*;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.Optional;

@RestController
@CrossOrigin(origins = "http://localhost:4200") 
@RequestMapping("/api")
public class UserController {

    @Autowired
    private AuthenticationManager authManager;

    @Autowired
    private JWTUtil jwtUtil;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private UserService userService;


    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> loginData) {
        try {
            String username = loginData.get("username");
            String password = loginData.get("password");

            authManager.authenticate(
                    new UsernamePasswordAuthenticationToken(username, password)
            );

            UserDetails userDetails = userDetailsService.loadUserByUsername(username);

            // ✅ Fetch the full User entity
            Optional<User> userOptional = userRepository.findByUsername(username);
            if (userOptional.isEmpty()) {
                return ResponseEntity.status(401).body(Map.of("error", "User not found"));
            }

            User user = userOptional.get();

            // ✅ Update last login time
            user.setLast_login(LocalDateTime.now());
            userRepository.save(user);

            // ✅ Now generate token using actual userId
            String token = jwtUtil.generateToken(userDetails, user.getUser_id());

            return ResponseEntity.ok(Map.of("token", token));

        } catch (UsernameNotFoundException e) {
            return ResponseEntity.status(401).body(Map.of("error", "User not found"));
        } catch (BadCredentialsException e) {
            return ResponseEntity.status(401).body(Map.of("error", "Invalid credentials"));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body(Map.of("error", "Something went wrong"));
        }
    }

    
    @PostMapping("/adduser")
    public ResponseEntity<?> createUser(@RequestBody User user) {
        try {
            if (user.getRole() != null) {
                System.out.println("Received roleId: " + user.getRole().getRole_id());
            } else {
                System.out.println("No role provided in request.");
            }

            Optional<User> createdUser = userService.createUser(user);

            if (createdUser.isEmpty()) {
                return ResponseEntity.badRequest().body(Map.of("error", "Username already exists"));
            }

            return ResponseEntity.ok(createdUser.get());
        } catch (Exception e) {
            e.printStackTrace(); // To print full stack trace for debugging
            return ResponseEntity.status(500).body(Map.of("error", "Unable to create user"));
        }
    }
    
    @GetMapping("/getallusers")
    public ResponseEntity<?> getAllUsers() {
        try {
            return ResponseEntity.ok(userService.getAllUsers());
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body(Map.of("error", "Unable to fetch users"));
        }
    }
    
    @DeleteMapping("/deleteuser/{id}")
    public void deleteUser(@PathVariable Long id) {
    	userService.deleteUser(id);
    }
    
    @PutMapping("/updateuser/{id}")
    public ResponseEntity<User> updateUser(@PathVariable Long id, @RequestBody User updatedUser) {
        User user = userService.updateUser(id, updatedUser);
        return ResponseEntity.ok(user);
    }
    
}

