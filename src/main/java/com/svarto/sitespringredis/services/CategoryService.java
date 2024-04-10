package com.svarto.sitespringredis.services;

import com.svarto.sitespringredis.Category;
import com.svarto.sitespringredis.repos.CategoryRepository;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@AllArgsConstructor

public class CategoryService {
    @Autowired
    private final CategoryRepository categoryRepository;

    public List<Category> list() {

        return (List<Category>) categoryRepository.findAll();
    }
    public void addCategory (Category category){
        categoryRepository.save(category);
    }

    public void deleteCategoryById(Long id ){ categoryRepository.deleteById(id);

    }
    public Object getCategoryById(Long id){
        return categoryRepository.findById(id);

    }

}
