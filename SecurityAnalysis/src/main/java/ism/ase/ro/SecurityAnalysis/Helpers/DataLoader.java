package ism.ase.ro.SecurityAnalysis.Helpers;

import ism.ase.ro.SecurityAnalysis.Entity.Account;
import ism.ase.ro.SecurityAnalysis.Entity.User;
import ism.ase.ro.SecurityAnalysis.Repository.AccountRepository;
import ism.ase.ro.SecurityAnalysis.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.Arrays;
import java.util.Base64;
import java.util.Random;

@Component
public class DataLoader implements CommandLineRunner {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private AccountRepository accountRepository;

    @Override
    public void run(String... args) throws IOException {
        // Assuming checks to avoid duplicates are in place
        if (userRepository.count() == 0) {
            for (int i = 0; i < 20; i++) {
                User user = new User();
                user.setUsername("user" + i);
                user.setPassword(Encoder.encode("pass" + i));
                user.setEmail("user" + i + "@example.com");
                if (i % 2 == 0) {
                    user.setRole("ADMIN");
                } else {
                    user.setRole("USER");
                }

                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                ObjectOutputStream oos = new ObjectOutputStream(baos);
                oos.writeObject(user);
                oos.close();

//                byte[] userBytes = baos.toByteArray();
//                // Print the serialized userBytes in Base64 format
//                System.out.println(Base64.getEncoder().encodeToString(userBytes));

                userRepository.save(user);
            }
        }

        // Assuming checks to avoid duplicates are in place
        if (accountRepository.count() == 0) {
            for (int i = 0; i < 20; i++) {
                Account account = new Account();
                account.setAccountDetails("details" + i);
                account.setAccountOwner((long) i);
                account.setAccountNumber(getAccountNumber());

                accountRepository.save(account);
            }
        }
    }

    private Integer getAccountNumber() {
        Random random = new Random();
        int min = 100000;
        int max = 999999;
        return random.nextInt((max - min) + 1) + min;
    }
}