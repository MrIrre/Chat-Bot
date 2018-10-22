package chatbot;

import enums.Status;
import enums.Version;

import java.util.ArrayList;
import java.util.Map;
import java.util.Set;

/**
 * Класс пользователя. Хранит в себе всю необходимую информацию о данном пользователе.
 */
public class User {
    private int score = 0;
    private int health = 3;
    private String id;
    private enums.Version version;
    private boolean winState = true;

    public Status state = Status.StartGame;
    public Map<String, Set<String>> questionsAndAnswers;
    public ArrayList<String> allQuestions;
    public String curQuestion;

    public User(String id, Version version) {
        this.id = id;
        this.version = version;
    }

    public void healthDown() {
        health--;
    }

    public void scoreUp() {
        score++;
    }

    public int getHealth() {
        return health;
    }

    public int getScore() {
        return score;
    }

    public String getId() {
        return id;
    }

    public Version getVersion(){
        return version;
    }

    public void changeWinState(){
        winState = false;
    }

    public boolean isWin(){
        return winState;
    }

    public void resetScore(){
        score = 0;
    }

    public void resetHealth(){
        health = 3;
    }

    public void resetUser() {
        allQuestions = null;
        questionsAndAnswers = null;
        curQuestion = null;
        winState = true;
        resetHealth();
        resetScore();
    }
}

