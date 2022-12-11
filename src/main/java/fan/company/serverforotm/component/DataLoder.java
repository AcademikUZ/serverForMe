package fan.company.serverforotm.component;

import fan.company.serverforotm.entity.Role;
import fan.company.serverforotm.entity.Users;
import fan.company.serverforotm.entity.enums.Huquq;
import fan.company.serverforotm.repository.RoleRepository;
import fan.company.serverforotm.repository.UserRepository;
import fan.company.serverforotm.utils.AppConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

import static fan.company.serverforotm.entity.enums.Huquq.*;

@Component
public class DataLoder implements CommandLineRunner {

    @Autowired
    RoleRepository roleRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    PasswordEncoder passwordEncoder;

    @Value("${spring.sql.init.mode}")
    private String initModeType;


    @Override
    public void run(String... args) throws Exception {

        /**
         * Boshlang'ich userlarni yaratish
         */

        try {

            if (initModeType.equals("always")) {

                if (userRepository.findAll().isEmpty()) {

                    Huquq[] huquqs = Huquq.values();

                    Role adminRole = roleRepository.save(
                            new Role(
                                    AppConstants.ADMIN,
                                    Arrays.asList(huquqs),
                                    "Tizim administratori"
                            )
                    );

                    Role userRole = roleRepository.save(
                            new Role(
                                    AppConstants.USER,
                                    Arrays.asList(SEND_FILE, DELETE_FILE, VIEW_STATISTIKA),
                                    "Oddiy foydalanuvchi"
                            )
                    );

                    userRepository.save(
                            new Users(
                                    "Admin",
                                    "admin",
                                    passwordEncoder.encode("admin"),
                                    adminRole,
                                    null
                            )
                    );

        //            userRepository.save(
        //                    new Users(
        //                            "User",
        //                            "user",
        //                            passwordEncoder.encode("user"),
        //                            userRole,
        //                            null
        //                    )
        //            );

                }


            }

        } catch (Exception e) {

        }


    }
}
