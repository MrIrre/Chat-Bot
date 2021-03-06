package chatbot;

import enums.RequestFrom;
import interfaces.InputOutput;

import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.ArrayList;
import java.util.concurrent.ConcurrentLinkedQueue;

public class TelegramBot extends TelegramLongPollingBot implements InputOutput {
    private final String BOT_NAME = "Quizzz...";
    private final String BOT_TOKEN = "...";

    private ConcurrentLinkedQueue<Request> requestQueue = new ConcurrentLinkedQueue<>();

    @Override
    public Request getRequest() {
        return requestQueue.poll();
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
        requestQueue.add(new Request(curMessage.getChatId().toString(), curMessage.getText(), RequestFrom.Telegram));
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
