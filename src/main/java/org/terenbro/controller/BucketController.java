package org.terenbro.controller;

import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.terenbro.config.Constants;
import org.terenbro.database.entity.Dish;
import org.terenbro.database.entity.Ingredient;
import org.terenbro.database.service.DishService;
import org.terenbro.telegramBot.TelegramBot;
import org.terenbro.telegramBot.util.BotUtils;

import java.util.ArrayList;
import java.util.List;

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

        for (Ingredient ingredient : dishService.getIngredientsByDishIds(dishesIds)) {
            messageText.append(ingredient.getName()).append(": ").append(ingredient.getQuantity()).append(" ").append(ingredient.getQuantityName()).append(";");
        }

        telegramBot.sendQueue.add(BotUtils.generateMessage(Constants.KATE_USERID, messageText.toString()));
    }
}
