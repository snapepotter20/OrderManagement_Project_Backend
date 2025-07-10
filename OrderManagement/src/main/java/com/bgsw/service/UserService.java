package com.bgsw.service;

import com.bgsw.entity.Role;
import com.bgsw.entity.User;
import com.bgsw.repository.RoleRepository;
import com.bgsw.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.*;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private RoleRepository roleRepository;

    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    // Load user by username for login
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username)
                      .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        
        String rawRoleName = user.getRole().getRoleName(); // e.g., "Procurement Officer"
        String normalizedRole = rawRoleName.trim().toUpperCase().replace(" ", "_");
        if (!normalizedRole.startsWith("ROLE_")) {
            normalizedRole = "ROLE_" + normalizedRole;
        }

        System.out.println("User: " + username + ", Normalized Role: " + normalizedRole);

        return new org.springframework.security.core.userdetails.User(
                user.getUsername(),
                user.getPassword(),
                Collections.singletonList(new SimpleGrantedAuthority(normalizedRole))
        );
    }


    // Create user method
    public Optional<User> createUser(User user) {
        if (userRepository.findByUsername(user.getUsername()).isPresent()) {
            return Optional.empty(); // Username already exists
        }
        Role role = roleRepository.findById(user.getRole().getRole_id())
                .orElseThrow(() -> new RuntimeException("Role not found"));

user.setRole(role);

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setIsActive(true);
        user.setLast_login(null); // or LocalDateTime.now()

        return Optional.of(userRepository.save(user));
    }
    
    public List<User> getAllUsers(){
    	return userRepository.findAll();
    }

	public void deleteUser(Long id) {
		
		userRepository.deleteById(id);
	}

	public User updateUser(Long id, User updatedUser) {
	    User existingUser = userRepository.findById(id)
	        .orElseThrow(() -> new RuntimeException("User not found with id: " + id));

	    existingUser.setUsername(updatedUser.getUsername());
	    existingUser.setPassword(passwordEncoder.encode(updatedUser.getPassword()));
	    existingUser.setEmail(updatedUser.getEmail());

	    // Fetch and set role properly
	    Long roleId = updatedUser.getRole().getRole_id();
	    Role persistentRole = roleRepository.findById(roleId)
	        .orElseThrow(() -> new RuntimeException("Role not found with id: " + roleId));
	    
	    existingUser.setRole(persistentRole);

	    return userRepository.save(existingUser);
	}
	
    public boolean updatePasswordByEmail(String email, String newPassword) {
        User user = userRepository.findByEmail(email);
        if (user != null) {
            user.setPassword(passwordEncoder.encode(newPassword));
            userRepository.save(user);
            return true;
        }
        return false;
    }
}
