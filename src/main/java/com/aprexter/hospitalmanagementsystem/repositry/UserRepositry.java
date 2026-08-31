package com.aprexter.hospitalmanagementsystem.repositry;

import com.aprexter.hospitalmanagementsystem.models.User;
import com.aprexter.hospitalmanagementsystem.models.type.AuthProviderType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepositry extends JpaRepository<User,Long> {
    Optional<User> findByUsername(String username);

    Optional<User> findByProviderIdAndProviderType(String providerId, AuthProviderType providerType);
}
