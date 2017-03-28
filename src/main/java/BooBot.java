/**
 * Created by Alexander on 2017-03-23.
 */
import sx.blah.discord.api.ClientBuilder;
import sx.blah.discord.api.IDiscordClient;
import sx.blah.discord.api.events.EventSubscriber;
import sx.blah.discord.handle.impl.events.MessageReceivedEvent;
import sx.blah.discord.handle.impl.events.ReadyEvent;
import sx.blah.discord.handle.obj.*;
import sx.blah.discord.util.DiscordException;
import sx.blah.discord.util.MissingPermissionsException;
import sx.blah.discord.util.RateLimitException;

import java.util.*;
import java.io.*;
import java.util.HashMap;

import static sx.blah.discord.handle.obj.Status.game;

public class BooBot {
    private static String TOKEN = "";
    private static final String PREFIX = "!!";
    private static IDiscordClient client;
    private static final String owner = "115557239862984712";

    private final Map<IGuild, IChannel> lastChannel = new HashMap<IGuild, IChannel>();

    public static void main(String[] args) throws IOException, DiscordException, RateLimitException {
        Scanner file = new Scanner(new File("src/main/java/hidden.txt"));
        TOKEN = file.nextLine();

        System.out.println("Logging bot in...");
        client = new ClientBuilder().withToken(TOKEN).build();
        client.getDispatcher().registerListener(new BooBot());
        client.login();
    }

    @EventSubscriber
    public void onReady(ReadyEvent event) {
        client.changeStatus(game("!!help"));
        System.out.println("Bot is now ready!");
    }

    @EventSubscriber
    public void onMessage(MessageReceivedEvent event) throws RateLimitException, DiscordException, MissingPermissionsException {
        IMessage message = event.getMessage();
        IUser user = message.getAuthor();

        IChannel channel = message.getChannel();

        String[] split = message.getContent().split(" ");
        if(split.length >= 1 && split[0].startsWith(PREFIX)){
            if (user.isBot()){
                channel.sendMessage("Our love can never be " + user.getName() + " :disappointed_relieved: ");
                return;
            }

            String command = split[0].replaceFirst(PREFIX, "");

            if(command.equalsIgnoreCase("freddyspin")){
                channel.sendMessage("::freddyspin");
            } else if(command.equalsIgnoreCase("help")){
                channel.sendMessage("For a list of commands, visit:\nhttps://github.com/Astol/BooBot");
            } else if(command.equalsIgnoreCase("tja")){
                channel.sendMessage("Gib cash<3");
            } else if(command.equalsIgnoreCase("logoff")){
                if(user.getID().equals(owner)){
                    channel.sendMessage("Hejdårå :(");
                    client.logout();
                } else {
                    channel.sendMessage("Du är inte min riktiga pappa " + user.getName() + " :(");
                }
            }
        }
    }

}
