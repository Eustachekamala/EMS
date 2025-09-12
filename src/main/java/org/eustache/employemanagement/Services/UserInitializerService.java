package org.eustache.employemanagement.Services;

import org.eustache.employemanagement.DAOs.RoleRepository;
import org.eustache.employemanagement.DAOs.UserRepository;
import org.eustache.employemanagement.models.Role;
import org.eustache.employemanagement.models.RoleType;
import org.eustache.employemanagement.models.User;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

@Component
public class UserInitializerService {

    private final PasswordEncoder passwordEncoder;

    public UserInitializerService(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Bean
    public CommandLineRunner commandLineRunner(UserRepository userRepository,
                                               RoleRepository roleRepository) {
        return args -> {
            // ✅ Create roles if not exist
            for (RoleType roleType : RoleType.values()) {
                roleRepository.findByName(roleType);
            }

            // ✅ Create admin user if not exist
            if (userRepository.findByUsername("admin").isEmpty()) {
                Role adminRole = roleRepository.findByName(RoleType.ADMIN)
                        .orElseGet(() -> roleRepository.save(new Role(RoleType.ADMIN)));

                User admin = new User();
                admin.setUsername("admin");
                admin.setEmail("admin@example.com");
                admin.setPassword(passwordEncoder.encode("admin123"));
                admin.setActive(true);

                Set<Role> roles = new HashSet<>();
                roles.add(adminRole);
                admin.setRoles(roles);

                userRepository.save(admin);
                System.out.println("✅ Admin user created: username=admin, password=admin123");
            }
        };
    }
}
