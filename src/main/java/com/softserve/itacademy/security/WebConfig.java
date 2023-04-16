package com.softserve.itacademy.security;

import com.softserve.itacademy.model.ToDo;
import com.softserve.itacademy.model.User;
import com.softserve.itacademy.repository.RoleRepository;
import com.softserve.itacademy.repository.ToDoRepository;
import com.softserve.itacademy.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.time.LocalDateTime;
import java.util.List;

@Configuration
public class WebConfig implements WebMvcConfigurer{
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/form-login");
    }

    @Bean
    public CommandLineRunner dataLoader(UserRepository userRepo, RoleRepository roleRepo, ToDoRepository toDoRepository, PasswordEncoder encoder) {
        return args -> {

            ToDo toDo1 = new ToDo();
            toDo1.setId(55L);
            toDo1.setTitle("Leo's todo");
            toDo1.setCreatedAt(LocalDateTime.now());

            ToDo toDo2 = new ToDo();
            toDo2.setId(56);
            toDo2.setTitle("Cris's todo");
            toDo2.setCreatedAt(LocalDateTime.now());

            User user = new User();
            user.setFirstName("Leo");
            user.setLastName("Messi");
            user.setEmail("user@gmail.com");
            user.setPassword(encoder.encode("password"));
            user.setRole(roleRepo.getReferenceById(2L));
            user.setMyTodos(List.of(toDo1));
            userRepo.save(user);

            User admin = new User();
            admin.setFirstName("Cristiano");
            admin.setLastName("Ronaldo");
            admin.setEmail("admin@gmail.com");
            admin.setPassword(encoder.encode("password"));
            admin.setRole(roleRepo.getReferenceById(1L));
            admin.setMyTodos(List.of(toDo2));
            userRepo.save(admin);

            toDo1.setOwner(user);
            toDoRepository.save(toDo1);
            toDo2.setOwner(admin);
            toDoRepository.save(toDo2);
        };
    }
}
