package chatbot;

import interfaces.Input;
import interfaces.Output;

import org.apache.commons.cli.*;
import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.meta.TelegramBotsApi;

/**
 * Точка входа в приложение. Обработка входных аргументов для запуска определённой версии чат-бота.
 */
public class Quizzz {
    public static void main(String[] args) throws Exception {
        Options options = new Options();

        Option vkBotArgOption = new Option("vk", "vkbot",
                false, "run vk bot version");
        vkBotArgOption.setRequired(false);
        options.addOption(vkBotArgOption);

        Option telegramBotArgOption = new Option("t", "telegram",
                false, "run telegram bot version");
        telegramBotArgOption.setRequired(false);
        options.addOption(telegramBotArgOption);

        Option consoleArgOption = new Option("c", "console",
                false, "run console version");
        consoleArgOption.setRequired(false);
        options.addOption(consoleArgOption);

        Option helpOption = new Option("h", "help", false, "see help");
        helpOption.setRequired(false);
        options.addOption(helpOption);

        CommandLineParser parser = new DefaultParser();
        HelpFormatter formatter = new HelpFormatter();
        CommandLine cmd;

        Object curMode = null;

        try {
            cmd = parser.parse(options, args);
            if (cmd.getOptions().length == 0){
                System.out.println("See help. Quizzz.java [-h or --help]");
                System.exit(1);
            }

            if (cmd.getOptions().length > 1){
                System.out.println("Enter ONLY one argument!");
                System.out.println("See help. Quizzz.java [-h or --help]");
                System.exit(1);
            }

            if (cmd.hasOption("h")){
                formatter.printHelp("Quizzz.java", options);
                System.exit(0);
            }

            if (cmd.hasOption("vk")){
                System.out.println("VkBot Version is running.");
                curMode = new VkVersion();
            }
            else if (cmd.hasOption("c")){
                System.out.println("Console Version is running.");
                curMode = new Console();
            }
            else if (cmd.hasOption("t")){
                System.out.println("TelegramBot Version is running.");
                ApiContextInitializer.init();
                curMode = new TelegramBot();
                TelegramBotsApi botApi = new TelegramBotsApi();
                botApi.registerBot((TelegramBot)curMode);
            }

        } catch (ParseException e) {
            System.out.println(e.getMessage());
            formatter.printHelp("Quizzz.java", options);
            System.exit(1);
        }

        MainLoop mainLoop = new MainLoop();
        mainLoop.runMainLoop((Input)curMode, (Output)curMode);
    }
}
