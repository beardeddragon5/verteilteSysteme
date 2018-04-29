package bank.server;

import java.security.*;
import java.util.Arrays;
import java.io.Serializable;

import bank.common.BankAccount;

public class Account extends BankAccount implements Serializable {
  private static final long serialVersionUID = 0;

  private final static MessageDigest digest;

  static {
    try {
      digest = MessageDigest.getInstance("SHA-512");
    } catch (NoSuchAlgorithmException e) {
      throw new RuntimeException(e);
    }
  }

  private final byte[] passwordHash;

  public Account(long number, String name, String password) {
    this(new BankAccount(number, name), password);
  }

  public Account(BankAccount account, String password) {
    super(account);
    this.passwordHash = digest.digest(password.getBytes());
  }

  public Account(Account account, long amountInCent) {
    super(account.getNumber(), account.getOwner(), amountInCent);
    this.passwordHash = Arrays.copyOf(account.passwordHash, account.passwordHash.length);
  }

  public BankAccount getAccount() {
    return new BankAccount(this.getNumber(), this.getOwner(), this.getAmountInCent());
  }

  public boolean verifyPassword(String password) {
    return Arrays.equals(passwordHash, digest.digest(password.getBytes()));
  }

}
