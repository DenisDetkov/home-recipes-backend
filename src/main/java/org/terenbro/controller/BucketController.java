package org.terenbro.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.terenbro.config.Constants;
import org.terenbro.database.entity.Ingredient;
import org.terenbro.database.service.DishService;
import org.terenbro.telegramBot.TelegramBot;
import org.terenbro.telegramBot.util.BotUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("api/bucket")
public class BucketController {
    @Autowired
    TelegramBot telegramBot;
    @Autowired
    BotUtils botUtils;
    @Autowired
    DishService dishService;

    @PostMapping
    public void applyBucket(@RequestParam long[] dishesIds) {
        StringBuilder messageText = new StringBuilder();

        List<Ingredient> ingredients = dishService.getIngredientsByDishIds(dishesIds);
        Map<String, Map<Ingredient.Type, Integer>> counts = new HashMap<>();


        for (Ingredient ingredient : ingredients) {
            if (!counts.containsKey(ingredient.getName())) counts.put(ingredient.getName(), new HashMap<>());

            Map<Ingredient.Type, Integer> typeCount = counts.get(ingredient.getName());
            if (!typeCount.containsKey(ingredient.getType())) typeCount.put(ingredient.getType(), 0);

            typeCount.put(ingredient.getType(), typeCount.get(ingredient.getType()) + ingredient.getQuantity());
        }

        for (String key : counts.keySet()) {
            Map<Ingredient.Type, Integer> typeCount = counts.get(key);

            for (Ingredient.Type type: typeCount.keySet()) {
                messageText.append(key).append(": ").append(typeCount.get(type)).append(" ").append(type.text).append(";");
            }
        }

        telegramBot.sendQueue.add(BotUtils.generateMessage(Constants.DEVELOPER_USERID, messageText.toString()));
    }
}
