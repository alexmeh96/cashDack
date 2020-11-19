package com.coder.resourceserver.controller;

import com.coder.resourceserver.dao.Category;
import com.coder.resourceserver.dao.User;
import com.coder.resourceserver.repo.CategoryRepository;
import com.coder.resourceserver.repo.UserRepository;
import com.coder.resourceserver.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/category")
@CrossOrigin(origins = "*", maxAge = 3600)
public class CategoryController {


    @Autowired
    private CategoryService categoryService;

    @GetMapping
    public List<Category> allCategory(Principal principal) {
        User user = categoryService.findUser(principal.getName());
        return categoryService.allCategories(user);
    }

    @GetMapping("/{id}")
    public Category getCategory(@PathVariable Long id) {
        return categoryService.findCategory(id);
    }

    @PostMapping("/add")
    public Category addCategory(@RequestBody Category category, Principal principal) {
        User user = categoryService.findUser(principal.getName());
        return categoryService.addCategory(user, category);
    }

    @PostMapping("/edit")
    public Category updateCategory(@RequestBody Category category, Principal principal) {
        User user = categoryService.findUser(principal.getName());
        return categoryService.editCategory(user, category);
    }
}
