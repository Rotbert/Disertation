package ism.ase.ro.SecurityAnalysis.Controller;

import ism.ase.ro.SecurityAnalysis.Entity.Account;
import ism.ase.ro.SecurityAnalysis.Entity.User;
import ism.ase.ro.SecurityAnalysis.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    private UserService userService;


    //***************************************************************************
    // Basic REST
    @PostMapping
    public User createUser(@RequestBody User user) {
        return userService.createUser(user);
    }

    @GetMapping("/{id}")
    public User getUserById(@PathVariable Long id) {
        return userService.getUserById(id);
    }

    @CrossOrigin
//    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/all")
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    @CrossOrigin()
    @PutMapping("/{id}")
    public User updateUser(@PathVariable Long id, @RequestBody User updatedUser) {
        return userService.updateUser(id, updatedUser);
    }

    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
    }
    //***************************************************************************






    // I
    //***************************************************************************
    // SQL Injection
    @GetMapping("/search")
    public List<User> searchUsers(@RequestParam boolean isSafe, @RequestParam String username) {
        return userService.searchUsers(isSafe, username);
    }
    //***************************************************************************






    // II
    //***************************************************************************
    // Sensitive Data Exposure
    @GetMapping
    public List<User> getAllUsers(@RequestParam boolean isSafe) {
        return userService.getAllUsers(isSafe);
    }
    //***************************************************************************






    // III
    //***************************************************************************
    // Broken Access Control
    @GetMapping("/accounts")
    public List<Account> getAccounts(@RequestParam boolean isSafe, @RequestParam Long userId) {
        return userService.getAccounts(isSafe, userId);
    }
    //***************************************************************************






    // IV
    //***************************************************************************
    // Insecure Deserialization
    @PostMapping("/deserialize")
    public String deserializeUser(@RequestParam boolean isSafe, @RequestBody String encodedData) {
        return userService.deserializeUser(isSafe, encodedData);
    }
    //***************************************************************************
}