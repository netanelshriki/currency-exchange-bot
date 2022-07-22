package org.telegram.extensions;

import com.google.common.annotations.VisibleForTesting;
import org.telegram.BotConfig;
import org.telegram.abilitybots.api.bot.AbilityBot;
import org.telegram.abilitybots.api.objects.Ability;
import org.telegram.abilitybots.api.sender.MessageSender;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.File;
import java.util.Map;
import java.util.function.Predicate;

import static org.telegram.abilitybots.api.objects.Flag.TEXT;
import static org.telegram.abilitybots.api.objects.Locality.ALL;
import static org.telegram.abilitybots.api.objects.Privacy.PUBLIC;

public class HelloBot extends AbilityBot {

    public HelloBot() {
        super(BotConfig.TOKENMYPROJECT, BotConfig.USERNAMEMYPROJECT);
    }

    @Override
    public long creatorId() {
        return 123456789;
    }

    //that's handle a free text input
    @Override
    public void onUpdateReceived(Update update) {

        if (update.hasMessage()) {
            Message message = update.getMessage();

            //check if the message has text. it could also  contain for example a location ( message.hasLocation() )
            if (message.hasText()) {
                //create a object that contains the information to send back the message
                SendMessage sendMessageRequest = new SendMessage();


                sendMessageRequest.setChatId(message.getChatId().toString()); //who should get the message? the sender from which we got the message...

                try {
                    sendMessageRequest.setText("you are: " + message.getChat().getFirstName());
                    execute(sendMessageRequest);
                } catch (TelegramApiException e) {
                    System.out.println(e.getMessage());
                }

            }//end if()
        }//end  if()

    }//end onUpdateRec


    //ability handles a ready words such as - /hello or - /by

    public Ability sayHelloWorld() {

        return Ability
                .builder()
                .name("hello")
                .info("says hello world!")
                .locality(ALL)
                .privacy(PUBLIC)
                .action(ctx -> {

                    silent.send("Hello "+ctx.user().getFirstName()+"! ", ctx.chatId());
                })
                .build();
    }

    /**
     * Says hi with the specified argument.
     * <p>
     * You can experiment by using /sayhi developer. You can also try not supplying it an argument. :)
     * <p>
     * Note that this ability only works in USER locality mode. So, it won't work in groups!
     */
//    public Ability saysHelloWorldToFriend() {
//        return Ability.builder()
//                .name("sayhi")
//                .info("Says hi")
//                .privacy(PUBLIC)
//                .locality(USER)
//                .input(1)
//                .action(ctx -> sender.send("Hi " + ctx.firstArg(), ctx.chatId()))
//                .build();
//    }
    @Override
    public boolean checkGlobalFlags(Update update) {
        return true;
    }

    /**
     * This ability has an extra "flag". It needs a photo to activate! This feature is activated by default if there is no /command given.
     */
    public Ability sayNiceToPhoto() {
        return Ability.builder()
                .name("hi") // DEFAULT ability is executed if user did not specify a command -> Bot needs to have access to messages (check FatherBot)
                .flag(TEXT)
                .privacy(PUBLIC)
                .locality(ALL)
                .input(0)
                .action(ctx -> {


                    SendMessage message = new SendMessage();

                    message.setText("hi to you!");

//                        sender.execute(message);
                    silent.send(message.getText(), ctx.chatId());

                }).build();
    }

    /**
     * Use the database to fetch a count per user and increments.
     * <p>
     * Use /count to experiment with this ability.
     */
    public Ability useDatabaseToCountPerUser() {
        return Ability.builder()
                .name("count")
                .info("Increments a counter per user")
                .privacy(PUBLIC)
                .locality(ALL)
                .input(0)
                .action(ctx -> {
                    // db.getMap takes in a string, this must be unique and the same everytime you want to call the exact same map
                    // TODO: Using integer as a key in this db map is not recommended, it won't be serialized/deserialized properly if you ever decide to recover/backup db
                    Map<String, Integer> countMap = db.getMap("COUNTERS");
                    int userId = Math.toIntExact(ctx.user().getId());

                    // Get and increment counter, put it back in the map
                    Integer counter = countMap.compute(String.valueOf(userId), (id, count) -> count == null ? 1 : ++count);

                    File pic = new File("C:\\Users\\netanel\\Pictures\\newLogo.jpeg");

                    InputFile inputFile = new InputFile(pic, "newLogo");

                    SendPhoto sendPhoto = SendPhoto.builder()
                            .allowSendingWithoutReply(true)
                            .chatId(ctx.chatId())
                            .photo(inputFile)
                            .build();

                    try {
                        sender.sendPhoto(sendPhoto);
                    } catch (TelegramApiException e) {
                        System.out.println(e.getMessage());
                    }


                    // Send formatted will enable markdown
                    String message = String.format("%s, your count is now *%d*!", ctx.user().getUserName(), counter);
                })
                .build();

        // In this ability, you can also experiment with /backup and /recover of the AbilityBot!
        // Take a backup of when the counter is at 1, do /count multiple times and attempt to recover
        // The counter will be reset since the db will recover to the backup that you specify
    }


    private Predicate<Update> isReplyToMessage(String message) {
        return upd -> {
            Message reply = upd.getMessage().getReplyToMessage();
            return reply.hasText() && reply.getText().equalsIgnoreCase(message);
        };
    }

    private Predicate<Update> isReplyToBot() {
        return upd -> upd.getMessage().getReplyToMessage().getFrom().getUserName().equalsIgnoreCase(getBotUsername());
    }


    @VisibleForTesting
    void setSender(MessageSender sender) {
        this.sender = sender;
    }
}
