package ism.ase.ro.SecurityAnalysis.Service;

import ism.ase.ro.SecurityAnalysis.Entity.Account;
import ism.ase.ro.SecurityAnalysis.Entity.User;
import ism.ase.ro.SecurityAnalysis.Helpers.Encoder;
import ism.ase.ro.SecurityAnalysis.Repository.UserRepository;
import ism.ase.ro.SecurityAnalysis.Repository.UserRepositoryUnsafe;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.List;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private AccountService accountService;
    @Autowired
    private UserRepositoryUnsafe userRepositoryUnsafe;


    //***************************************************************************
    // Basic REST
    public User createUser(User user) {
        user.setPassword(Encoder.encode(user.getPassword()));

        return userRepository.save(user);
    }

    public User getUserById(Long id) {
        return userRepository.findById(id).orElse(null);
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User updateUser(Long id, User updatedUser) {
        User user = userRepository.findById(id).orElse(null);
        if (user != null) {
            user.setUsername(updatedUser.getUsername());
            user.setPassword(Encoder.encode(updatedUser.getPassword()));
            user.setEmail(updatedUser.getEmail());
            user.setRole(updatedUser.getRole());
            return userRepository.save(user);
        }
        return null;
    }

    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }
    //***************************************************************************


    // I
    //***************************************************************************
    // SQL Injection
    public List<User> searchUsers(boolean isSafe, String username) {
        if (isSafe) {
            return userRepository.searchByUsername(username);
        }

        // Vulnerable SQL query execution
        return userRepositoryUnsafe.searchByUsername(username);
    }
    //***************************************************************************


    // II
    //***************************************************************************
    // Sensitive Data Exposure
    public List<User> getAllUsers(boolean isSafe) {
        if (isSafe) {
            List<User> users = userRepository.findAll();
            // Ensure passwords are not included in the response
            users.forEach(user -> user.setPassword(null));
            return users;
        }

        return userRepository.findAll();
    }
    //***************************************************************************


    // III
    //***************************************************************************
    // Broken Access Control
    public List<Account> getAccounts(boolean isSafe, Long userId) {
        User user = userRepository.findById(userId).orElse(null);

        try {
            if (null == user) {
                throw new Exception("Invalid user");
            }

            if (isSafe) {
                if ("ADMIN".equals(user.getRole())) {
                    return accountService.getAccounts();
                }

                return null;
            }
        } catch (Exception e) {
            return null;
        }

        return accountService.getAccounts();
    }
    //***************************************************************************






    // IV
    //***************************************************************************
    // Insecure Deserialization
    @PostMapping("/deserialize")
    public String deserializeUser(@RequestParam boolean isSafe, @RequestBody String encodedData) {
        byte[] data = Base64.decodeBase64(encodedData);

        if (isSafe) {
            try (ObjectInputStream ois = new ObjectInputStream(new ByteArrayInputStream(data))) {
                Object obj = ois.readObject();
                if (!(obj instanceof User)) {
                    throw new SecurityException("Unexpected object type");
                }
                User user = (User) obj;
                userRepository.save(user);
                return "User deserialized and saved successfully";
            } catch (IOException | ClassNotFoundException e) {
                return "Failed to deserialize user";
            }
        }

        try (ObjectInputStream ois = new ObjectInputStream(new ByteArrayInputStream(data))) {
            User user = (User) ois.readObject();
            userRepository.save(user);
            return "User deserialized and saved successfully";
        } catch (IOException | ClassNotFoundException e) {
            return "Failed to deserialize user";
        }
    }
    //***************************************************************************
}
