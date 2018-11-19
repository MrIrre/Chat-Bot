package chatbot;

import enums.RequestFrom;
import interfaces.InputOutput;

import org.apache.commons.cli.*;
import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.meta.TelegramBotsApi;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.*;

import static java.lang.String.format;

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

        Map<RequestFrom, InputOutput> versions = new HashMap<>();

        try {
            cmd = parser.parse(options, args);
            if (cmd.getOptions().length == 0){
                formatter.printHelp("Quizzz.java", options);
                System.exit(1);
            }

//            if (cmd.getOptions().length > 1){
//                formatter.printHelp("Quizzz.java", options);
//                System.exit(1);
//            }

            if (cmd.hasOption("h")){
                formatter.printHelp("Quizzz.java", options);
                System.exit(0);
            }

            if (cmd.hasOption("vk")){
                System.out.println("VkBot Version is running.");
                versions.put(RequestFrom.VK, new VkVersion());
            }

            if (cmd.hasOption("c")){
                System.out.println("Console Version is running.");
                versions.put(RequestFrom.Console, new Console());
            }

            if (cmd.hasOption("t")){
                System.out.println("TelegramBot Version is running.");
                ApiContextInitializer.init();
                TelegramBot telegramBot = new TelegramBot();
                versions.put(RequestFrom.Telegram, telegramBot);
                TelegramBotsApi botApi = new TelegramBotsApi();
                botApi.registerBot(telegramBot);
            }

        } catch (ParseException e) {
            System.out.println(e.getMessage());
            formatter.printHelp("Quizzz.java", options);
            System.exit(1);
        }

        MainLoop mainLoop = new MainLoop();
        mainLoop.runMainLoop(versions);
    }
}
