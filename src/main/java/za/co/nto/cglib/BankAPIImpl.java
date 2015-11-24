package za.co.nto.cglib;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.jdbc.CannotGetJdbcConnectionException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author ntobeko.
 */
@Service
public class BankAPIImpl implements BankAPI {

    @Autowired
    private BankDAO bankDAO;

    @Override
    @Transactional
    public void save(Account account) throws BankServiceException {

        try {
            bankDAO.save(account);
        }
        catch(DataIntegrityViolationException e) {

            throw new BankServiceException("An account with the given ID number already exists", e);
        }
        catch(CannotGetJdbcConnectionException e) {

            throw new BankServiceException("There seems to be a problem with the database. Please...", e);
        }
        catch(Exception e) {

            throw new BankServiceException("An unexpected error occurred. Please...", e);
        }
    }

    @Override
    public Account getAccount(String accountNumber) throws BankServiceException {

        try {
            return bankDAO.loadAccount(accountNumber);
        }
        catch (Exception e) {

            throw new BankServiceException("There was a problem loading the account.", e);
        }
    }
}
