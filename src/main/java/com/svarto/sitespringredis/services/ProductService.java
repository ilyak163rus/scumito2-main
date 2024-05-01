package com.svarto.sitespringredis.services;

import com.svarto.sitespringredis.Product;
import com.svarto.sitespringredis.User;
import com.svarto.sitespringredis.repos.ProductRepository;
import com.svarto.sitespringredis.repos.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.support.atomic.RedisAtomicLong;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.*;

@Service
@Slf4j
@RequiredArgsConstructor
public class ProductService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RedisAtomicLong idGenerator;
    @Autowired
    private final ProductRepository productRepository;

    public List<Product> list(String title) {
        if (title != null) {
            return productRepository.findByTitle(title);

        }
        return (List<Product>) productRepository.findAll();
    }

    public void saveProduct(Principal principal, Product product) {
        User user1 = new User(1L, "Test", true, "test@test.com", "123", "01.01.2000", "8937", new ArrayList<>(),
                new HashSet<>());
        Product product1 = new Product(1L, 1L, "Майка летняя", "Автор", 1000, "Самара", user1, "Крутая майка", "ссылка",
                true, 1, false);
        Product product2 = new Product(2L, 1L, "Майка Зимняя", "Автор2", 5000, "Сызрань", user1,
                "Не крутая дорогая зимняя майка", "ссылка2", true, 1, false);
        Product product3 = new Product(3L, 1L, "Шорты весенние", "Автор3", 700, "Тольятти", user1, "Шорты веселые",
                "ссылка3", true, 2, false);

        productRepository.save(product1);
        productRepository.save(product2);
        productRepository.save(product3);

        String email = principal.getName();
        User user = userRepository.findByEmail(email);
        if (user == null) {
            System.out.println("No user found for email: " + email);
            return;
        }

        product.setUser(user);
        product.setUser_id(user.getId());
        Long newId = idGenerator.incrementAndGet();
        product.setId(newId);
        product.setBoost(false);
        log.info("Savin new {}", product);
        productRepository.save(product);
    }

    public User getUserByPrincipal(Principal principal) {
        if (principal == null) {
            return new User();
        }
        return userRepository.findByEmail(principal.getName());
    }

    public void deleteProduct(Long id) {
        productRepository.deleteById(id);
    }

    public Product getProductById(Long id) {
        return productRepository.findById(id).orElse(null);
    }

    public User getUserById(Long id) {
        return userRepository.findById(id).orElse(null);
    }

}
