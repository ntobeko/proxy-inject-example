package za.co.nto;

import junit.framework.Assert;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.aop.framework.Advised;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jdbc.CannotGetJdbcConnectionException;
import za.co.nto.cglib.*;

import java.lang.reflect.Field;
import java.sql.SQLException;

import static org.easymock.EasyMock.*;

/**
 * @author ntobeko.
 */
public class TestBankAPI {

    private static final Log LOG = LogFactory.getLog(TestBankAPI.class);

    private BankAPI bankService = null;
    private BankDAO realBankDAO = null;
    private static ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");

    @Before
    public void setup() {
        // do some setup
        bankService = (BankAPI) context.getBean("bankAPIImpl");
        realBankDAO = (BankDAO) context.getBean("bankDAOJPA");
    }

    @After
    public void tearDown() {
        // clean up
    }

    public static void injectField(Object object, String fieldName, Object fieldValue) throws TestException {
        try {
            if (object instanceof Advised)
                object = ((Advised)object).getTargetSource().getTarget();

            // Get the member variable for which the value must be replaced
            Field field = object.getClass().getDeclaredField(fieldName);
            // Make sure it's accessible
            field.setAccessible(true);
            // Replace the value
            field.set(object, fieldValue);
        }
        catch (Exception e) {
            throw new TestException(e);
        }
    }

    @Test(expected = BankServiceException.class)
    public void testSaveAccountFailNoConnection() throws BankServiceException, TestException {

        // Setup mocking
        BankDAO dao = createMock(BankDAO.class);

        try {
            Account account = new Account("12345", "Joe Soap");
            dao.save(account);

            expectLastCall().andThrow(new CannotGetJdbcConnectionException(
                                            "Can't get connection.",
                                            new SQLException("Dummy SQL Exception.")));
            replay(dao);

            // Inject the DAO into the service object using reflection
            injectField(bankService, "bankDAO", dao);

            // Now call service method
            bankService.save(account);

        }
        catch (BankDataException e) {
            LOG.error(e);
            Assert.fail("Unexpected exception thrown during test: " + e.getMessage());
        }
        finally {
            // finally, restore the real DAO so other tests
            // expecting a real database can use it.
            injectField(bankService, "bankDAO", realBankDAO);
        }
    }

    @Test
    public void testSaveAccountForReal() {

        try {
            Account account = new Account("54321", "Joe Soap");

            // Now call service method
            bankService.save(account);
            Account newAccount = bankService.getAccount(account.getAccountNumber());
            Assert.assertEquals(account.getAccountNumber(), newAccount.getAccountNumber());
        }
        catch (BankServiceException e) {
            LOG.error(e.getMessage(), e);
            Assert.fail("Unexpected exception thrown during test: " + e.getMessage());
        }
    }
}