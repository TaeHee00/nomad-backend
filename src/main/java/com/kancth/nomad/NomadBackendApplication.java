package com.kancth.nomad;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class NomadBackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(NomadBackendApplication.class, args);
    }

}
