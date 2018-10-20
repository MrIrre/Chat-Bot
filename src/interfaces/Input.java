package interfaces;

import chatbot.Request;

/**
 * Интерфейс для реализации ввода запросов на разных UI.
 */
public interface Input {
    Request getRequest();
}
