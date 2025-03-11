package DAO;

import java.util.List;
import Model.*;

public interface AccountDAO {
    public Account registerAccount(Account newAccount);

    public Account validLogin(Account credentials);

    public Account getAccountByUsername(String username);

    public Account getAccountById(int id);

    public List<Message> getAllMessagesWithAccountId(int accountId);

}
