package com.svarto.sitespringredis.services;

import java.security.Principal;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.support.atomic.RedisAtomicLong;
import org.springframework.stereotype.Service;

import com.svarto.sitespringredis.Product;
import com.svarto.sitespringredis.Response;
import com.svarto.sitespringredis.User;
import com.svarto.sitespringredis.repos.ProductRepository;
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
    private ProductRepository productRepository;
    @Autowired
    private  ResponseRepository responseRepository;

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
        response.setPid(product.getId());
        response.setMessage_date(ZonedDateTime.now());
        response.setMessage(message);
        log.info("Savin new response {}", response);

        responseRepository.save(response);

    }
    public List<Response> getResponsesByUser(User user) {
        List<Product> products = productRepository.findByUser_id(user.getId());
        List<Response> allResponses = new ArrayList<>();
        for (Product product : products) {
            System.out.println("=================");
            System.out.println(product.getId());
            System.out.println(responseRepository.findByPid(3L));
            System.out.println("=================");
            allResponses.addAll(responseRepository.findByPid(product.getId()));
            
        }
        return allResponses;
    }
    
}
