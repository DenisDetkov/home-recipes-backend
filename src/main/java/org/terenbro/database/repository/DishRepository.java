package org.terenbro.database.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.terenbro.database.entity.Dish;
import org.terenbro.database.entity.Ingredient;

import java.util.List;

@Repository
public interface DishRepository extends JpaRepository<Dish, Long> {
    List<Dish> findByType(Dish.Type type);

    @Query(value = "SELECT d.ingredients FROM Dish d WHERE d.id IN (:dishIds)")
    List<Ingredient> getIngredientsByDishIds(long[] dishIds);
}
