package bank.client;

import java.rmi.*;
import java.util.Scanner;
import java.util.Arrays;
import java.util.Collection;

import bank.common.BankAccount;
import bank.common.BankException;

public class TaxOfficeClient extends BaseClient {

  public static void main(String[] args) {
    try {
      final TaxOfficeClient client = new TaxOfficeClient(args[0], Integer.parseInt(args[1]), Integer.parseInt(args[2]));
      client.init(Arrays.copyOfRange(args, 3, args.length));
    } catch( RemoteException | NotBoundException e ) {
      System.out.println("Can't connect to bank: " + e.getMessage());
    } catch( ArrayIndexOutOfBoundsException e  ) {
      System.out.println("java -jar client.jar <host> <port> <bankid> {numbers|amount} <name>");
    }
  }

  public TaxOfficeClient(String host, int port, int bankID) throws RemoteException, NotBoundException, AccessException {
    super(host, port, bankID);
  }

  public void init(String[] args) throws ArrayIndexOutOfBoundsException, RemoteException {
    final String action = args[0];
    final String name = args[1];
    System.out.print("secret: ");
    final Scanner scan = new Scanner(System.in);
    final String secret = scan.nextLine();

    try {
      switch ( action ) {
        case "amount":
            System.out.println(service.showAmount(name, secret));
          break;
        case "numbers":
          Collection<Long> numbers = service.showAccountNumbers(name, secret);
          for (long number : numbers) {
            System.out.println(number);
          }
          break;
        default:
          scan.close();
          throw new ArrayIndexOutOfBoundsException();
      }
    } catch(BankException e) {
      System.err.println(e.getMessage());
    }
    scan.close();
  }

}
