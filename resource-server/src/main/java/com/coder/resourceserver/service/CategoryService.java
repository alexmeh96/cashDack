package com.coder.resourceserver.service;

import com.coder.resourceserver.dao.Category;
import com.coder.resourceserver.dao.User;
import com.coder.resourceserver.repo.CategoryRepository;
import com.coder.resourceserver.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private CategoryRepository categoryRepository;

    public User findUser(String email) {
        return userRepository.findByEmail(email).get();
    }

    public List<Category> allCategories(User user) {
        return user.getCategories();
    }

    public Category addCategory(User user, Category category) {
        category.setAuthor(user);
        user.getCategories().add(category);
        return categoryRepository.save(category);
    }

    public Category editCategory(User user, Category newCategory) {
        newCategory.setAuthor(user);
        return categoryRepository.save(newCategory);
    }

    public Category findCategory(Long id) {
        return categoryRepository.findById(id).get();
    }

}
