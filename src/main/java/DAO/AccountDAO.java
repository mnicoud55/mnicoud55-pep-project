package DAO;

import Model.Account;

public interface AccountDAO {

    // insert statement to add a new account
    public Account registerAccount(Account newAccount);

    // select statement to authenticate login
    public Account validLogin(Account credentials);

    // select statement for specific account by username
    public Account getAccountByUsername(String username);

    // select statement for specific account by account_id
    public Account getAccountById(int id);

}
