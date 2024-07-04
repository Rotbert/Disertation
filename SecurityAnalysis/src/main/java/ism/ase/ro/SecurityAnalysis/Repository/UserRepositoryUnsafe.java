package ism.ase.ro.SecurityAnalysis.Repository;

import ism.ase.ro.SecurityAnalysis.Entity.User;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class UserRepositoryUnsafe {

    @Autowired
    private EntityManager entityManager;

    // SQL Injection
    // Method used to execute a raw SQL query based on user input
    public List<User> searchByUsername(String username) {
        String sql = "SELECT * FROM Users WHERE username = '" + username + "'"; // Vulnerable SQL
        Query query = entityManager.createNativeQuery(sql, User.class);
        return query.getResultList();
    }
}
