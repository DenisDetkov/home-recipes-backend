package org.terenbro.telegramBot.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.terenbro.telegramBot.service.MessageSender;

@Component
public class ThreadsStartup {
    @Autowired
    MessageSender messageSender;

    @EventListener(ApplicationReadyEvent.class)
    public void doSomethingAfterStartup() {
        Thread sender = new Thread(messageSender);
        sender.setDaemon(true);
        sender.setName("MsgSender");
        sender.setPriority(1);
        sender.start();
    }
}
