package com.oscar.toaquiza.kruger;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ApiInventarioVacunacionEmpleadosApplication implements CommandLineRunner {

//	@Autowired
//	private BCryptPasswordEncoder passwordEncoder;

	public static void main(String[] args) {
		SpringApplication.run(ApiInventarioVacunacionEmpleadosApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		String pas = "12345";
		for (int i = 0; i < 3; i++) {
	//		System.out.println(passwordEncoder.encode(pas));
		}
		
	}

}
