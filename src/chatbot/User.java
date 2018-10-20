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
    private int Score = 0;
    private int Health = 3;
    private String Id;
    private enums.Version Version;
    private boolean WinState = true;

    public Status State = Status.StartGame;
    public Map<String, Set<String>> QuestionsAndAnswers;
    public ArrayList<String> AllQuestions;
    public String CurQuestion;

    public User(String id, Version version) {
        Id = id;
        Version = version;
    }

    public void healthDown() {
        Health--;
    }

    public void scoreUp() {
        Score++;
    }

    public int getHealth() {
        return Health;
    }

    public int getScore() {
        return Score;
    }

    public String getId() {
        return Id;
    }

    public Version getVersion(){
        return Version;
    }

    public void changeWinState(){
        WinState = false;
    }

    public boolean isWin(){
        return WinState;
    }

    public void resetScore(){
        Score = 0;
    }

    public void resetHealth(){
        Health = 3;
    }

    public void resetUser() {
        AllQuestions = null;
        QuestionsAndAnswers = null;
        CurQuestion = null;
        WinState = true;
        resetHealth();
        resetScore();
    }
}

