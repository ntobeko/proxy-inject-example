package za.co.nto.cglib;

/**
 * @author ntobeko.
 */
public interface BankAPI {

    public void save(Account account) throws BankServiceException;
    public Account getAccount(String accountNumber) throws BankServiceException;
}
