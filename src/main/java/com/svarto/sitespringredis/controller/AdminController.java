package com.svarto.sitespringredis.controller;

import com.svarto.sitespringredis.Category;
import com.svarto.sitespringredis.Role;
import com.svarto.sitespringredis.User;
import com.svarto.sitespringredis.repos.CategoryRepository;
import com.svarto.sitespringredis.services.CategoryService;
import com.svarto.sitespringredis.services.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.support.atomic.RedisAtomicLong;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;
import java.util.List;
import java.util.Map;

@Controller
@RequiredArgsConstructor
@PreAuthorize("hasAuthority('ROLE_ADMIN')")
@Slf4j
public class AdminController {
    private final UserService userService;
    @Autowired
    private RedisAtomicLong idGenerator;
    private final CategoryService categoryService;

    @GetMapping("/admin")
    public String admin(Model model, Principal principal){
        List<Category> categories = categoryService.list();
        model.addAttribute("users", userService.list());
        model.addAttribute("user", userService.getUserByPrincipal(principal));
        model.addAttribute("categories", categories);
        /*model.addAttribute("category", categoryService.getCategoryByPrincipal(principal));*/
        return "admin";
    }

    @PostMapping("/admin/user/ban/{id}")
    public String userBan(@PathVariable("id") Long id){
        userService.banUser(id);
        return "redirect:/admin";
    }

    @GetMapping("/admin/user/edit/{user}")
    public String userEdit(@PathVariable("user") User user, Model model, Principal principal){
        model.addAttribute("user", user);
        model.addAttribute("user", userService.getUserByPrincipal(principal));
        model.addAttribute("roles", Role.values());
        return "user_edit";
    }
    @PostMapping("/admin/user/edit")
    public String category(@RequestParam("userId") User user, @RequestParam Map<String, String> form) {
        userService.changeUserRoles(user, form);
        return "redirect:/admin";
    }


    @PostMapping("/admin/category/add/")
    public String categoryEdit(String name){
        Category category1 = new Category();
        Long id = idGenerator.incrementAndGet();
        category1.setId(id);
        category1.setName(name);
        categoryService.addCategory(category1);
        System.out.println("Add new category with " + category1);
        log.info("Add new category with " + category1);
        return "redirect:/admin";
    }

    @PostMapping("/admin/category/delete/{id}")
    public String categoryDelete(@PathVariable("id") Long id ){
        categoryService.deleteCategoryById(id);
        return "redirect:/admin";
    }

    @GetMapping("/admin/category/edit/{id}")
    public String categoryEdit(@PathVariable("id")Long id, User user,  Principal principal, Model model){
        model.addAttribute("category", categoryService.getCategoryById(id));
        model.addAttribute("user", user);
        model.addAttribute("user", userService.getUserByPrincipal(principal));
        return "category_edit";
    }

   /* @PostMapping("/admin/category/edit/{category}")
    public String categoryDelete(@PathVariable("category") Category category){
        categoryService.editCategory(category);
        return "redirect:/admin";
    }*/
}
