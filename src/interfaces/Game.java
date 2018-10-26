package interfaces;

import chatbot.User;
import java.util.ArrayList;

/**
 * Интерфейс игры, которую, если понадобится, будем добавлять боту.
 */
public interface Game {
    /**
     * Метод, который, в зависимости от игры, правильно обрабатывает ввод от пользователя.
     * @param inputString Введённая пользователем строка
     * @param user Экземпляр класса текущего пользователь
     * @return Лист со строками ответа на ввод пользователя
     */
    ArrayList<String> handleGameRequest(String inputString, User user) throws Exception;
}
