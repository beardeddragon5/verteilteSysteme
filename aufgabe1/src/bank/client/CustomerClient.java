package bank.client;

import java.rmi.*;
import java.util.Scanner;
import java.io.IOException;

import bank.common.BankAccount;
import bank.common.BankException;

public class CustomerClient extends BaseClient {

  public static void main(String[] args) throws IOException {
    try {
      final CustomerClient client = new CustomerClient(args[0], Integer.parseInt(args[1]));
      client.init();
    } catch( RemoteException | NotBoundException e ) {
      System.out.println("Can't connect to bank: " + e.getMessage());
    } catch( ArrayIndexOutOfBoundsException e  ) {
      System.out.println("java -jar client.jar <host> <port>");
    }
  }

  private final Scanner scanner = new Scanner(System.in);

  public CustomerClient(String host, int port) throws RemoteException, NotBoundException, AccessException {
    super(host, port);
  }

  public void init() throws IOException, RemoteException {
    while( true ) {
      System.out.println("Willkommen bei Bank rott");
      System.out.println("Was wollen sie heute tun?");
      System.out.println(" 1 - Konto eröffnen");
      System.out.println(" 2 - Geld einzahlen");
      System.out.println(" 3 - Geld auszahlen");
      System.out.println(" 4 - Konto anzeigen");
      System.out.print("Eingabe [1-4]: ");

      int input = scanner.nextInt();
      switch (input) {
        case 1:
          register();
          break;
        case 2:
          deposit();
          break;
        case 3:
          withdraw();
          break;
        case 4:
          show();
          break;
        default:
          System.out.println("Keine gültige Eingabe");
          break;
      }
    }
  }

  public String promtNonEmpty(final String first, final String repeating) {
    System.out.print(first);
    scanner.reset();
    String input = scanner.next();
    while ( input.isEmpty() ) {
      System.out.print(repeating);
      input = scanner.next();
    }
    return input;
  }

  public void register() throws RemoteException {
    System.out.println("Es ist uns eine Freude das sie ein Konto");
    System.out.println("bei uns eröffnen wollen.\n");

    final String name = promtNonEmpty("Wie lautet ihr Name: ", "BITTE geben sie ihren Namen ein: ");
    final String password = promtNonEmpty("Welches Password: ", "BITTE geben sei ein nicht leeres Password ein: ");

    try {
      final BankAccount account = service.register(name, password);

      System.out.println("Glückwunsch sie haben ein Konto eröffnet\n");
      print(account);
    } catch(BankException e) {
      System.out.println("Fehler bei Kontoerstellung: " + e.getMessage());
    }
  }

  public void print(BankAccount account) {
    System.out.println("Daten:");
    System.out.printf("  Besitzer: %s\n", account.getOwner());
    System.out.printf("  Konto-Nummer: %d\n", account.getNumber());
    System.out.printf("  Betrag: %.2f\n", account.getAmountInCent() / 100.0);
  }

  public BankAccount find(boolean personal)  {
    final String name;
    final String numberStr;

    if ( personal ) {
      name = promtNonEmpty("Wie lautet ihr Name: ", "BITTE geben Sie ihren Namen ein: ");
      numberStr = promtNonEmpty("Unter welcher Kontonummer: ", "BITTE geben eine Kontonummer ein: ");
    } else {
      name = promtNonEmpty("Bei wem möchten sie Geld einzahlen: ", "BITTE geben sie einen Namen ein: ");
      numberStr = promtNonEmpty("Unter welcher Kontonummer: ", "BITTE geben eine Kontonummer ein: ");
    }

    long number = 0;
    try {
      number = Long.parseLong(numberStr);
    } catch(NumberFormatException e) {
      System.out.println("Keine gültige Kontonummer");
      return null;
    }
    return new BankAccount(number, name);
  }

  public void deposit() throws RemoteException {
    BankAccount account = find(false);

    final String amountStr = promtNonEmpty("Wie viel möchten sie einzahlen: ", "BITTE geben sie einen Betrag ein: ");

    double amount = 0;
    try {
      amount = Double.parseDouble(amountStr);;
    } catch(NumberFormatException e) {
      System.out.println("Kein gültiger Betrag");
      return;
    }

    long amountInCent = (long)(amount * 100);

    try {
      service.deposit(account, amountInCent);
    } catch( BankException e) {
      System.out.println("Fehler: " + e.getMessage());
    }
  }

  public void withdraw() throws RemoteException {
    final BankAccount a = find(true);
    final String password = promtNonEmpty("Wie lautet das Passwort: ", "BITTE geben sie ihr Passwort ein: ");
    final String amountStr = promtNonEmpty("Wie viel möchten sie auszahlen: ", "BITTE geben sie einen Betrag ein: ");

    double amount = 0;
    try {
      amount = Double.parseDouble(amountStr);;
    } catch(NumberFormatException e) {
      System.out.println("Kein gültiger Betrag");
      return;
    }

    long amountInCent = (long)(amount * 100);

    try {
      final BankAccount account = service.withdraw(a, password, amountInCent);
      print(account);
    } catch( BankException e) {
      System.out.println("Fehler: " + e.getMessage());
    }
  }

  public void show() throws RemoteException {
    final BankAccount a = find(true);
    final String password = promtNonEmpty("Wie lautet das Passwort: ", "BITTE geben sie ihr Passwort ein: ");
    try {
      final BankAccount account = service.showAccount(a, password);
      print(account);
    } catch( BankException e) {
      System.out.println("Fehler: " + e.getMessage());
    }
  }
}
