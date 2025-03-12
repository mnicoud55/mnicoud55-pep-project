package DAO;

import java.util.*;
import Model.Message;

public interface MessageDAO {

    // insert statement to add a new message
    public Message createMessage(Message newMessage);

    // select query for all messages
    public List<Message> getAllMessages();

    // select query for a specific message
    public Message getMessageById(int id);

    // delete statement for a specific message
    public Message deleteMessage(int id);

    // update statement for a specific message
    public Message updateMessage(int id, String text);

    // select query for all messages with specific account_id
    public List<Message> getAllMessagesWithAccountId(int accountId);

}
