package avada.media.myhouse24_admin.data;

import avada.media.myhouse24_admin.model.systemSettings.extra.Permission;
import avada.media.myhouse24_admin.model.systemSettings.pages.Role;
import avada.media.myhouse24_admin.model.systemSettings.pages.Staff;
import avada.media.myhouse24_admin.repo.RoleRepo;
import avada.media.myhouse24_admin.service.StaffService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.stream.Collectors;

@Component
@Slf4j
@RequiredArgsConstructor
public class Init implements CommandLineRunner {

    private final RoleRepo roleRepo;
    private final StaffService staffService;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        log.info("Initialization checks");
        log.info("Checking for the roles");
        if (roleRepo.findAll().isEmpty()) {
            log.warn("No roles have been found");
            log.info("Creating initial roles");
            roleRepo.save(new Role("ROLE_DIRECTOR", "Директор", Arrays.stream(Permission.values()).collect(Collectors.toSet())));
            roleRepo.save(new Role("ROLE_MANAGER", "Управляющий", Arrays.stream(Permission.values()).collect(Collectors.toSet())));
            roleRepo.save(new Role("ROLE_ACCOUNTANT", "Бухгалтер", Arrays.stream(Permission.values()).collect(Collectors.toSet())));
            roleRepo.save(new Role("ROLE_ELECTRICIAN", "Электрик", Arrays.stream(Permission.values()).collect(Collectors.toSet())));
            roleRepo.save(new Role("ROLE_PLUMBER", "Сантехник", Arrays.stream(Permission.values()).collect(Collectors.toSet())));
            log.info("Initial roles successfully created");
        } else log.info("Initial roles have been found");

        log.info("Checking for the ROOT staff");
        if (staffService.getStaffByEmail("root@gmail.com") == null) {
            log.warn("ROOT staff has not been found");
            log.info("Creating initial ROOT staff");
            staffService.createStaff(
                    new Staff("root@gmail.com",
                            passwordEncoder.encode("root"),
                            "Administrator",
                            "Super",
                            null,
                            roleRepo.getRoleByName("ROLE_DIRECTOR"),
                            Staff.Status.ACTIVE));
            log.info("ROOT staff successfully created");
        } else log.info("ROOT staff has been found");
        log.info("Credentials of root for testing purposes:");
        log.info("Login: root@gmail.com");
        log.info("Password: root");

    }

}
