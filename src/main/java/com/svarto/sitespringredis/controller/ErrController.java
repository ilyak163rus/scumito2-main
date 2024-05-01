package com.svarto.sitespringredis.controller;

import com.svarto.sitespringredis.User;
import com.svarto.sitespringredis.services.ProductService;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;

@Controller
public class ErrController implements ErrorController {
    @Autowired
    private  ProductService productService;

    @RequestMapping("/error")
    public String handleError(HttpServletRequest request, Model model, Principal principal){
        model.addAttribute("user", productService.getUserByPrincipal(principal));
        Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);

        if(status != null && Integer.valueOf(status.toString()) == HttpStatus.NOT_FOUND.value()){
            System.out.println("Errror!!!!!!");
            return "404";
        }

        return "error";
    }

    @PostMapping("/")
    public String goBack(@ModelAttribute("user") User user, Model model) {
        return "redirect:/";
    }

}
