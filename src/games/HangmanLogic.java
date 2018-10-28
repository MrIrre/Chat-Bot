package games;

import chatbot.AnswerRepository;
import chatbot.User;
import enums.Status;
import interfaces.Game;
import iomanager.AnswerReader;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class HangmanLogic implements Game {
    private final Random random = new Random();
    private int health = 10;
    private int score = 0;
    private String curWord;
    private StringBuilder wordForUser;
    private ArrayList<String> answerList;
    private Set<String> usedLetters = new HashSet<>();

    // Нужно для тестов
    public String getCurWord() {
        return curWord;
    }

    public int getHealth(){
        return health;
    }

    public String getWordForUser() {
        return wordForUser.toString();
    }

    @Override
    public ArrayList<String> handleGameRequest(String inputString, User user) {
        answerList = new ArrayList<>();

        if (curWord == null){
            giveRandomWord();
        }
        else{
            inputString = inputString.toLowerCase();

            if (!checkLetterValidation(inputString)){
                /*
                Проверка ответа от пользователя на валидность.
                 */
                answerList.add("Введи одну букву русского алфавита, пожалуйста)");
            }
            else if (usedLetters.contains(inputString)){
                /*
                Проверка на ввод ранее использованной буквы.
                 */
                answerList.add("Эта буква уже была)");
            }
            else {
                /*
                Проверка ответа от пользователя.
                Изменение количества его жизней.
                Проверка на проигрыш.
                 */
                if(curWord.contains(inputString)){
                    char curLetter = inputString.charAt(0);
                    addLetterInWord(curLetter);
                    answerList.add(AnswerRepository.getRandomAnswer(AnswerRepository.rightAnswers));
                }
                else{
                    health--;
                    answerList.add(AnswerRepository.getRandomAnswer(AnswerRepository.wrongAnswers));
                }

                usedLetters.add(inputString);

                if (health <= 0){
                    answerList.add(AnswerRepository.getLoseString());
                    user.state = Status.GameOver;
                }

                if (score == curWord.length()){
                    answerList.add(AnswerRepository.getWinString());
                    user.state = Status.GameOver;
                }
            }
        }

        if (user.state == Status.GameOver){
            answerList.add("Слово было: " + curWord);
            answerList.add(AnswerRepository.getGameOverString());
            user.state = Status.Starting;
        }
        else{
            answerList.add(AnswerRepository.getHealthString() + health);
            answerList.add("Твоё слово: " + wordForUser);
        }

        return answerList;
    }

    /**
     * Метод выдаёт слово для виселицы на текущий сеанс игры.
     */
    private void giveRandomWord(){
        ArrayList<String> allWords = AnswerReader.ParseAnswersFromFile("FilesForBot/WordsForHangman.txt");
        curWord = allWords.get(random.nextInt(allWords.size())).toLowerCase();
        wordForUser = new StringBuilder(new String(new char[curWord.length()]).replace("\0", "*"));
    }

    /**
     * Проверка ввода на корректность. В вводе должна быть только одна буква русского алфавита.
     * @param inputString ввод пользователя
     * @return результат проверки на корректность(true - ввод корректен, false - ввод некорректен)
     */
    private boolean checkLetterValidation(String inputString){
        if (inputString.length() != 1){
            return false;
        }

        char curLetter = inputString.charAt(0);
        return (curLetter >= 'а' && curLetter <= 'я') | curLetter == 'ё';
    }

    /**
     * Добавление букв в показываемое пользователю слово в случае, если он угадал букву.
     * @param curLetter буква, которую пользователь угадал
     */
    private void addLetterInWord(char curLetter){
        for (var i = 0; i < curWord.length(); i++){
            if (curWord.charAt(i) == curLetter){
                wordForUser.setCharAt(i, curLetter);
                score++;
            }
        }
    }
}
