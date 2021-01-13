import org.apache.thrift.TException;

import org.apache.thrift.protocol.TBinaryProtocol;

import org.apache.thrift.protocol.TProtocol;

import org.apache.thrift.transport.TSocket;

import org.apache.thrift.transport.TTransport;

import java.text.SimpleDateFormat;
import java.util.Scanner;


public class StoreClient {

  public static void main(String[] args) {

    try {
      TTransport transport;

      transport = new TSocket("localhost", 9090);

      transport.open();

      TProtocol protocol = new TBinaryProtocol(transport);

      StoreOperations.Client client = new StoreOperations.Client(protocol);

      perform(client);

      transport.close();

    } catch (TException x) {
      x.printStackTrace();
    }
  }

  private static void perform(StoreOperations.Client client) throws InvalidOperation
  {
    Scanner scanner = new Scanner(System.in);

    System.out.println("This program is used to record the food and its price as a key-" +
            "value pair.\n Please enter one of the 3 operations, \n the name of the food \n" +
            "and its price, seperated them by spaces");
      while (true) {

        try {
          System.out.println();
          System.out.println("Please enter a PUT/GET/DELETE command");
          String input = scanner.nextLine();
          String res = client.analyze(input);
          System.out.println(res);
          showTime();

        } catch (InvalidOperation io) {

          System.out.println("Invalid Operation: " + io.why);
          showTime();
        } catch (TException e) {

          System.out.println(e.getMessage());
          showTime();
        }
      }
  }

  /**
   * This function shows the current system time in millisecond precision.
   */

  private static void showTime() throws InvalidOperation {

    SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
    System.out.print("System Time:  ");
    System.out.println(formatter.format(System.currentTimeMillis()));
    System.out.println();
  }
}
