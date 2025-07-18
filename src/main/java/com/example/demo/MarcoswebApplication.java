package com.example.demo;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.example.demo.entities.Usuario;
import com.example.demo.repositories.UsuarioRepository;
import com.example.demo.util.RolEnum;

@SpringBootApplication
public class MarcoswebApplication {

	public static void main(String[] args) {
		SpringApplication.run(MarcoswebApplication.class, args);
	}

	@Bean
	CommandLineRunner commandLineRunner(
		UsuarioRepository repository,
		PasswordEncoder encoder
	){
		return args->{
			if(repository.findByCorreo("admin@gmail.com").isEmpty()){
				Usuario usuario = new Usuario();
				usuario.setNombre("Admin");
				usuario.setCorreo("admin@gmail.com");
				usuario.setContrasena(encoder.encode("12345678"));
				usuario.setRol(RolEnum.ADMIN);
				usuario.setEstado("activo");
				repository.save(usuario);
			}
		};
	}

}
