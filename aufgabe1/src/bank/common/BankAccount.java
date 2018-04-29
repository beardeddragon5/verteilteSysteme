package bank.common;

import java.io.Serializable;

public class BankAccount implements Serializable {
  private static final long serialVersionUID = 0;

  private final long number;
  private final String owner;
  private final long amountInCent;

  public BankAccount(final long number, final String owner) {
    this(number, owner, 0);
  }

  public BankAccount(final long number, final String owner, final long amountInCent) {
    this.number = number;
    this.owner = owner;
    this.amountInCent = amountInCent;
  }

  public BankAccount(final BankAccount account) {
    this.number = account.number;
    this.owner = account.owner;
    this.amountInCent = account.amountInCent;
  }

  public String getOwner() {
    return this.owner;
  }

  public long getNumber() {
    return this.number;
  }

  public long getAmountInCent() {
    return this.amountInCent;
  }

}
