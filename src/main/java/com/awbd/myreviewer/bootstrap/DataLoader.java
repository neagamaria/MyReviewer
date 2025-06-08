package com.awbd.myreviewer.bootstrap;
import com.awbd.myreviewer.domain.security.User;
import com.awbd.myreviewer.repositories.security.AuthorityRepository;
import com.awbd.myreviewer.repositories.security.UserRepository;
import lombok.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import com.awbd.myreviewer.domain.security.Authority;

@AllArgsConstructor
@Component
@Profile("mysql")

public class DataLoader implements CommandLineRunner {

    private AuthorityRepository authorityRepository;
    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;


    private void loadUserData() {
        if (userRepository.count() == 0){
            Authority adminRole = authorityRepository.save(Authority.builder().role("ROLE_ADMIN").build());
            Authority reviewerRole = authorityRepository.save(Authority.builder().role("ROLE_REVIEWER").build());
            Authority writerRole = authorityRepository.save(Authority.builder().role("ROLE_WRITER").build());

            User admin = User.builder()
                    .username("admin")
                    .password(passwordEncoder.encode("12345"))
                    .authority(adminRole)
                    .build();

            User reviewer = User.builder()
                    .username("reviewer")
                    .password(passwordEncoder.encode("12345"))
                    .authority(reviewerRole)
                    .build();

            User writer = User.builder()
                    .username("writer")
                    .password(passwordEncoder.encode("12345"))
                    .authority(writerRole)
                    .build();

            admin.setEnabled(true);
            reviewer.setEnabled(true);
            writer.setEnabled(true);
            userRepository.save(admin);
            userRepository.save(reviewer);
            userRepository.save(writer);
        }
    }


    @Override
    public void run(String... args) throws Exception {
        loadUserData();
    }
}
