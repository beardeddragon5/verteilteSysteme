package bank.common;

import java.util.Collection;
import java.rmi.*;

public interface BankService extends Remote {

  public static final String REGISTRY_NAME = "bankservice_%d";

  public BankAccount register(String name, String password) throws BankException, RemoteException;

  public void deposit(BankAccount account, long amountInCent) throws BankException, RemoteException;

  public BankAccount withdraw(BankAccount account, String password, long amountInCent) throws BankException, RemoteException;

  public BankAccount showAccount(BankAccount account, String password) throws BankException, RemoteException;


  public long showAmount(String name, String secret) throws BankException, RemoteException;

  public Collection<Long> showAccountNumbers(String name, String secret) throws BankException, RemoteException;

}
