package com.tripmate.tripmate.alram;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.messaging.*;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

@Slf4j
@Service
public class NotificationServiceImpl {

    @Value("${fcm.key.path}")
    private String FCM_PRIVATE_KEY_PATH;

    //
    // 메시징만 권한 설정
    @Value("${fcm.key.scope}")
    private String fireBaseScope;

    private FirebaseApp firebaseApp;

    private FirebaseMessaging firebaseMessaging;
    // fcm 기본 설정 진행
    @PostConstruct
    public void init() {
        try {
            FirebaseOptions options = new FirebaseOptions.Builder()
                    .setCredentials(
                            GoogleCredentials
                                    .fromStream(new ClassPathResource(FCM_PRIVATE_KEY_PATH).getInputStream())
                                    .createScoped(List.of(fireBaseScope)))
                    .build();
            if (FirebaseApp.getApps().isEmpty()) {

                log.info("Firebase application has been initialized");
            }

            FirebaseApp app = FirebaseApp.initializeApp(options);
            this.firebaseMessaging = FirebaseMessaging.getInstance();
        } catch (IOException e) {
            log.error(e.getMessage());
            // spring 뜰때 알림 서버가 잘 동작하지 않는 것이므로 바로 죽임
            throw new RuntimeException(e.getMessage());
        }
    }


    // 알림 보내기
    public void sendByTokenList(String token,String title, String body) throws ExecutionException, InterruptedException {

        // 메시지 만들기
        Message message = Message.builder()
                .putData("time", LocalDateTime.now().toString())
                .setNotification(new Notification(title, body))
                .setToken(token)
                .build();

            this.firebaseMessaging.sendAsync(message).get();

    }
}