package chatbot;

import enums.Status;
import interfaces.Game;

/**
 * Класс пользователя. Хранит в себе всю необходимую информацию о данном пользователе.
 */
public class User {
    private String id;

    public Status state = Status.Starting;
    public Game game;

    public User(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }
}
