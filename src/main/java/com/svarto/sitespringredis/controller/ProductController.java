package com.svarto.sitespringredis.controller;

import com.svarto.sitespringredis.Category;
import com.svarto.sitespringredis.Product;
import com.svarto.sitespringredis.User;
import com.svarto.sitespringredis.services.CategoryService;
import com.svarto.sitespringredis.services.ProductService;
import com.svarto.sitespringredis.services.ResponseService;
import com.svarto.sitespringredis.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.Principal;
import java.util.Base64;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
public class ProductController {
    @Autowired
    private final ProductService productService;
    @Autowired
    private final UserService userService;

    @Autowired
    private final CategoryService categoryService;
    @Autowired
    private ResponseService responseService;

    @GetMapping("/")
    public String products(@RequestParam(name = "searchWord", required = false) String title, Principal principal,
            Model model) {
        System.out.println(title);
        model.addAttribute("products", productService.list(title));
        model.addAttribute("user", productService.getUserByPrincipal(principal));
        model.addAttribute("searchWord", title);
        return "index";
    }

    @GetMapping("/product/{id}")
    public String productInfo(@PathVariable Long id, Model model, Principal principal) {
        Product product = productService.getProductById(id);
        Optional<Category> optionalCategory = (Optional<Category>) categoryService.getCategoryById(Long.valueOf(product.getCategory_id()));
        if(optionalCategory.isPresent()){
            Category category = optionalCategory.get();
            model.addAttribute("category", category);
        }
        else{
            // Обработка случая, когда категория была не указана
            Category category = new Category((long) -1, "не найдено" );
            model.addAttribute("category", category);
        }

        model.addAttribute("user", productService.getUserByPrincipal(principal));
        model.addAttribute("product", product);
        model.addAttribute("authorProduct", product.getUser());
        return "index_info";
    }

    public String makeResponse(@PathVariable("id") Long id, Principal principal) {
        Product product = productService.getProductById(id);
        String message = "bad idea";
        System.out.println(message);
        responseService.makeResponse(message, principal, product);
        return "index_info";
    }
    @PostMapping("/product/create")
public String createProduct(@RequestParam("title") String title,
                            @RequestParam("price") int price,
                            @RequestParam("city") String city,
                            @RequestParam("description") String description,
                            @RequestParam("image") MultipartFile image,
                            @RequestParam("Category_id") int  category_id,
                            Principal principal) {
    Product product = new Product();
    product.setTitle(title);
    product.setPrice(price);
    product.setCity(city);
    product.setDescription(description);
    product.setCategory_id(category_id);

    if (!image.isEmpty()) {
        try {
            String imageBase64 = Base64.getEncoder().encodeToString(image.getBytes());
            product.setImage(imageBase64);
        } catch (IOException e) {
            System.out.println("Error processing image");
            e.printStackTrace();
        }
    }

    productService.saveProduct(principal, product);
    return "redirect:/";
}

    @PostMapping("/product/delete/{id}")
    public String deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
        return "redirect:/my/products";
    }

    @GetMapping("/my/products")
    public String userProducts(Principal principal, Model model) {
        User user = productService.getUserByPrincipal(principal);
        model.addAttribute("user", user);
        model.addAttribute("categories", categoryService.list());
        model.addAttribute("products", userService.getProductByUser_id(user));
        return "my_products";
    }

}
