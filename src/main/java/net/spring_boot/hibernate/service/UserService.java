package net.spring_boot.hibernate.service;

import net.spring_boot.hibernate.entity.User;
import net.spring_boot.hibernate.enums.AuthProviderType;
import net.spring_boot.hibernate.enums.RoleType;
import net.spring_boot.hibernate.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    // Only for Query Demonstrations
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public List<User> getUsersByProviderType(AuthProviderType providerType) {
        return userRepository.findByProviderType(providerType);
    }

    public List<User> getUsersByRole(RoleType role) {
        return userRepository.findByRole(role);
    }

    // Only for Query Demonstrations
    public List<User> getUsersByRoleCount(int minRoles) {
        return userRepository.findByRoleCountGreaterThan(minRoles);
    }
}
