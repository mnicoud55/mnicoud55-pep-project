package DAO;

import java.sql.*;

import Model.Account;
import Util.ConnectionUtil;

public class AccountDAOImpl implements AccountDAO {

    @Override
    public Account registerAccount(Account newAccount) {
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
}
