package ism.ase.ro.SecurityAnalysis.Helpers;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

public class Encoder {

    public static final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public static String encode(String password) {
        return passwordEncoder.encode(password);
    }

    public static boolean validatePassword(String insertedPassword, String userPassword) {
        return passwordEncoder.matches(insertedPassword, userPassword);
    }
}
