package DAO;
import java.util.*;
import Model.*;

public interface MessageDAO {
    public Message createMessage(Message newMessage);

    public List<Message> getAllMessages();

    public Message getMessageById(int id);

    public Message deleteMessage(int id);

    public Message updateMessage(int id, String text);

}
