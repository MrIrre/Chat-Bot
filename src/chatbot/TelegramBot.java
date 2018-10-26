package chatbot;

import interfaces.Input;
import interfaces.Output;

import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.ArrayList;

public class TelegramBot extends TelegramLongPollingBot implements Input, Output {
    private final String BOT_NAME = "Quizzz...";
    private final String BOT_TOKEN = "635041031:AAF-hgOeGO0QF7lK2kt0STQ6C5T7eq4z4wU";

    private Request curRequest;

    @Override
    public Request getRequest() {
        Request request = curRequest;
        curRequest = null;
        return request;
    }

    @Override
    public void print(String userId, ArrayList<String> outputStrings) {
        sendMsg(userId, String.join("\n", outputStrings));
    }

    /**
     * Отправка сообщения нужному пользователю.
     * @param userId ID пользователя
     * @param text Текст сообщения
     */
    private void sendMsg(String userId, String text) {
        SendMessage s = new SendMessage()
                .setChatId(userId)
                .setText(text);
        try {
            execute(s);
        } catch (TelegramApiException e){
            e.printStackTrace();
        }
    }

    @Override
    public void onUpdateReceived(Update update) {
        Message curMessage = update.getMessage();
        curRequest = new Request(curMessage.getChatId().toString(), curMessage.getText());
    }

    @Override
    public String getBotUsername() {
        return BOT_NAME;
    }

    @Override
    public String getBotToken() {
        return BOT_TOKEN;
    }
}