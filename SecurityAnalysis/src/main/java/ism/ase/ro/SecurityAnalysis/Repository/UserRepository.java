package ism.ase.ro.SecurityAnalysis.Repository;

import ism.ase.ro.SecurityAnalysis.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);
    User findByUsernameAndPassword(String username, String password);

    // Solution for SQL Injection
    // Method used to execute a parametrized SQL query based on user input
    @Query(value = "SELECT u FROM User u WHERE u.username = :username")
    List<User> searchByUsername(@Param("username") String username);
}