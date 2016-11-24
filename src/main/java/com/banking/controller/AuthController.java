package com.banking.controller;

import com.banking.bank.Customer;
import com.banking.persistence.PersistenceManager;
import com.banking.util.HashUtil;

import javax.persistence.TypedQuery;

/**
 * This controller deals with authentication prior
 * to any transactions taking place on a account
 *
 * @author Graham Murray
 */
public class AuthController {

    private PersistenceManager persistenceManager;
    private Customer customer;

    public AuthController(PersistenceManager persistenceManager) {
        this.persistenceManager = persistenceManager;
    }

    /**
     * Takes a email and password and returns the customer
     * associated with if credentials are valid
     *
     * @param email
     * @param password
     * @return Customer
     */
    public Customer getAuthenticatedCustomer(String email, String password) {

        TypedQuery customerQuery = createCustomerQuery(email);
        customer = (Customer) persistenceManager.getSingleResult(customerQuery);

        if (customer != null && isPasswordCorrect(password)) {
            return customer;
        }

        return null;
    }

    public boolean isCredentialsValid(Customer customer, String email, String password) {
        this.customer = customer;

        return isEmailCorrect(email) && isPasswordCorrect(password);
    }

    private boolean isEmailCorrect(String email) {
        return customer.getEmail().equals(email);
    }

    private boolean isPasswordCorrect(String password) {
        String encryptedPassword = HashUtil.sha256(password);

        return encryptedPassword.equals(customer.getPassword());
    }

    private TypedQuery createCustomerQuery(String email) {
        TypedQuery<Customer> query = persistenceManager.getEntityManager().createQuery(
                "SELECT c FROM Customer c WHERE c.email = ?1", Customer.class);
        query.setParameter(1, email);

        return  query;
    }
}
