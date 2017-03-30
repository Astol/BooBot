import sx.blah.discord.api.IDiscordClient;
import sx.blah.discord.handle.obj.IChannel;
import sx.blah.discord.handle.obj.IMessage;
import sx.blah.discord.handle.obj.IUser;
import sx.blah.discord.util.DiscordException;
import sx.blah.discord.util.MissingPermissionsException;
import sx.blah.discord.util.RateLimitException;

import com.google.gson.JsonSyntaxException;
import java.lang.IllegalStateException;

/**
 * Created by Alexander on 2017-03-29.
 */
public class MessageHandler {
    private static final String PREFIX = "!!";
    private static final String owner = "115557239862984712";
    private IDiscordClient client;
    private static final QuizGame game = new QuizGame();

    MessageHandler(IDiscordClient client) {
        this.client = client;
    }

    public void handleMessage(IMessage message, IUser user, IChannel channel) throws RateLimitException, DiscordException, MissingPermissionsException, JsonSyntaxException, IllegalStateException {
        String[] split = message.getContent().split(" ");

        if(split.length >= 1 && split[0].startsWith(PREFIX)){
            if (user.isBot()){
                channel.sendMessage("Our love can never be " + user.getName() + " :disappointed_relieved: ");
                return;
            }

            String command = split[0].replaceFirst(PREFIX, "");

            if(command.equalsIgnoreCase("freddyspin")){
                channel.sendMessage("::freddyspin");
            }
            else if(command.equalsIgnoreCase("help")){
                channel.sendMessage("For a list of commands, visit:\nhttps://github.com/Astol/BooBot");
            }
            else if(command.equalsIgnoreCase("quiz")){
                if(!game.isActive(message.getCreationDate())){
                    QuizQuestion question = QuizQuestion.generateQuestion();
                    String questionFormatted = question.getFormatted();
                    game.createGame(question, message.getCreationDate());

                    channel.sendMessage(questionFormatted + game.questionEnding());
                } else {
                    channel.sendMessage("Don't u dare interrupt my quiz! Finnish it and try again!");
                }

            } else if(command.equalsIgnoreCase("A") || command.equalsIgnoreCase("B")
                    || command.equalsIgnoreCase("C") || command.equalsIgnoreCase("D")
                    || command.equalsIgnoreCase("true") || command.equalsIgnoreCase("false")) {
                System.out.println("GUESSING: " + command.toUpperCase());
                if(game.isActive(message.getCreationDate())) {
                    if(game.correctGuess(command.toUpperCase())) {
                        channel.sendMessage("Congratulations! Your guess was correct: " + "**" +  command.toUpperCase() + "**");
                    } else {
                        channel.sendMessage("Ahw dang it! It would seem that you did not answer correctly on that one." +
                                                "Correct answer was: **" + game.getCorrectAnswer() + "**" +
                                                "\n**!!quiz** for a new question! :) ");
                    }
                } else {
                    channel.sendMessage("Oh I'm afraid the time has run out for the last question(12s time limit)." +
                                            "\nStart a new question with **!!quiz**! :) ");
                }
            }
            else if(command.equalsIgnoreCase("logoff")){
                if(user.getID().equals(owner)){
                    channel.sendMessage("~~Bye Bye :(~~");
                    client.logout();
                } else {
                    channel.sendMessage("You're not my real dad " + user.getName() + "! :(");
                }
            }
        }
    }
}
