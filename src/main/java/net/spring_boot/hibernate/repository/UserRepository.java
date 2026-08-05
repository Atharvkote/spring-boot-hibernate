package net.spring_boot.hibernate.repository;

import net.spring_boot.hibernate.entity.User;
import net.spring_boot.hibernate.enums.AuthProviderType;
import net.spring_boot.hibernate.enums.RoleType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;


public interface UserRepository extends JpaRepository<User, Long> {

    // Finds a user by username.
    // Commonly used during authentication.
    Optional<User> findByUsername(String username);

    // Finds a user using OAuth provider information.
    // Useful for Google, GitHub, or other OAuth logins.
    Optional<User> findByProviderIdAndProviderType(
            String providerId,
            AuthProviderType providerType
    );

    // Returns all users registered using
    // the specified authentication provider.
    @Query("""
            SELECT record
            FROM User record
            WHERE record.providerType = :providerType
            """)
    List<User> findByProviderType(
            @Param("providerType") AuthProviderType providerType
    );

    // Retrieves all users having the specified role.
    // Performs a JOIN on the roles' collection.
    @Query("""
            SELECT record
            FROM User record
            JOIN record.roles role
            WHERE role = :role
            """)
    List<User> findByRole(
            @Param("role") RoleType role
    );

    // Returns users whose number of assigned roles
    // is greater than the specified value.
    // SIZE() is a JPQL function that counts
    // elements in a collection.
    @Query("""
            SELECT record
            FROM User record
            WHERE SIZE(record.roles) > :minRoles
            """)
    List<User> findByRoleCountGreaterThan(
            @Param("minRoles") int minRoles
    );
}