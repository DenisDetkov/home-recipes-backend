package org.terenbro.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.terenbro.database.entity.Dish;
import org.terenbro.database.service.DishService;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("api/dish")
public class DishController {
    @Autowired
    DishService dishService;

    @GetMapping
    public List<Dish> getDishesByType(@RequestParam Dish.Type type) {
        return dishService.findByType(type);
    }

    @GetMapping("/list")
    public List<Dish> getDishesByIds(@RequestParam long[] ids) {
        List<Dish> result = new ArrayList<>();

        for (long id : ids) result.add(dishService.getById(id).get());

        return result;
    }

    @PostMapping
    public Dish addDish(@RequestBody Dish dish) {
        dishService.save(dish);
        return dish;
    }

    @PutMapping
    public Dish updateDish(@RequestBody Dish dish) {
        dishService.save(dish);
        return dish;
    }

    @DeleteMapping
    public void deleteDish(@RequestParam long dishId) {
        dishService.deleteById(dishId);
    }
}
