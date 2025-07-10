package com.bgsw.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import com.bgsw.entity.Profile;

public interface ProfileRepository extends JpaRepository<Profile, Long> {
    Optional<Profile> findByUsername(String username);
}
