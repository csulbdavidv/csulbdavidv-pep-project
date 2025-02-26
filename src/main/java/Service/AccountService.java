package Service;
import Model.Account;
import DAO.AccountDAO;
import java.util.*;
import io.javalin.http.Context;


public class AccountService {
    private AccountDAO accountDAO;

    public AccountService(){
        accountDAO = new AccountDAO();
    }

    public AccountService(AccountDAO accountDAO){
        this.accountDAO = accountDAO;
    }

    public Account getUserByAccountId(int account_id){
        return accountDAO.getUserByAccountId(account_id);
    }
    public Account register(Account a){
        return accountDAO.register(a);
    }

    public Account login(Account a){
        return accountDAO.login(a);
    }
}
