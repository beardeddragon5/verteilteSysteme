package bank.server;

import java.io.Serializable;
import java.util.Hashtable;
import java.util.Random;
import java.util.Objects;
import java.util.Enumeration;
import java.util.Collection;
import java.util.ArrayList;
import java.util.List;
import java.rmi.*;
import java.rmi.server.*;
import java.rmi.registry.*;

import bank.common.*;


public class Bank extends UnicastRemoteObject implements Serializable, BankService {
  private static final long serialVersionUID = 0;
  private static final String SECRET = "123456";
  private static final Random random = new Random(System.currentTimeMillis());

  private final Hashtable<Long, Account> accounts;

  public Bank() throws RemoteException {
    this.accounts = new Hashtable<>();
  }

  public BankAccount register(String name, String password) throws BankException {
    if ( name == null || password == null ) {
      throw new BankException("name or password null");
    }
    if ( name.isEmpty() || password.isEmpty() ) {
      throw new BankException("name or password is empty");
    }

    long number;
    do {
      number = Math.abs(random.nextLong());
    } while( this.accounts.contains( number ) );
    Account a = new Account(number, name, password);
    this.accounts.put(number, a);
    return a.getAccount();
  }

  private Account findAccount(BankAccount account) throws BankException {
    final Account a = this.accounts.get(account.getNumber());
    if ( a == null || ! a.getOwner().equals(account.getOwner()) ) {
      throw new BankException("No valid account found");
    }
    return a;
  }

  public void deposit(BankAccount account, long amountInCent) throws BankException {
    if ( account == null ) {
      throw new BankException("account is null");
    }
    if ( amountInCent < 0 ) {
      throw new BankException("amount to deposit must be greateer than 0");
    }
    if ( amountInCent == 0 ) {
      return;
    }

    final Account a = this.findAccount(account);
    synchronized ( a ) {
      final long amount = a.getAmountInCent() + amountInCent;
      this.accounts.put( account.getNumber(), new Account( a, amount ) );
    }
  }

  public BankAccount withdraw(BankAccount account, String password, long amountInCent) throws BankException {
    if ( account == null || password == null ) {
      throw new BankException("account or password is null");
    }
    if ( amountInCent < 0 ) {
      throw new BankException("amount to withdraw must be greateer than 0");
    }
    if ( password.isEmpty() ) {
      throw new BankException("wrong password");
    }

    Account a = this.findAccount(account);
    if ( amountInCent == 0 ) {
      return a.getAccount();
    }
    if ( ! a.verifyPassword( password ) ) {
      throw new BankException("wrong password");
    }
    synchronized ( a ) {
      final long amount = a.getAmountInCent() - amountInCent;
      if ( amount < 0 ) {
        throw new BankException("not enough money on account");
      }
      a = new Account( a, amount );
      this.accounts.put( account.getNumber(), a );
    }
    return a.getAccount();
  }

  public BankAccount showAccount(BankAccount account, String password) throws BankException {
    if ( account == null || password == null ) {
      throw new BankException("account or password is null");
    }
    if ( password.isEmpty() ) {
      throw new BankException("wrong password");
    }

    Account a = this.findAccount(account);
    if ( ! a.verifyPassword( password ) ) {
      throw new BankException("wrong password");
    }
    return a.getAccount();
  }

  public long showAmount(String name, String secret) throws BankException {
    if ( ! secret.equals(SECRET) ) {
      throw new BankException("Permission denied");
    }
    long amount = 0;
    final Enumeration<Account> e = this.accounts.elements();
    while (e.hasMoreElements()) {
      final Account a = e.nextElement();
      amount += a.getAmountInCent();
    }
    return amount;
  }

  public Collection<Long> showAccountNumbers(String name, String secret) throws BankException {
    if ( ! secret.equals(SECRET) ) {
      throw new BankException("Permission denied");
    }
    final List<Long> numbers = new ArrayList<>();
    final Enumeration<Account> e = this.accounts.elements();
    while (e.hasMoreElements()) {
      final Account a = e.nextElement();
      if ( a.getOwner().equals( name ) ) {
        numbers.add(a.getNumber());
      }
    }
    return numbers;
  }

}
