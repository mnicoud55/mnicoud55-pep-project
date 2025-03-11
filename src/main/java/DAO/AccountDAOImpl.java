package DAO;

import java.util.List;
import java.util.ArrayList;
import java.sql.*;

import Model.Account;
import Model.Message;
import Util.ConnectionUtil;

public class AccountDAOImpl implements AccountDAO {

    @Override
    public Account registerAccount(Account newAccount) {
        if (newAccount.getUsername().length() < 1 || newAccount.getPassword().length() < 4 || getAccountByUsername(newAccount.getUsername()) != null)
            return null;
        Connection conn = ConnectionUtil.getConnection();
        try {
            String sql = "INSERT INTO account (username, password) VALUES (?,?);";
            PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            ps.setString(1, newAccount.getUsername());
            ps.setString(2, newAccount.getPassword());
            
            ps.executeUpdate();
            ResultSet pkeyResultSet = ps.getGeneratedKeys();
            if (pkeyResultSet.next()) {
                int accountId = pkeyResultSet.getInt(1);
                return new Account(accountId, newAccount.getUsername(), newAccount.getPassword());
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    @Override
    public Account validLogin(Account credentials) {
        Connection conn = ConnectionUtil.getConnection();
        try {
            String sql = "SELECT * FROM account WHERE username = ? AND password = ?;";
            PreparedStatement ps = conn.prepareStatement(sql);

            ps.setString(1, credentials.getUsername());
            ps.setString(2, credentials.getPassword());

            ResultSet queryResultSet = ps.executeQuery();
            if (queryResultSet.next()) {
                return new Account(
                    queryResultSet.getInt("account_id"), 
                    queryResultSet.getString("username"),
                    queryResultSet.getString("password")
                );
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    @Override
    public Account getAccountByUsername(String username) {
        Connection conn = ConnectionUtil.getConnection();
        try {
            String sql = "SELECT * FROM account WHERE username = ?;";
            PreparedStatement ps = conn.prepareStatement(sql);

            ps.setString(1, username);
            
            ResultSet queryResultSet = ps.executeQuery();
            if (queryResultSet.next()) {
                return new Account(
                    queryResultSet.getInt("account_id"), 
                    queryResultSet.getString("username"),
                    queryResultSet.getString("password")
                );
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    @Override
    public Account getAccountById(int id) {
        Connection conn = ConnectionUtil.getConnection();
        try {
            String sql = "SELECT * FROM account WHERE account_id = ?;";
            PreparedStatement ps = conn.prepareStatement(sql);

            ps.setInt(1, id);
            
            ResultSet queryResultSet = ps.executeQuery();
            if (queryResultSet.next()) {
                return new Account(
                    queryResultSet.getInt("account_id"), 
                    queryResultSet.getString("username"),
                    queryResultSet.getString("password")
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
            String sql = "SELECT * FROM message WHERE posted_by = ?;";
            PreparedStatement ps = conn.prepareStatement(sql);

            ps.setInt(1,accountId);

            ResultSet queryResultSet = ps.executeQuery();
            while (queryResultSet.next()) {
                messages.add(new Message(
                    queryResultSet.getInt("message_id"),
                    queryResultSet.getInt("posted_by"),
                    queryResultSet.getString("message_text"),
                    queryResultSet.getLong("time_posted_epoch")
                ));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return messages;
    }

}
