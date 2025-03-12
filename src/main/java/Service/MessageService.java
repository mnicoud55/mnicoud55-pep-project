package Service;

import java.util.List;

import DAO.*;
import Model.Message;

public class MessageService {

    private MessageDAO messageDAO;

    public MessageService() {
        messageDAO = new MessageDAOImpl();
    }

    public Message createMessage(Message newMessage) {
        AccountDAO accDAO = new AccountDAOImpl();
        if (newMessage.getMessage_text() == null || newMessage.getMessage_text().equals("") || newMessage.getMessage_text().length() > 255 || accDAO.getAccountById(newMessage.getPosted_by()) == null) 
            return null;
        return messageDAO.createMessage(newMessage);
    }

    public List<Message> getAllMessages() {
        return messageDAO.getAllMessages();
    }

    public Message getMessageById(int id) {
        return messageDAO.getMessageById(id);
    }

    public Message deleteMessage(int id) {
        if (messageDAO.getMessageById(id) == null)
            return null;
        return messageDAO.deleteMessage(id);
    }

    public Message updateMessage(int id, String text) {
        if (text == null || text.length() < 1 || text.length() > 255) 
            return null;
        return messageDAO.updateMessage(id, text);
    }
    
    public List<Message> getAllMessagesWithAccountId(int accountId) {
        return messageDAO.getAllMessagesWithAccountId(accountId);
    }
}
