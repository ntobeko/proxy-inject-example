package za.co.nto.cglib;

/**
 * @author ntobeko.
 */
public interface BankDAO {

    public void save(Account account) throws BankDataException;
    public Account loadAccount(String accountNumber) throws BankDataException;
}
