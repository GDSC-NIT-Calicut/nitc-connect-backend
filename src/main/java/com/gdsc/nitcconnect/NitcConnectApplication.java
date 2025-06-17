package com.gdsc.nitcconnect;

import io.jsonwebtoken.Jwts;
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
        System.setProperty("OAUTH_CLIENT_ID", Objects.requireNonNull(dotenv.get("OAUTH_CLIENT_ID")));
        System.setProperty("OAUTH_CLIENT_SECRET", Objects.requireNonNull(dotenv.get("OAUTH_CLIENT_SECRET")));
        System.setProperty("JWT_SECRET", Objects.requireNonNull(dotenv.get("JWT_SECRET")));
    }

    public static void main(String[] args) {
        System.out.println("JJWT qweqweqwversion: " + Jwts.class.getPackage().getImplementationVersion());
        SpringApplication.run(NitcConnectApplication.class, args);

        System.out.println("Here");
    }

}
