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

import com.google.gson.JsonSyntaxException;
import java.lang.IllegalStateException;
import static sx.blah.discord.handle.obj.Status.game;

public class BooBot {
    private static String TOKEN = "";
    private static IDiscordClient client;
    private static MessageHandler messageHandler;

    private final Map<IGuild, IChannel> lastChannel = new HashMap<IGuild, IChannel>();

    public static void main(String[] args) throws DiscordException, RateLimitException, FileNotFoundException {
        Scanner file = new Scanner(new File("src/main/java/hidden.txt"));
        TOKEN = file.nextLine();

        System.out.println("Logging bot in...");
        client = new ClientBuilder().withToken(TOKEN).build();
        client.getDispatcher().registerListener(new BooBot());

        messageHandler = new MessageHandler(client);

        client.login();
    }

    @EventSubscriber
    public void onReady(ReadyEvent event) {
        client.changeStatus(game("!!help"));
        System.out.println("Bot is now ready!");
    }

    @EventSubscriber
    public void onMessage(MessageReceivedEvent event) throws RateLimitException, DiscordException, MissingPermissionsException, IllegalStateException, JsonSyntaxException {
        IMessage message = event.getMessage();
        IUser user = message.getAuthor();
        IChannel channel = message.getChannel();

        messageHandler.handleMessage(message, user, channel);
    }

}
