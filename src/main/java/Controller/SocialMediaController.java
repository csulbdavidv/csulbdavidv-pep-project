package Controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import Model.Account;
import Service.AccountService;
import Service.MessageService;
import Model.Message;
import java.util.*;


import io.javalin.Javalin;
import io.javalin.http.Context;

/**
 * TODO: You will need to write your own endpoints and handlers for your controller. The endpoints you will need can be
 * found in readme.md as well as the test cases. You should
 * refer to prior mini-project labs and lecture materials for guidance on how a controller may be built.
 */
public class SocialMediaController {
    /**
     * In order for the test cases to work, you will need to write the endpoints in the startAPI() method, as the test
     * suite must receive a Javalin object from this method.
     * @return a Javalin app object which defines the behavior of the Javalin controller.
     */
    AccountService accountService;
    MessageService messageService;

    public SocialMediaController(){
        this.accountService = new AccountService();
        this.messageService = new MessageService();
    }

    public Javalin startAPI() {
        Javalin app = Javalin.create();
        app.post("/register", this::postRegisterHandler);
        app.post("/login", this::postLoginHandler);
        app.post("/messages", this::postNewMessageHandler);
        app.get("/messages", this::getMessagesHandler);
        app.get("/messages/{message_id}", this::getMessageByIdHandler);
        app.delete("/messages/{message_id}", this::deleteMessageHandler);
        app.patch("/messages/{message_id}", this::updateMessageHandler);

        return app;
    }

    /**
     * This is an example handler for an example endpoint.
     * @param context The Javalin Context object manages information about both the HTTP request and response.
     */
    private void exampleHandler(Context context) {
        context.json("sample text");
    }


    private void postRegisterHandler(Context context) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        Account account = mapper.readValue(context.body(), Account.class);
        if((account.getPassword().length() < 4)|| account.getUsername().isEmpty()){
            context.status(400);
            return;
        }
        Account registeredAccount = accountService.register(account);
        if(registeredAccount != null){
            context.json(mapper.writeValueAsString(registeredAccount));
        }else {
            context.status(400);
        }
    }

    private void postLoginHandler(Context context) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        Account account = mapper.readValue(context.body(), Account.class);
        Account loginAccount = accountService.login(account);
        if(loginAccount != null){
            context.json(mapper.writeValueAsString(loginAccount));
        }else {
            context.status(401);
        }
    }

    private void postNewMessageHandler(Context context) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        Message message = mapper.readValue(context.body(), Message.class);
        Message newMessage = messageService.createMessage(message); //finish implementation
        if(newMessage != null){
            context.json(mapper.writeValueAsString(newMessage));
        }else {
            context.status(400);
        }
    }

    private void getMessagesHandler(Context context){
        List<Message> messages = messageService.getAllMessages();
        context.json(messages);
    }

    private void getMessageByIdHandler(Context context){
        int messageId = Integer.parseInt(context.pathParam("message_id")); // Extract message_id
        Message message = messageService.getMessageById(messageId);

        if (message != null) {
            context.json(message);
        } else {
            context.status(200);
            context.result("");
        }
    }

    private void deleteMessageHandler(Context context){
        int messageId = Integer.parseInt(context.pathParam("message_id")); // Extract message_id
        Message message = messageService.deleteMessageById(messageId);

        if (message != null) {
            context.json(message);
        } else {
            context.status(200);
            context.result("");
        }
    }

    public void updateMessageHandler(Context context) throws JsonProcessingException{
        ObjectMapper mapper = new ObjectMapper();
        Message message = mapper.readValue(context.body(), Message.class);
        int message_id = Integer.parseInt(context.pathParam("message_id"));
        Message updatedMessage = messageService.updateMessage(message_id, message);
        if(updatedMessage != null){
            context.json(mapper.writeValueAsString(updatedMessage));
        }else{
            context.status(400);
        }
    }

}