package Controller;

import io.javalin.Javalin;
import io.javalin.http.Context;
import java.util.List;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import Model.*;
import Service.*;


/**
 * TODO: You will need to write your own endpoints and handlers for your controller. The endpoints you will need can be
 * found in readme.md as well as the test cases. You should
 * refer to prior mini-project labs and lecture materials for guidance on how a controller may be built.
 */
public class SocialMediaController {
    AccountService accountService;
    MessageService messageService;

    public SocialMediaController() {
        accountService = new AccountService();
        messageService = new MessageService();
    }
    /**
     * In order for the test cases to work, you will need to write the endpoints in the startAPI() method, as the test
     * suite must receive a Javalin object from this method.
     * @return a Javalin app object which defines the behavior of the Javalin controller.
     */
    public Javalin startAPI() {
        Javalin app = Javalin.create();
        app.post("/register", this::postAccountHandler);
        app.post("/login", this::postLoginHandler);
        app.post("/messages", this::postMessageHandler);
        app.get("/messages", this::getAllMessagesHandler);
        app.get("/messages/{message_id}", this::getMessageHandler);
        app.delete("/messages/{message_id}", this::deleteMessageHandler);
        app.patch("/messages/{message_id}", this::updateMessageHandler);
        app.get("/accounts/{account_id}/messages", this::getMessagesByIdHandler);

        return app;
    }

    /**
     * This is a post handler for the /register endpoint.
     * @param context The Javalin Context object manages information about both the HTTP request and response.
     */
    private void postAccountHandler(Context context) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        Account account = mapper.readValue(context.body(), Account.class);
        Account registeredAccount = accountService.registerAccount(account);
        if (registeredAccount != null) 
            context.json(registeredAccount);
        else    
            context.status(400);
    }

    /**
     * This is a post handler for the /login endpoint.
     * @param context The Javalin Context object manages information about both the HTTP request and response.
     */
    private void postLoginHandler(Context context) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        Account account = mapper.readValue(context.body(), Account.class);
        Account authorizedAccount = accountService.validLogin(account);
        if (authorizedAccount != null) 
            context.json(authorizedAccount);
        else 
            context.status(401);
    }

    /**
     * This is a post handler for the /messages endpoint.
     * @param context The Javalin Context object manages information about both the HTTP request and response.
     */
    private void postMessageHandler(Context context) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        Message message = mapper.readValue(context.body(), Message.class);
        Message createdMessage = messageService.createMessage(message);

        if (createdMessage != null) 
            context.json(createdMessage);
        else 
            context.status(400);
    }

    /**
     * This is a get handler for the /messages endpoint.
     * @param context The Javalin Context object manages information about both the HTTP request and response.
     */
    private void getAllMessagesHandler(Context context) {
        List<Message> messages = messageService.getAllMessages();
        context.json(messages);
    }

    /**
     * This is a get handler for the /messages/{message_id} endpoint.
     * @param context The Javalin Context object manages information about both the HTTP request and response.
     */
    private void getMessageHandler(Context context) {
        Message message = messageService.getMessageById(Integer.parseInt(context.pathParam("message_id")));
        if (message != null)
            context.json(message);
    }

    /**
     * This is a delete handler for the /messages/{message_id} endpoint.
     * @param context The Javalin Context object manages information about both the HTTP request and response.
     */
    private void deleteMessageHandler(Context context) {
        Message deletedMessage = messageService.deleteMessage(Integer.parseInt(context.pathParam("message_id")));
        if (deletedMessage != null)
            context.json(deletedMessage);
    }

    /**
     * This is an update handler for the /messages/{message_id} endpoint.
     * @param context The Javalin Context object manages information about both the HTTP request and response.
     */
    private void updateMessageHandler(Context context) throws JsonProcessingException{
        ObjectMapper mapper = new ObjectMapper();
        Message message = mapper.readValue(context.body(), Message.class);
        Message updatedMessage = messageService.updateMessage(Integer.parseInt(context.pathParam("message_id")), message.getMessage_text());
        if (updatedMessage != null)
            context.json(updatedMessage);    
        else 
            context.status(400);
    }

    /**
     * This is a get handler for the /account/{account_id}/messages endpoint.
     * @param context The Javalin Context object manages information about both the HTTP request and response.
     */
    private void getMessagesByIdHandler(Context context) {
        List<Message> messagesbyId = messageService.getAllMessagesWithAccountId(Integer.parseInt(context.pathParam("account_id")));
        context.json(messagesbyId);
    }
    
}