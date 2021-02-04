package com.ymyang;

import com.ymyang.mq.MySink;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.context.annotation.Bean;
import org.springframework.core.ParameterizedTypeReference;

@EnableBinding({ MySink.class })
@EnableDiscoveryClient
@SpringBootApplication
public class ConsumerApplication {

    public static void main(String[] args) {
        SpringApplication.run(ConsumerApplication.class, args);
    }

    @Bean
    public ConsumerCustomRunner customRunner() {
        return new ConsumerCustomRunner();
    }

    @Slf4j
    public static class ConsumerCustomRunner implements CommandLineRunner {

        @Autowired
        private MySink mySink;

        @Override
        public void run(String... args) throws InterruptedException {
            while (true) {
                mySink.input5().poll(m -> {
                    String payload = (String) m.getPayload();
//                    if(payload.contains("0")){
//                        throw new IllegalArgumentException("111111111111111111111111111111111111111111");
//                    }
                    log.info("input5 pull msg: " + payload);
                }, new ParameterizedTypeReference<Object>() {
                });
                Thread.sleep(5_00);
            }
        }

    }
}
