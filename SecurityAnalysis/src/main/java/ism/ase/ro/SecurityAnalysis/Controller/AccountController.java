package ism.ase.ro.SecurityAnalysis.Controller;

import ism.ase.ro.SecurityAnalysis.Entity.Account;
import ism.ase.ro.SecurityAnalysis.Service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/accounts")
public class AccountController {
    @Autowired
    private AccountService accountService;

    @PostMapping
    public Account createUser(@RequestBody Account account) {
        return accountService.createAccount(account);
    }

    @GetMapping("/{id}")
    public Account getUserById(@PathVariable Long id) {
        return accountService.getAccountById(id);
    }

    @PutMapping("/{id}")
    public Account updateUser(@PathVariable Long id, @RequestBody Account userDetails) {
        return accountService.updateAccount(id, userDetails);
    }

    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable Long id) {
        accountService.deleteAccount(id);
    }
}