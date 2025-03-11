package DAO;
import Model.*;

public interface AccountDAO {
    public Account register(Account newAccount);

    public Account validLogin(Account credentials);

    public Account getAccountByUsername(String username);

}
