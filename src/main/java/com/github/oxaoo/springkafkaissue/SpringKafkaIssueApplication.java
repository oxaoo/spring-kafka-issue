package com.github.oxaoo.springkafkaissue;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.github.oxaoo.springkafkaissue")
public class SpringKafkaIssueApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringKafkaIssueApplication.class, args);
    }

}
