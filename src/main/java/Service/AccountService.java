package Service;

import DAO.*;
import Model.Account;

public class AccountService {

    private AccountDAO accountDAO;

    public AccountService() {
        accountDAO = new AccountDAOImpl();
    }

    public Account registerAccount(Account newAccount) {
        if (newAccount.getUsername().length() < 1 || newAccount.getPassword().length() < 4 || accountDAO.getAccountByUsername(newAccount.getUsername()) != null)
            return null;
        return accountDAO.registerAccount(newAccount);
    }

    public Account validLogin(Account account) {
        return accountDAO.validLogin(account);
    }
}
