package com.banking.bank;

import com.banking.bank.exception.CustomerNotOwnerException;
import com.banking.bank.exception.InsufficientFundsException;

/**
 * Created by graham on 09/11/16.
 */
public class Withdrawal extends Transaction {

    public Withdrawal(Customer customer, Account account, double amount) throws CustomerNotOwnerException, InsufficientFundsException {
        super(Type.DEBIT, amount, account);
        withdraw(customer, account, amount);
    }

    private void withdraw(Customer customer, Account account, double amount) throws CustomerNotOwnerException, InsufficientFundsException {
        if (customer.isOwner(account)) {
            if (account.canWithdraw(amount)) {
                account.updateBalance(account.getBalance() - amount);
            } else {
                throw new InsufficientFundsException();
            }
        } else {
            throw new CustomerNotOwnerException();
        }
    }
}
