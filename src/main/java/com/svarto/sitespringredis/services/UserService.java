package com.svarto.sitespringredis.services;

import com.svarto.sitespringredis.Product;
import com.svarto.sitespringredis.Role;
import com.svarto.sitespringredis.User;
import com.svarto.sitespringredis.repos.ProductRepository;
import com.svarto.sitespringredis.repos.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.support.atomic.RedisAtomicLong;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserService {
    @Autowired
    private RedisAtomicLong idGenerator;
    @Autowired
    private final UserRepository userRepository;
    @Autowired
    private final ProductRepository productRepository;
    @Autowired
    private final PasswordEncoder passwordEncoder;

    public boolean createUser(User user) {
        String email = user.getEmail();
        Long newId = idGenerator.incrementAndGet();
        user.setActive(true);
        user.setId(newId);
        user.setPassword(passwordEncoder.encode((user.getPassword())));
        user.getRoles().add(Role.ROLE_ADMIN);
        log.info("Saving new User with email: {}", email);
        userRepository.save(user);

        return true;
    }

    public List<User> list() {
        return (List<User>) userRepository.findAll();
    }

    public List<Product> getProductByUser_id(User user) {
        System.out.println("Trying to get product with id" + user.getId() + ":"
                + productRepository.findByUser_id(user.getId()).toString());
        return productRepository.findByUser_id(user.getId());
    }

    public void banUser(Long id) {
        User user = userRepository.findById(id).orElse(null);
        if (user != null) {
            if (user.isActive()) {
                user.setActive(false);
                log.info("Ban user with id = {}; email: {}", user.getId(), user.getEmail());
            } else {
                user.setActive(true);
                log.info("unBan user with id = {}; email: {}", user.getId(), user.getEmail());
            }
        }
        ;
        userRepository.save(user);
    }

    public void changeUserRoles(User user, Map<String, String> form) {
        Set<String> roles = Arrays.stream(Role.values())
                .map(Role::name)
                .collect(Collectors.toSet());
        user.getRoles().clear();

        for (String key : form.keySet()) {
            System.out.println(key);
            if (roles.contains(key)) {
                System.out.println(Role.valueOf(key));
                user.getRoles().add(Role.valueOf(key));
            }
        }
        userRepository.save(user);
    }

    public User getUserByPrincipal(Principal principal) {
        if (principal == null)
            return new User();
        return userRepository.findByEmail(principal.getName());
    }

    public Optional<User> getUserByUser_id(Long id){
        return userRepository.findById(id);
    }
    

}
