package bank;

import com.banking.bank.Account;
import com.banking.bank.Customer;
import com.banking.bank.exception.CustomerNotOwnerException;
import com.banking.bank.exception.InsufficentFundsException;
import com.banking.bank.exception.InvalidAmountException;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Created by Graham Murray on 07/11/16.
 */

public class AccountTest extends BaseTest{

    @Test
    public void testCreateAccount() {
        Customer customer = createCustomer();
        Account account = createAccount(customer);

        assertEquals(1234, account.getSortCode());
        assertTrue(customer.isOwner(account));
    }

    @Test
    public void testTransfer() throws InsufficentFundsException, CustomerNotOwnerException, InvalidAmountException {
        Account fromAccount = createAccount(createCustomer());
        Account toAccount = createAccount(createCustomer());

        fromAccount.lodge(fromAccount.getOwner(), 500);
        fromAccount.transfer(toAccount, 50);

        assertEquals(450.00, fromAccount.getBalance(), DELTA);
        assertEquals(50.00, toAccount.getBalance(), DELTA);
    }
}
