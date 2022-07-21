package org.telegram;

import com.twilio.Twilio;
import org.telegram.abilitybots.api.bot.AbilityBot;
import org.telegram.abilitybots.api.util.AbilityExtension;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;


public class MyProjectHandler extends AbilityBot{


    protected MyProjectHandler(String botToken, String botUsername) {
        super(botToken, botUsername);
    }

    @Override
    public String getBotUsername() {
        return BotConfig.USERNAMEMYPROJECT;
    }

    @Override
    public String getBotToken() {
        return BotConfig.TOKENMYPROJECT;
    }



    @Override
    public long creatorId() {
        return 0;
    }

    @Override
    public void onUpdateReceived(Update update) {
        //check if the update has a message
        if (update.hasMessage()) {
            Message message = update.getMessage();

            //check if the message has text. it could also  contain for example a location ( message.hasLocation() )
            if (message.hasText()) {
                //create a object that contains the information to send back the message
                SendMessage sendMessageRequest = new SendMessage();
                sendMessageRequest.setChatId(message.getChatId().toString()); //who should get the message? the sender from which we got the message...
                sendMessageRequest.setText("you said: " + message.getText());

                try {
                    execute(sendMessageRequest); //at the end, so some magic and send the message ;)
                    sendSMS(message.getText());
                } catch (TelegramApiException e) {
                    //do some error handling
                    System.out.println(e.getMessage());
                }//end catch()
            }//end if()
        }//end  if()

    }//end onUpdateReceived()

    public static void sendSMS(String msg) {

//        Twilio.init("ACdf5d7b684b368dd9e1e5f32ee9b65e13", "454fcf19379d20cfe3b6e2df22690582");
//
//        com.twilio.rest.api.v2010.account.Message.creator(new com.twilio.type.PhoneNumber("+972584909582"),
//                new com.twilio.type.PhoneNumber("+972584909583"), "Hello from Twilio 📞 " + msg).create();

        Twilio.init("ACdf5d7b684b368dd9e1e5f32ee9b65e13", "454fcf19379d20cfe3b6e2df22690582");
        com.twilio.rest.api.v2010.account.Message messageSms = com.twilio.rest.api.v2010.account.Message.creator(
                new com.twilio.type.PhoneNumber("+972584909583"),
                "MG5eef5e84ec9a9220de12eae8943c346f",
                msg)
                .create();
        System.out.println(messageSms.getFrom());
        System.out.println(messageSms.getTo());

    }


}
