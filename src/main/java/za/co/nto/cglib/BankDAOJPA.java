package za.co.nto.cglib;

import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 * @author ntobeko.
 */
@Repository
public class BankDAOJPA implements BankDAO {

    @PersistenceContext(unitName = "bankEmf")
    private EntityManager entityManager;

    @Override
    public void save(Account account) throws BankDataException {
        entityManager.persist(entityManager.merge(account));
    }

    @Override
    public Account loadAccount(String accountNumber) throws BankDataException {
        Query query = entityManager.createQuery(
                                        "select account from Account account " +
                                        "where account.accountNumber = :accountNumber");
        query.setParameter("accountNumber", accountNumber);
        return (Account)query.getSingleResult();
    }
}
