package br.com.SplagProj.util;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class PasswordHashGenerator {
    public static void main(String[] args) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String password = "admin";
        String hashedPassword = encoder.encode(password);
        System.out.println("Senha original: " + password);
        System.out.println("Hash gerado: " + hashedPassword);
    }
} 