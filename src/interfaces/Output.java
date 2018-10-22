package interfaces;

import java.util.ArrayList;

/**
 * Интерфейс для реализации вывода ответов на разных UI.
 */
public interface Output {
    void print(String userId, ArrayList<String> outputStrings);
}
