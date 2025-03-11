package Service;

import java.util.*;

import DAO.*;
import Model.Message;

public class MessageService {
    private MessageDAO messageDAO;

    public MessageService() {
        messageDAO = new MessageDAOImpl();
    }

    public Message createMessage(Message newMessage) {
        return messageDAO.createMessage(newMessage);
    }

    public List<Message> getAllMessages() {
        return messageDAO.getAllMessages();
    }

    public Message getMessageById(int id) {
        return messageDAO.getMessageById(id);
    }

    public Message deleteMessage(int id) {
        return messageDAO.deleteMessage(id);
    }

    public Message updateMessage(int id, String text) {
        return messageDAO.updateMessage(id, text);
    }

}
