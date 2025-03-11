package DAO;
import java.util.*;
import Model.*;

public interface MessageDAO {
    public Message creatMessage(Message newMessage);

    public List<Message> getAllMessages();

    public Message getMessageById(int id);

    public Message deleteMessage(int id);

    public Message updateMessage(int id);

    public List<Message> getAllMessagesWithAccountId(int accountId);
}
