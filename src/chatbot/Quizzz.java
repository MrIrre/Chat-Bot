package chatbot;

public class Quizzz {

    public static void main(String[] args) throws Exception {
        Console curConsole = new Console();
        MainLoop mainLoop = new MainLoop();
        mainLoop.runMainLoop(curConsole, curConsole);
    }
}
