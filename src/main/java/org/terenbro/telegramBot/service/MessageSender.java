package org.terenbro.telegramBot.service;

import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendDocument;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.methods.send.SendSticker;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageReplyMarkup;
import org.telegram.telegrambots.meta.exceptions.TelegramApiRequestException;
import org.terenbro.telegramBot.TelegramBot;


@Component
public class MessageSender implements Runnable {
    @Autowired
    TelegramBot bot;

    private final int SENDER_SLEEP_TIME = 1000;

    @SneakyThrows
    @Override
    public void run() {
        while (true) {
            for (Object object = bot.sendQueue.poll(); object != null; object = bot.sendQueue.poll()) send(object);
            Thread.sleep(SENDER_SLEEP_TIME);
        }
    }

    private void send(Object object) {
        try {
            SendMessage message = (SendMessage) object;
            message.setText(message.getText().replace("_", " "));
            bot.execute(message);
        } catch (TelegramApiRequestException telegramApiRequestException) {
            if (telegramApiRequestException.getApiResponse().contains("chat not found")) return;
        } catch (Exception e) {
        }
    }

    private MessageType messageType(Object object) {
        if (object instanceof SendSticker) return MessageType.STICKER;
        else if (object instanceof EditMessageReplyMarkup) return MessageType.EDIT_MESSAGE_REPLY_MARKUP;
        else if (object instanceof BotApiMethod) return MessageType.EXECUTE;
        else if (object instanceof SendDocument) return MessageType.DOCUMENT;
        else if (object instanceof SendPhoto) return MessageType.PHOTO;
        return MessageType.NOT_DETECTED;
    }

    enum MessageType {
        EXECUTE, STICKER, NOT_DETECTED, DOCUMENT, PHOTO, EDIT_MESSAGE_REPLY_MARKUP
    }
}