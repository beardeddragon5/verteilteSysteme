package bank.server;

import java.rmi.*;
import java.rmi.server.*;
import java.rmi.registry.*;
import java.io.*;
import java.nio.file.*;
import java.util.*;

import bank.common.*;

public class BankServer {

  private static BankService createBank(String dbfile) throws IOException {
    if ( Files.exists( Paths.get(dbfile) ) ) {
      try ( FileInputStream fin = new FileInputStream(dbfile);
            ObjectInputStream in = new ObjectInputStream( fin ) ) {
        return (Bank) in.readObject();
      } catch (FileNotFoundException | ClassNotFoundException e) {
        e.printStackTrace();
      }
    }
    return new Bank();
  }

  private static void writeBank(String dbfile, BankService bank) {
    try ( FileOutputStream fout = new FileOutputStream(dbfile);
          ObjectOutputStream out = new ObjectOutputStream( fout ) ) {
      out.writeObject(bank);
    } catch( IOException e ) {
      e.printStackTrace();
    }
  }

  public static void main (String args[]) throws IOException {
    try {
      final Registry reg = LocateRegistry.createRegistry(Integer.parseInt(args[0]));

      final Thread mainThread = Thread.currentThread();
      for ( int i = 1; i < args.length; i++ ) {
        final int bankID = i;
        final BankService service = createBank(args[bankID]);
        reg.rebind(String.format(BankService.REGISTRY_NAME, bankID), service);

        Runtime.getRuntime().addShutdownHook(new Thread() {
          public void run() {
            writeBank(args[bankID], service);
            try {
              mainThread.join();
            } catch(InterruptedException e) {
              e.printStackTrace();
            }
          }
        });
      }
      System.out.println("Bank ready");
    } catch( RemoteException e ) {
      e.printStackTrace();
    } catch( ArrayIndexOutOfBoundsException | NumberFormatException e ) {
      System.out.println("java -jar server.jar <port> <dbfile>");
    }
  }
}
