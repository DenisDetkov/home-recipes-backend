package org.terenbro.database.service;

import org.springframework.stereotype.Service;
import org.terenbro.database.entity.Dish;
import org.terenbro.database.entity.Ingredient;
import org.terenbro.database.repository.DishRepository;

import java.util.List;

@Service
public class DishService extends DefaultService<Dish, DishRepository> {
    public List<Dish> findByType(Dish.Type type) {
        return jpaRepository.findByType(type);
    }

    public List<Ingredient> getIngredientsByDishIds(long[] dishIds) {
        return jpaRepository.getIngredientsByDishIds(dishIds);
    }
}
