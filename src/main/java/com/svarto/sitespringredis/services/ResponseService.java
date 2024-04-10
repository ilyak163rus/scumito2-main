package com.svarto.sitespringredis.services;

import java.security.Principal;
import java.time.ZonedDateTime;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
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
    // public ResponseService(ResponseRepository responseRepository) {
    // this.responseRepository = responseRepository;
    // }

    // public List<Response> getResponseByProductId(Long id) {
    // return responseRepository.findByProductId(id);
    // }

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
        // log.info("Saving new Response with Customer Id: " + user.getId() + " message:
        // " + message + " on product:"+ product);

        response.setAb(user.getId());
        response.setProductId(product.getId());
        System.out.println(product.getId());
        response.setMessage_date(ZonedDateTime.now());
        response.setMessage(message);
        // System.out.println(response.toString());

        responseRepository.save(response);

    }
    // {
    // Response response = new Response();
    // response.setId(1L);
    // response.setCustomerId(1L);
    // response.setProductId(1L);
    // response.setMessage("Test message");
    // response.setMessage_date(ZonedDateTime.now());
    // responseRepository.save(response);
    // }

}
