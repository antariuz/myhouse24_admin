package avada.media.myhouse24_admin.data;

import avada.media.myhouse24_admin.model.Profile;
import avada.media.myhouse24_admin.model.Status;
import avada.media.myhouse24_admin.model.User;
import avada.media.myhouse24_admin.model.common.Seo;
import avada.media.myhouse24_admin.model.systemSettings.extra.Permission;
import avada.media.myhouse24_admin.model.systemSettings.pages.Role;
import avada.media.myhouse24_admin.model.systemSettings.pages.Staff;
import avada.media.myhouse24_admin.model.websiteControl.extra.WebExtraInformation;
import avada.media.myhouse24_admin.model.websiteControl.extra.WebNextToUs;
import avada.media.myhouse24_admin.model.websiteControl.pages.WebAboutUs;
import avada.media.myhouse24_admin.model.websiteControl.pages.WebMain;
import avada.media.myhouse24_admin.repo.ProfileRepo;
import avada.media.myhouse24_admin.repo.RoleRepo;
import avada.media.myhouse24_admin.repo.SeoRepo;
import avada.media.myhouse24_admin.repo.UserRepo;
import avada.media.myhouse24_admin.repo.websiteControl.WebAboutUsRepo;
import avada.media.myhouse24_admin.repo.websiteControl.WebExtraInformationRepo;
import avada.media.myhouse24_admin.repo.websiteControl.WebMainRepo;
import avada.media.myhouse24_admin.repo.websiteControl.WebNextToUsRepo;
import avada.media.myhouse24_admin.service.StaffService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Component
@Slf4j
@RequiredArgsConstructor
@Transactional
public class Init implements CommandLineRunner {

    private final RoleRepo roleRepo;
    private final StaffService staffService;
    private final PasswordEncoder passwordEncoder;
    private final WebMainRepo webMainRepo;
    private final WebAboutUsRepo webAboutUsRepo;
    private final WebNextToUsRepo webNextToUsRepo;
    private final WebExtraInformationRepo webExtraInformationRepo;
    private final SeoRepo seoRepo;
    private final UserRepo userRepo;
    private final ProfileRepo profileRepo;

    @Override
    public void run(String... args) throws Exception {
        log.info("Initialization checks");
        log.info("Checking for the roles");
        if (roleRepo.findAll().isEmpty()) {
            log.info("No roles has been found");
            log.info("Creating initial roles");
            roleRepo.save(new Role("ROLE_DIRECTOR", "Директор", Arrays.stream(Permission.values()).collect(Collectors.toSet())));
            roleRepo.save(new Role("ROLE_MANAGER", "Управляющий", Arrays.stream(Permission.values()).collect(Collectors.toSet())));
            roleRepo.save(new Role("ROLE_ACCOUNTANT", "Бухгалтер", Arrays.stream(Permission.values()).collect(Collectors.toSet())));
            roleRepo.save(new Role("ROLE_ELECTRICIAN", "Электрик", Arrays.stream(Permission.values()).collect(Collectors.toSet())));
            roleRepo.save(new Role("ROLE_PLUMBER", "Сантехник", Arrays.stream(Permission.values()).collect(Collectors.toSet())));
            log.info("Initial roles successfully created");
        } else log.info("Initial roles has been found");

        log.info("Checking for the ROOT staff");
        if (staffService.getStaffByEmail("root@gmail.com") == null) {
            log.info("ROOT staff has not been found");
            log.info("Creating initial ROOT staff");
            staffService.createStaff(
                    new Staff("root@gmail.com",
                            passwordEncoder.encode("root"),
                            "root",
                            "root",
                            null,
                            roleRepo.getRoleByName("ROLE_DIRECTOR"),
                            Staff.Status.ACTIVE));
            log.info("ROOT staff successfully created");
        } else log.info("ROOT staff has been found");

        log.info("Checking for the WebMain");
        if (!webMainRepo.findById(1L).isPresent()) {
            log.info("WebMain has not been found");
            log.info("Creating initial WebMain");
            WebMain webMain = new WebMain();
            Seo seo = new Seo();
            webMain.setSeo(seo);
            List<WebNextToUs> webNextToUsList = new ArrayList<>();
            for (int i = 0; i < 6; i++) {
                WebNextToUs webNextToUs = new WebNextToUs();
                webNextToUsList.add(webNextToUs);
                webNextToUsRepo.save(webNextToUs);
            }
            webMain.setWebNextToUsList(webNextToUsList);
            seoRepo.save(seo);
            webMainRepo.save(webMain);
        }

        log.info("Checking for the WebAboutUs");
        if (!webAboutUsRepo.findById(1L).isPresent()) {
            log.info("WebAboutUs has not been found");
            log.info("Creating initial WebAboutUs");
            WebAboutUs webAboutUs = new WebAboutUs();
            Seo seo = new Seo();
            webAboutUs.setSeo(seo);
            seoRepo.save(seo);
            WebExtraInformation webExtraInformation = new WebExtraInformation();
            webAboutUs.setWebExtraInformation(webExtraInformation);
            webExtraInformationRepo.save(webExtraInformation);
            webAboutUsRepo.save(webAboutUs);
        }

        log.info("Checking for the testing Users");
        if (userRepo.findAll().isEmpty()) {
            log.info("Users have not been found");
            log.info("Creating initial Users");
            User user = new User();
            Profile profile = new Profile();
            user.setUniqueId("000001");
            user.setEmail("bobi@gmail.com");
            user.setPassword(passwordEncoder.encode("password"));
            user.setStatus(Status.NEW);
            profile.setFirstname("Bob");
            profile.setMiddleName("SquarePants");
            profile.setLastname("Sponge");
            profile.setPhoneNumber("+380677777777");
            profileRepo.save(profile);
            user.setProfile(profile);
            userRepo.save(user);
            User user2 = new User();
            Profile profile2 = new Profile();
            user2.setUniqueId("000002");
            user2.setEmail("patrick@gmail.com");
            user2.setPassword(passwordEncoder.encode("password"));
            user2.setStatus(Status.ACTIVE);
            profile2.setFirstname("Patrick");
            profile2.setMiddleName("Joseph");
            profile2.setLastname("Star");
            profile2.setPhoneNumber("+380688888888");
            profileRepo.save(profile2);
            user2.setProfile(profile2);
            userRepo.save(user2);
            User user3 = new User();
            Profile profile3 = new Profile();
            user3.setUniqueId("000003");
            user3.setEmail("blob3@gmail.com");
            user3.setPassword(passwordEncoder.encode("password"));
            user3.setStatus(Status.INACTIVE);
            profile3.setFirstname("Mr.");
            profile3.setMiddleName("Banana");
            profile3.setLastname("Crabs");
            profile3.setPhoneNumber("+380633333333");
            profileRepo.save(profile3);
            user3.setProfile(profile3);
            userRepo.save(user3);
        }

    }
}
