package ism.ase.ro.SecurityAnalysis.Service;

import ism.ase.ro.SecurityAnalysis.Entity.Account;
import ism.ase.ro.SecurityAnalysis.Entity.User;
import ism.ase.ro.SecurityAnalysis.Repository.AccountRepository;
import ism.ase.ro.SecurityAnalysis.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AccountService {
    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private UserRepository userRepository;

    public Account createAccount(Account account) {
        try {
            if (isOwnerValid(account.getAccountOwner())) {
                throw new Exception("Invalid account owner!");
            }

            return accountRepository.save(account);
        } catch (Exception e) {
            return null;
        }
    }

    private boolean isOwnerValid(Long accountOwner) {
        if (null == accountOwner) {
            return false;
        }

        User user = userRepository.findById(accountOwner).orElse(null);

        return null != user;
    }

    public Account getAccountById(Long id) {
        return accountRepository.findById(id).orElse(null);
    }

    public Account updateAccount(Long id, Account accountDetails) {
        Account account = accountRepository.findById(id).orElse(null);
        if (account != null) {
            account.setAccountDetails(accountDetails.getAccountDetails());
            accountRepository.save(account);
        }

        return account;
    }

    public void deleteAccount(Long id) {
        accountRepository.deleteById(id);
    }

    public Account getAccountByAccountOwner(Long accountOwner) {
        return accountRepository.getAccountByAccountOwner(accountOwner);
    }

    public List<Account> getAccounts() {
        return accountRepository.findAll();
    }
}
