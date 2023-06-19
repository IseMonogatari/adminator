package com.example.Preproject;

import com.example.Preproject.model.Role;
import com.example.Preproject.repository.RolesRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.scheduling.annotation.EnableScheduling;


@SpringBootApplication
@EnableScheduling
public class Application {
	public static void main(String[] args) {

		// Для первого запуска необходимо этот код раскомментить, а последнюю строчку закоментить

		ConfigurableApplicationContext context = SpringApplication.run(Application.class, args);
		RolesRepository roleRepository = context.getBean(RolesRepository.class);

		roleRepository.save(new Role("ROLE_ADMIN"));
		roleRepository.save(new Role("ROLE_USER"));

//		SpringApplication.run(Application.class, args);
	}

}
