package Service;

import java.util.List;

import DAO.*;
import Model.Account;
import Model.Message;

public class AccountService {
    private AccountDAO accountDAO;

    public AccountService() {
        accountDAO = new AccountDAOImpl();
    }

    public Account registerAccount(Account account) {
        return accountDAO.registerAccount(account);
    }

    public Account validLogin(Account account) {
        return accountDAO.validLogin(account);
    }

    public List<Message> getAllMessagesWithAccountId(int accountId) {
        return accountDAO.getAllMessagesWithAccountId(accountId);
    }

}
