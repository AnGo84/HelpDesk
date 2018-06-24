package ua.helpdesk.utils;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * Created by AnGo on 06.09.2017.
 */
public class QuickPasswordEncodingGenerator {
    /**
     * @param args
     */
    public static void main(String[] args) {
        String password = "qwerty";
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        System.out.println(passwordEncoder.encode(password));
    }
}
