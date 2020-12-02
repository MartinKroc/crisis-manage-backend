package com.crisis.management.db;

import com.crisis.management.models.authority.Role;
import com.crisis.management.models.authority.RoleName;
import com.crisis.management.repo.RoleRepo;
import lombok.AllArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
@AllArgsConstructor
public class RoleDbStart implements ApplicationRunner {

    RoleRepo roleRepository;

    @Override
    public void run(ApplicationArguments args) throws Exception {

        if (roleRepository.count() <= 0) {
            try {
                roleRepository.saveAll(Arrays.asList(
                        new Role(RoleName.ROLE_USER),
                        new Role(RoleName.ROLE_EMPLOYEE)));
            } catch (Error e) {
                System.out.println("Bład dodawania roli do bazy danych");
            }
        }
    }
}
