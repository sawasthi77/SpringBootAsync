package net.javaguides.springboot.springbootasyncexample.service;

import net.javaguides.springboot.springbootasyncexample.model.User;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.concurrent.CompletableFuture;

@Service
public class GithubLookupService {

    private static final Logger logger = LoggerFactory.getLogger(GithubLookupService.class);
    private final RestTemplate restTemplate;


    public GithubLookupService(RestTemplateBuilder restTemplateBuilder) {
        this.restTemplate = restTemplateBuilder.build();
    }

    @Async("threadPoolTaskExecutor")
    public CompletableFuture<User> findUser(String user) throws InterruptedException {
        logger.info("Looking up for " + user);
        String url = String.format("https://api.github.com/users/%s", user);
        User results = restTemplate.getForObject(url,User.class);
        Thread.sleep(1000L);
        return CompletableFuture.completedFuture(results);
    }
}
