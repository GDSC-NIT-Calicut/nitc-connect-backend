package com.gdsc.nitcconnect;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import io.github.cdimascio.dotenv.Dotenv;

import java.util.Objects;

@SpringBootApplication
public class NitcConnectApplication {


    static {
        Dotenv dotenv = Dotenv.load();
        System.setProperty("DB_URL", Objects.requireNonNull(dotenv.get("DB_URL")));
        System.setProperty("DB_USERNAME", Objects.requireNonNull(dotenv.get("DB_USERNAME")));
        System.setProperty("DB_PASSWORD", Objects.requireNonNull(dotenv.get("DB_PASSWORD")));
    }

    public static void main(String[] args) {
        SpringApplication.run(NitcConnectApplication.class, args);
        System.out.println("Here");
    }

}
