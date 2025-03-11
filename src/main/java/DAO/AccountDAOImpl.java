package DAO;

import Model.Account;
import Util.ConnectionUtil;
import java.sql.*;

public class AccountDAOImpl implements AccountDAO {

    @Override
    public Account register(Account newAccount) {
        if (!newAccount.getUsername().equals("") || newAccount.getPassword().length() < 4 || getAccountByUsername(newAccount.getUsername()) != null)
            return null;
        Connection conn = ConnectionUtil.getConnection();
        try {
            String sql = "INSERT INTO account (username, password) VALUES (?,?);";
            PreparedStatement ps = conn.prepareStatement(sql);

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
            String sql = "SELECT * FROM account WHERE username = ? & password = ?;";
            PreparedStatement ps = conn.prepareStatement(sql);

            ps.setString(1, credentials.getUsername());
            ps.setString(2, credentials.getPassword());

            ResultSet queryResultSet = ps.executeQuery();
            if (queryResultSet.next()) {
                return new Account(queryResultSet.getInt("account_id"), 
                queryResultSet.getString("username"),
                queryResultSet.getString("password"));
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
                return new Account(queryResultSet.getInt("account_id"), 
                queryResultSet.getString("username"),
                queryResultSet.getString("password"));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

}
