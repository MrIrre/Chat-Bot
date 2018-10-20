package chatbot;

import java.util.ArrayList;
import java.util.Map;
import java.util.Set;

public class User {
    private int Score = 0;
    private int Health = 3;
    private String Id;
    private Version Version;
    private boolean IsWin = true;

    Status State = Status.StartGame;
    Map<String, Set<String>> QuestionsAndAnswers;
    ArrayList<String> AllQuestions;
    String CurQuestion;

    User(String id, Version version) {
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
        IsWin = false;
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
        resetHealth();
        resetScore();
    }
}

enum Status {
    StartGame,
    AnswerTheQuestion,
    GameOver,
    HelpReading,
    ChoosingTopic
}

enum Version {
    Console
}
