package bank.client;

import java.rmi.*;
import java.rmi.server.*;
import java.rmi.registry.*;

import bank.common.BankService;

public abstract class BaseClient {

  protected final Registry registry;
  protected final BankService service;

  public BaseClient(String host, int port, int bankID) throws RemoteException, NotBoundException, AccessException {
    this.registry = LocateRegistry.getRegistry(host, port);
    this.service = (BankService) this.registry.lookup(String.format(BankService.REGISTRY_NAME, bankID));
  }

}
