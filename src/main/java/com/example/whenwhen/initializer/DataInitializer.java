package com.example.whenwhen.initializer;

import com.example.whenwhen.entity.User;
import com.example.whenwhen.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DataInitializer implements ApplicationRunner {

    private final UserRepository userRepository;

    @Override
    public void run(ApplicationArguments args) {
        if (!userRepository.existsByKakaoSub("0")) {
            User defaultUser = User.builder()
                    .kakaoSub("0")
                    .accountName("default")
                    .build();
            userRepository.save(defaultUser);
        }
    }
}
