package com.svarto.sitespringredis.services;

import java.security.Principal;
import java.time.ZonedDateTime;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.support.atomic.RedisAtomicLong;
import org.springframework.stereotype.Service;

import com.svarto.sitespringredis.Product;
import com.svarto.sitespringredis.Response;
import com.svarto.sitespringredis.User;
import com.svarto.sitespringredis.repos.ResponseRepository;
import com.svarto.sitespringredis.repos.UserRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
@AllArgsConstructor
public class ResponseService {

    @Autowired
    private RedisAtomicLong idGenerator;

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ResponseRepository responseRepository;

    public void makeResponse(String message, Principal principal, Product product) {
        Response response = new Response();
        String email = principal.getName();
        System.out.println(email);
        User user = userRepository.findByEmail(email);

        if (user == null) {
            System.out.println("No user found for email: " + email);
            return;
        }
        Long newId = idGenerator.incrementAndGet();
        response.setId(newId);

        response.setCustomerId(user.getId());
        response.setProductId(product.getId());
        response.setMessage_date(ZonedDateTime.now());
        response.setMessage(message);

        responseRepository.save(response);

    }

}
