package com.svarto.sitespringredis.controller;

import com.svarto.sitespringredis.Product;
import com.svarto.sitespringredis.Response;
import com.svarto.sitespringredis.User;
import com.svarto.sitespringredis.services.ProductService;
import com.svarto.sitespringredis.services.ResponseService;
import com.svarto.sitespringredis.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
@Controller
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final ProductService productService;
    private final ResponseService responseService;

    @GetMapping("/login")
    public String login(Principal principal, Model model) {
        model.addAttribute("user", userService.getUserByPrincipal(principal));
        return "login";
    }

    @GetMapping("/registration")
    public String registration(Principal principal, Model model) {
        model.addAttribute("user", userService.getUserByPrincipal(principal));
        return "registration";
    }


    @PostMapping("/registration")
    public String createUser(@ModelAttribute("user") User user, Model model) {
        if (!userService.createUser(user)) {
            model.addAttribute("errorMessage", "Пользователь с таким email уже существует");
            return "registration";
        }
        return "redirect:/login";
    }

    @GetMapping("/user/{user}")
    public String userinfo(@PathVariable("user") User user, Model model, Principal principal) {
        model.addAttribute("user", productService.getUserById(user.getId()));
        model.addAttribute("userByPrincipal", userService.getUserByPrincipal(principal));
        model.addAttribute("products", userService.getProductByUser_id(user));

        return "user_info";
    }

    @GetMapping("/profile")
    public String profile(Principal principal, Model model) {
        User user = userService.getUserByPrincipal(principal);
        model.addAttribute("user", user);
        return "profile";
    }

    @GetMapping("/responses")
    public String responses(Principal principal, Model model) {
        User user = userService.getUserByPrincipal(principal);
        List<Response> responses = responseService.getResponsesByUser(user);
        
        // Создаём списки для пользователей и товаров, связанных с откликами
        List<User> customers = new ArrayList<>();
        List<Product> products = new ArrayList<>();
        
        for (Response response : responses) {
            Optional<User> customer = userService.getUserByUser_id(response.getCustomerId());
            User user1 = customer.get();
            Product product = productService.getProductById(response.getPid());
            customers.add(user1);
            products.add(product);
        }
        
        // Добавление атрибутов в model
        model.addAttribute("user", user);
        model.addAttribute("responses", responses);
        model.addAttribute("customers", customers);
        model.addAttribute("products", products);
        
        return "responses";
    }
}
