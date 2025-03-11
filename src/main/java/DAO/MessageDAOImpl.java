package DAO;

import java.util.*;

import org.h2.command.ddl.PrepareProcedure;

import java.sql.*;

import Model.Message;
import Util.ConnectionUtil;

public class MessageDAOImpl implements MessageDAO {
    private AccountDAO accDAO = new AccountDAOImpl();

    @Override
    public Message creatMessage(Message newMessage) {
        if (newMessage.getMessage_text() == null || newMessage.getMessage_text().equals("") || newMessage.getMessage_text().length() > 255 || accDAO.getAccountById(newMessage.getPosted_by()) == null) {
            return null;
        }
        Connection conn = ConnectionUtil.getConnection();
        try {
            String sql = "INSERT INTO message (posted_by, message_text, time_posted_epoch) VALUES (?,?,?)";
            PreparedStatement ps = conn.prepareStatement(sql);

            ps.setInt(1, newMessage.getPosted_by());
            ps.setString(2, newMessage.getMessage_text());
            ps.setLong(3, newMessage.getTime_posted_epoch());

            ps.executeUpdate();
            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                return new Message(
                    rs.getInt("message_id"),
                    rs.getInt("posted_by"),
                    rs.getString("message_text"),
                    rs.getLong("time_posted")
                );
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    @Override
    public List<Message> getAllMessages() {
        Connection conn = ConnectionUtil.getConnection();
        List<Message> messages = new ArrayList<Message>();
        try {
            String sql = "SELECT * FROM message;";
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet queryResultSet = ps.executeQuery();
            while (queryResultSet.next()) {
                messages.add(new Message(
                    queryResultSet.getInt("message_id"),
                    queryResultSet.getInt("posted_by"),
                    queryResultSet.getString("message_text"),
                    queryResultSet.getLong("time_posted")
                ));
            }
            return messages;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    @Override
    public Message getMessageById(int id) {
        Connection conn = ConnectionUtil.getConnection();
        try {
            String sql = "SELECT * FROM message WHERE message_id = ?;";
            PreparedStatement ps = conn.prepareStatement(sql);

            ps.setInt(1, id);

            ResultSet queryResultSet = ps.executeQuery();
            if (queryResultSet.next()) {
                return new Message(
                    queryResultSet.getInt("message_id"),
                    queryResultSet.getInt("posted_by"),
                    queryResultSet.getString("message_text"),
                    queryResultSet.getLong("time_posted")
                );
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    @Override
    public Message deleteMessage(int id) {
        Connection conn = ConnectionUtil.getConnection();
        try {
            String sql = "DELETE * FROM message WHERE message_id = ?;";
            PreparedStatement ps = conn.prepareStatement(sql);

            ps.setInt(1, id);

            ps.executeUpdate();
            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                return new Message(
                    rs.getInt("message_id"),
                    rs.getInt("posted_by"),
                    rs.getString("message_text"),
                    rs.getLong("time_posted")
                );
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    @Override
    public Message updateMessage(int id, String text) {
        if (text == null || text.equals("") || text.length() > 255) {
            return null;
        }
        Connection conn = ConnectionUtil.getConnection();
        try {
            String sql = "UPDATE message SET message_text = ? WHERE message_id = ?;";
            PreparedStatement ps = conn.prepareStatement(sql);

            ps.setString(1, text);
            ps.setInt(2, id);

            ps.executeUpdate();
            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                return new Message(
                    rs.getInt("message_id"),
                    rs.getInt("posted_by"),
                    rs.getString("message_text"),
                    rs.getLong("time_posted")
                );
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    @Override
    public List<Message> getAllMessagesWithAccountId(int accountId) {
        Connection conn = ConnectionUtil.getConnection();
        List<Message> messages = new ArrayList<Message>();
        try {
            String sql = "SELECT * FROM message;";
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet queryResultSet = ps.executeQuery();
            while (queryResultSet.next()) {
                if (queryResultSet.getInt("posted_by") == accountId) {
                    messages.add(new Message(
                        queryResultSet.getInt("message_id"),
                        queryResultSet.getInt("posted_by"),
                        queryResultSet.getString("message_text"),
                        queryResultSet.getLong("time_posted")
                    ));
                }
            }
            return messages;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

}
