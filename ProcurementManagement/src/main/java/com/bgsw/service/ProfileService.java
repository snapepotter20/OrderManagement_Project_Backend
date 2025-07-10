package com.bgsw.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bgsw.entity.Profile;
import com.bgsw.repository.ProfileRepository;

@Service
public class ProfileService {

    @Autowired
    private ProfileRepository profileRepository;

    public Optional<Profile> getByUsername(String username) {
        return profileRepository.findByUsername(username);
    }

    public Optional<Profile> updateUser(Long id, Profile updatedProfile) {
        return profileRepository.findById(id).map(profile -> {
            profile.setUsername(updatedProfile.getUsername());
            profile.setEmail(updatedProfile.getEmail());
            profile.setPhone(updatedProfile.getPhone());
            return profileRepository.save(profile);
        });
    }
}
