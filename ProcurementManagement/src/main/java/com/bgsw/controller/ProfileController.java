package com.bgsw.controller;

import com.bgsw.entity.Profile;
import com.bgsw.service.ProfileService;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
@CrossOrigin(origins = "http://localhost:4200")
public class ProfileController {

    @Autowired
    private ProfileService profileService;
    
    @GetMapping("/test")
    public String test() {
        return "Controller is working";
    }

    @GetMapping("/profile")
    public ResponseEntity<Profile> getLoggedInUserProfile() {
    	  System.out.println("✅ Reached /api/user/profile endpoint");
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null && authentication.isAuthenticated()
                && !(authentication.getPrincipal() instanceof String && authentication.getPrincipal().equals("anonymousUser"))) {
            
            Object principal = authentication.getPrincipal();
            String username;

            if (principal instanceof UserDetails userDetails) {
                username = userDetails.getUsername();
            } else {
                username = principal.toString(); // fallback (rare case)
            }

            Optional<Profile> profile = profileService.getByUsername(username);
            return profile.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
        }

        return ResponseEntity.status(401).build(); // 401 Unauthorized
    }

    // ✅ PUT Update profile by ID
    @PutMapping("/profile/{id}")
    public ResponseEntity<Profile> updateProfile(@PathVariable Long id, @RequestBody Profile updatedUser) {
        return profileService.updateUser(id, updatedUser)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}
