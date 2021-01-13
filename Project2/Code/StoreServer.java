import org.apache.thrift.server.TServer;

import org.apache.thrift.server.TSimpleServer;

import org.apache.thrift.server.TThreadPoolServer;

import org.apache.thrift.transport.TServerSocket;

import org.apache.thrift.transport.TServerTransport;

import java.util.Scanner;

public class StoreServer {

  public static StoreHandler handler;

  public static StoreOperations.Processor processor;

  public static void main(String[] args) {

    Scanner scanner = new Scanner(System.in);

    try {
      handler = new StoreHandler();
      processor = new StoreOperations.Processor(handler);

      Runnable simple = new Runnable() {
        @Override
        public void run() {

          simple(processor);
        }
      };

      new Thread(simple).start();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public static void simple(StoreOperations.Processor processor) {
    try {
      TServerTransport serverTransport = new TServerSocket(9090);

      TServer server = new TThreadPoolServer(new TThreadPoolServer.Args(serverTransport)
              .processor(processor));

      System.out.println("Starting the simple server...");
      System.out.println();
      System.out.println();
      server.serve();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
