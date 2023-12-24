package com.example.demo.bot;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.logging.Level;

@Component
public class Bot extends TelegramLongPollingBot {

    private static final String START = "/start";

    public Bot(@Value("${bot.token}") String botToken) {
        super(botToken);
    }
    @Override
    public String getBotUsername() {
        return "realBestPremiumBot";
    }
    @Override
    public void onUpdateReceived(Update update) {
        if (!update.hasMessage() || !update.getMessage().hasText()) {
            return;
        }
        var message = update.getMessage().getText();
        var chatId = update.getMessage().getChatId();
        switch (message) {
            case START -> {
                String userName = update.getMessage().getChat().getUserName();
                startCommand(chatId, userName);
            }
            default -> unknownCommand(chatId);
        }
    }
    private void startCommand(Long chatId, String userName) {
        var text = """
                Привет кожанные ублюдки
                """;
        var formattedText = String.format(text, userName);
        sendMessage(chatId, formattedText);
    }




    private void helpCommand(Long chatId) {
        var text = """
                Справочная информация по боту
                                TODO
                """;
        sendMessage(chatId, text);
    }

    private void unknownCommand(Long chatId) {
        var text = "Не удалось распознать команду!";
        sendMessage(chatId, text);
    }

    private void sendMessage(Long chatId, String text) {
        var chatIdStr = String.valueOf(chatId);
        var sendMessage = new SendMessage(chatIdStr, text);
        try {
            execute(sendMessage);
        } catch (TelegramApiException e) {
            System.out.println("МЫ ВСЕ ПРОЕБАЛИ");
        }
    }
}